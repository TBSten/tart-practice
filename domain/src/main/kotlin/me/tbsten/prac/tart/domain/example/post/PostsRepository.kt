package me.tbsten.prac.tart.domain.example.post

interface PostsRepository {
    suspend fun getPosts(): List<Post>
}
