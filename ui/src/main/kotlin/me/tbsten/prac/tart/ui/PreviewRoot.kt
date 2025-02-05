package me.tbsten.prac.tart.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.tbsten.prac.tart.error.AbstractErrorStateHolder
import me.tbsten.prac.tart.ui.designSystem.AppTheme
import me.tbsten.prac.tart.ui.error.LocalErrorStateHolder

@SuppressLint("ComposeModifierMissing")
@Composable
fun PreviewRoot(content: @Composable () -> Unit) {
    AppTheme {
        CompositionLocalProvider(
            LocalErrorStateHolder provides PreviewErrorStateHolder,
        ) {
            Box(Modifier.sizeIn(minWidth = 1.dp, minHeight = 1.dp)) {
                content()
            }
        }
    }
}

internal object PreviewErrorStateHolder : AbstractErrorStateHolder()
