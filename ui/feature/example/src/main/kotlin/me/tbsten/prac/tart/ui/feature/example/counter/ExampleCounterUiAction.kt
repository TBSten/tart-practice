package me.tbsten.prac.tart.ui.feature.example.counter

internal sealed interface ExampleCounterUiAction {
    data object Refresh : ExampleCounterUiAction
}
