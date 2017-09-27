package bb.testask.githubusersearch.datamodel

import bb.testask.githubusersearch.api.ApiService
import bb.testask.githubusersearch.model.ProfileResponse
import bb.testask.githubusersearch.model.RepoEntry
import bb.testask.githubusersearch.model.UserEntry
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provide User data from [ApiService]
 */
@Singleton
class UserRemoteModel @Inject constructor() {

    @Inject lateinit var apiService: ApiService

    fun getUsers(query: String): Observable<List<UserEntry>> = apiService
            .getUsers(query)
            .map { if (it.isSuccessful) it.body().items else emptyList() }

    fun getProfile(login: String): Observable<ProfileResponse> = apiService
            .getProfile(login)
            .map { if (it.isSuccessful) it.body() else throw RuntimeException("Unable get Profile info") }

    fun getRepos(login: String): Observable<List<RepoEntry>> = apiService
            .getRepos(login)
            .map { if (it.isSuccessful) it.body() else throw RuntimeException("Unable get Repositories info") }

}