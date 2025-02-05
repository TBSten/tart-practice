package me.tbsten.prac.tart.domain.example.user

interface DeleteUserUseCase {
    suspend fun execute(user: User)
}
