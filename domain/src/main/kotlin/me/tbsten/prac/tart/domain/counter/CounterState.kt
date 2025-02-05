package me.tbsten.prac.tart.domain.counter

import io.yumemi.tart.core.State

data class CounterState(
    val count: Int,
    val isAutoIncrementing: Boolean = false,
) : State
