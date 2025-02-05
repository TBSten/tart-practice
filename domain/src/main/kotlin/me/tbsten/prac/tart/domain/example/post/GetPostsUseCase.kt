package me.tbsten.prac.tart.domain.example.post

interface GetPostsUseCase {
    suspend fun execute(): List<Post>
}
