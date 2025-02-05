package me.tbsten.prac.tart.domain.example.user

interface GetUsersUseCase {
    suspend fun execute(): List<User>
}
