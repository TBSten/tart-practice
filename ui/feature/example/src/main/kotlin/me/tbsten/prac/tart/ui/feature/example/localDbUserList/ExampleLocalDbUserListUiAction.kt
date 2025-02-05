package me.tbsten.prac.tart.ui.feature.example.localDbUserList

import me.tbsten.prac.tart.domain.example.user.User

sealed interface ExampleLocalDbUserListUiAction {
    data object OnAddUser : ExampleLocalDbUserListUiAction
    data class OnDeleteUser(val user: User) : ExampleLocalDbUserListUiAction
}
