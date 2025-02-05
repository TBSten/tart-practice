import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.createDirectories
import kotlin.io.path.readText
import kotlin.io.path.writeText

plugins {
    // build-logic primitive plugins
    alias(libs.plugins.buildLogicPrimitiveLint) apply false
    alias(libs.plugins.buildLogicPrimitiveCompose) apply false
    alias(libs.plugins.buildLogicPrimitiveNavigationCompose) apply false
    alias(libs.plugins.buildLogicPrimitiveKotlinxSerialization) apply false
    alias(libs.plugins.buildLogicPrimitiveHilt) apply false
    alias(libs.plugins.buildLogicPrimitiveRoom) apply false
    alias(libs.plugins.buildLogicPrimitiveOpenApi) apply false

    // build-logic module plugins
    alias(libs.plugins.buildLogicModuleAndroidApplication) apply false
    alias(libs.plugins.buildLogicModuleAndroidLibrary) apply false
    alias(libs.plugins.buildLogicModuleFeature) apply false

    // other plugins
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinCompose) apply false
    alias(libs.plugins.kotlinPluginSerialization) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.roborazzi) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.openApi) apply false
}

/**
 * usages
 *
 * ```shell
 * ./gradlew updateAppNames -PnewName=NewAppName -PnewApplicationId=new.packageName
 * # or
 * ./gradlew updateAppNames -PnewName=NewAppName -PoldApplicationId=old.packageName -PnewApplicationId=new.packageName
 * ```
 */
val updateAppNames by tasks.creating {

    val newNameProperty = providers.gradleProperty("newName")
    val newApplicationIdProperty = providers.gradleProperty("newApplicationId")
    val oldApplicationIdProperty = providers.gradleProperty("oldApplicationId")

    // update AppName
    doLast {
        val newName = newNameProperty.orNull ?: return@doLast
        println("newName: $newName")

        // replace settings.gradle.kts
        val settingsGradleKts = rootProject.file("settings.gradle.kts")
        settingsGradleKts.writeText(
            settingsGradleKts
                .readText()
                .replace(Regex("rootProject.name = \".*\""), "rootProject.name = \"$newName\""),
        )

        // replace app/src/main/res/values/strings.xml
        val appStringsXml = rootProject.file("app/src/main/res/values/strings.xml")
        appStringsXml.writeText(
            appStringsXml
                .readText()
                .replace(Regex("<string name=\"app_name\">.*</string>"), "<string name=\"app_name\">$newName</string>"),
        )
    }

    doLast {
        // replace libs.versions.toml app-applicationId
        val newApplicationId = newApplicationIdProperty.orNull ?: return@doLast
        val oldApplicationId = oldApplicationIdProperty.orNull ?: "me.tbsten.prac.tart"

        println("newApplicationId: $newApplicationId")
        println("oldApplicationId: $oldApplicationId")

        val libsVersionsToml = rootProject.file("gradle/libs.versions.toml")
        libsVersionsToml.writeText(
            libsVersionsToml
                .readText()
                .replace(oldApplicationId, newApplicationId),
        )

        // replace directories
        val newPackage =
            newApplicationIdProperty.orNull ?: return@doLast
        val newPackagePath = newPackage.replace(".", "/")
        val oldPackage = oldApplicationIdProperty.orNull ?: "me.tbsten.prac.tart"
        val oldPackagePath = oldPackage.replace(".", "/")
        val oldPackageFiles =
            rootProject.fileTree(".") {
                include("**/$oldPackagePath/**")
            }
        oldPackageFiles.forEach { oldFile ->
            println(oldFile.path)
            println("  > ${oldFile.absolutePath.replace(oldPackagePath, newPackagePath)}")
            val newFile = Paths.get(oldFile.absolutePath.replace(oldPackagePath, newPackagePath))
            newFile.parent.createDirectories()
            Files.move(
                oldFile.toPath(),
                newFile,
            )
        }

        // replace package name
        rootProject.fileTree("./") {
            exclude(".gradle/**")
            exclude("gradle/wrapper/**")
            exclude("**/build/**")
        }.forEach { file ->
            file.writeText(
                file.readText()
                    .replace(oldPackage, newPackage),
            )
        }
    }
}
