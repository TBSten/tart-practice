package me.tbsten.prac.tart.ui.error

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.tbsten.prac.tart.error.ErrorHandleType
import me.tbsten.prac.tart.error.ErrorState
import me.tbsten.prac.tart.error.ErrorStateHolder

@Suppress("NAME_SHADOWING")
@Composable
fun HandleErrors(
    errorStateHolder: ErrorStateHolder,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val errorState by errorStateHolder.errorState.collectAsStateWithLifecycle()

    CompositionLocalProvider(LocalErrorStateHolder provides errorStateHolder) {
        content()

        Crossfade(
            targetState = errorState,
            label = "HandleErrors Crossfade",
            modifier = modifier,
        ) { errorState ->
            when (errorState) {
                ErrorState.NoError -> Unit
                is ErrorState.HandleError -> when (val handleType = errorState.handleType) {
                    is ErrorHandleType.Ignore ->
                        Unit
                    is ErrorHandleType.Dialog ->
                        CommonErrorDialog(
                            errorState = errorState,
                            errorHandleType = handleType,
                            onClose = errorStateHolder::onClose,
                            onRetry = errorStateHolder::onRetry,
                        )
                }
                is ErrorState.Hide ->
                    Unit
            }
        }
    }
}
