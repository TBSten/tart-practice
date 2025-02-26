package me.tbsten.prac.tart.data.api.generated.api

import me.tbsten.prac.tart.data.api.generated.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import me.tbsten.prac.tart.data.api.generated.model.Post

interface PostsApi {
    /**
     * 
     * Returns a post by id
     * Responses:
     *  - 200: Successful response
     *  - 404: Post not found
     *
     * @param id The user id.
     * @return [Post]
     */
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: kotlin.Long): Response<Post>

    /**
     * 
     * Returns all posts
     * Responses:
     *  - 200: Successful response
     *
     * @return [kotlin.collections.List<Post>]
     */
    @GET("posts")
    suspend fun getPosts(): Response<kotlin.collections.List<Post>>

}
