package me.tbsten.prac.tart.domain.example.user

interface CreateUserUseCase {
    suspend fun execute(user: User)
}
