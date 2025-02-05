package your.projectPackage.domain.counter

import io.yumemi.tart.core.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CounterStore(
    private val coroutineScope: CoroutineScope,
) : Store.Base<CounterState, CounterAction, Nothing>(
    CounterState(count = 0),
    coroutineContext = coroutineScope.coroutineContext,
) {
    private var autoIncrementJob: Job? = null
        private set(newValue) {
            field?.cancel()
            field = newValue
        }

    override suspend fun onDispatch(state: CounterState, action: CounterAction): CounterState = when (action) {
        is CounterAction.Increment -> state.copy(count = state.count + 1)
        is CounterAction.Set -> state.copy(count = action.count)
        CounterAction.StartAutoIncrement -> {
            autoIncrementJob = coroutineScope.launch {
                while (true) {
                    delay(1000)
                    dispatch(AutoIncrement)
                }
            }
            state.copy(isAutoIncrementing = true)
        }
        CounterAction.StopAutoIncrement -> {
            autoIncrementJob = null
            state.copy(isAutoIncrementing = false)
        }
        AutoIncrement -> {
            state.copy(
                count = state.count + 1,
            )
        }
    }
}
