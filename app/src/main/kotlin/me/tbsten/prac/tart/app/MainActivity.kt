package me.tbsten.prac.tart.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import io.yumemi.tart.compose.rememberViewStore
import javax.inject.Inject
import me.tbsten.prac.tart.debug.ui.InjectDebugMenu
import me.tbsten.prac.tart.domain.counter.CounterAction
import me.tbsten.prac.tart.domain.counter.CounterStore
import me.tbsten.prac.tart.error.ApplicationErrorStateHolder
import me.tbsten.prac.tart.ui.designSystem.AppTheme
import me.tbsten.prac.tart.ui.error.HandleErrors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var applicationErrorStateHolder: ApplicationErrorStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                HandleErrors(applicationErrorStateHolder) {
                    InjectDebugMenu()
//                    AppNavHost()
                    AppContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("ComposeViewModelInjection")
@Composable
private fun AppContent() {
    val viewModel: MainViewModel = viewModel()
    val viewStore = rememberViewStore(viewModel.store)

    Column(Modifier.windowInsetsPadding(WindowInsets.systemBars)) {
        Text("count: ${viewStore.state.count}")
        HorizontalDivider()
        FlowRow {
            Button(onClick = { viewStore.dispatch(CounterAction.Increment()) }) {
                Text("increment")
            }
            Button(onClick = { viewStore.dispatch(CounterAction.Set(0)) }) {
                Text("reset")
            }
            if (viewStore.state.isAutoIncrementing) {
                Button(onClick = { viewStore.dispatch(CounterAction.StopAutoIncrement) }) {
                    Text("stop auto increment")
                }
            } else {
                Button(onClick = { viewStore.dispatch(CounterAction.StartAutoIncrement) }) {
                    Text("start auto increment")
                }
            }
        }
    }
}

class MainViewModel : ViewModel() {
    val store = CounterStore(viewModelScope)
}
