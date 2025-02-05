package me.tbsten.prac.tart.domain.counter

import io.yumemi.tart.core.Action

sealed interface CounterAction : Action {
    data class Increment(val count: Int = 1) : CounterAction
    data class Set(val count: Int) : CounterAction
    data object StartAutoIncrement : CounterAction
    data object StopAutoIncrement : CounterAction
}

internal data object AutoIncrement : CounterAction
