package bb.testask.githubusersearch.api

import bb.testask.githubusersearch.model.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * GitHub API retrofit service
 */
interface ApiService {

    @GET("search/users")
    fun getUsers(@Query("q") query: String): Observable<Response<UsersResponse>>

}