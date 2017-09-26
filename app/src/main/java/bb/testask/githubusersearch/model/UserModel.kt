package bb.testask.githubusersearch.model

import bb.testask.githubusersearch.api.ApiService

import com.google.gson.annotations.SerializedName

/**
 * Response model of [ApiService.getUsers]
 */
data class UsersResponse(
        @SerializedName("total_count")
        val total: Int,
        @SerializedName("items")
        val items: List<UserEntry>
)

/**
 * Entry of [UsersResponse.items]
 */
data class UserEntry(
        val login: String,
        val id: Int,
        @SerializedName("avatar_url")
        val avatarUrl: String
)

/**
 * Response model of [ApiService.getProfile]
 */
data class ProfileResponse(
        val login: String,
        val id: Int,
        val name: String,
        val bio: String?
)
