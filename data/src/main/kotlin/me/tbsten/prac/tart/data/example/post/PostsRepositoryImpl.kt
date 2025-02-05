package me.tbsten.prac.tart.data.example.post

import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.tbsten.prac.tart.data.api.generated.api.PostsApi
import me.tbsten.prac.tart.data.api.generated.model.Post as ApiPost
import me.tbsten.prac.tart.data.example.bodyOrThrow
import me.tbsten.prac.tart.domain.example.post.Post
import me.tbsten.prac.tart.domain.example.post.PostsRepository

internal class PostsRepositoryImpl @Inject constructor(
    private val postsApi: PostsApi,
) : PostsRepository {
    override suspend fun getPosts(): List<Post> = withContext(Dispatchers.IO) {
        postsApi
            .getPosts()
            .bodyOrThrow()
            .map(ApiPost::toDomain)
    }
}

private fun ApiPost.toDomain() = Post(
    id = me.tbsten.prac.tart.domain.example.post.PostId(id),
    userId = me.tbsten.prac.tart.domain.example.post.UserId(userId),
    title = title,
    body = body,
)
