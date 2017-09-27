package bb.testask.githubusersearch.ui.search

import android.arch.lifecycle.ViewModel
import bb.testask.githubusersearch.dao.User
import bb.testask.githubusersearch.datamodel.UserLocalModel
import bb.testask.githubusersearch.datamodel.UserRemoteModel
import bb.testask.githubusersearch.ui.ext.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Get users from remote API, save in local DB and notify [SearchFragment]
 */
class SearchViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var userLocalModel: UserLocalModel
    @Inject lateinit var userRemoteModel: UserRemoteModel

    private val subscriptions: CompositeSubscription = CompositeSubscription()
    val state = StateLiveData()

    /**
     * Try to restore last success query on app launch
     */
    fun restoreLastQuery() {
        if (state.value is SearchRestored || state.value is UsersLoaded) return
        val s = Observable.fromCallable { userLocalModel.getLastQuery() }
                .map { if (it.isNotEmpty()) SearchRestored(it) else StateIdle }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = StateProgress }
                .subscribe({ state.value = it }, { state.value = StateError(it) })
        subscriptions.add(s)
    }

    /**
     * Get users by query, if exists local data - do not perform API call
     */
    fun search(query: String) {
        val currentValue = state.value
        if (currentValue is UsersLoaded && currentValue.query == query) return
        val s = getLocalData(query)
                .concatWith(getRemoteData(query))
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = StateProgress }
                .subscribe({ state.value = UsersLoaded(query, it) }, { state.value = StateError(it) })
        subscriptions.add(s)
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
        subscriptions.unsubscribe()
    }

}
