package me.tbsten.prac.tart.ui.error

import android.annotation.SuppressLint
import androidx.compose.runtime.compositionLocalOf
import me.tbsten.prac.tart.error.ErrorStateHolder

@SuppressLint("ComposeCompositionLocalUsage")
val LocalErrorStateHolder = compositionLocalOf<ErrorStateHolder> {
    error("ErrorStateHolder is not provided.")
}
