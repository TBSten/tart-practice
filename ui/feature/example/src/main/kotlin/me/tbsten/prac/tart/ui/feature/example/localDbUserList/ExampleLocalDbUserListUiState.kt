package me.tbsten.prac.tart.ui.feature.example.localDbUserList

import me.tbsten.prac.tart.domain.example.user.User

internal sealed interface ExampleLocalDbUserListUiState {
    data object InitialLoading : ExampleLocalDbUserListUiState
    data class Success(
        val users: List<User>,
    ) : ExampleLocalDbUserListUiState
}
