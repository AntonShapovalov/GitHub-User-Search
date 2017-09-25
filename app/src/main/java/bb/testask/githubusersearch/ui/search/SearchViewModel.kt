package bb.testask.githubusersearch.ui.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import bb.testask.githubusersearch.dao.User
import bb.testask.githubusersearch.datamodel.UserLocalModel
import bb.testask.githubusersearch.datamodel.UserRemoteModel
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Get users from remote API, save in local DB and notify [SearchFragment]
 */
class SearchViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var userLocalModel: UserLocalModel
    @Inject lateinit var userRemoteModel: UserRemoteModel

    private var subscription: Subscription? = null
    val state = SearchStateLiveData()

    /**
     * Get users by query, if exists local data - do not perform API call
     */
    fun search(query: String) {
        subscription = getLocalData(query)
                .concatWith(getRemoteData(query))
                .take(1)
                .map { SearchState.Success(query, it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = SearchState.Progress }
                .subscribe({ state.value = it }, { state.value = SearchState.Error(it) })
    }

    private fun getLocalData(query: String): Observable<List<User>> = Observable
            .fromCallable { userLocalModel.getUsers(query) }
            .filter { it.isNotEmpty() }

    private fun getRemoteData(query: String): Observable<List<User>> = userRemoteModel
            .getUsers(query)
            .doOnNext { userLocalModel.saveUsers(query, it) }
            .map { userLocalModel.getUsers(query) }

    override fun onCleared() {
        super.onCleared()
        subscription?.unsubscribe()
    }

}

class SearchStateLiveData(state: SearchState = SearchState.Idle) : MutableLiveData<SearchState>() {
    init {
        value = state
    }
}

sealed class SearchState {
    object Idle : SearchState()
    object Progress : SearchState()
    data class Success(val query: String, val users: List<User>) : SearchState()
    data class Error(val t: Throwable) : SearchState()
}