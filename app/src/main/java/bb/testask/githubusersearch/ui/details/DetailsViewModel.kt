package bb.testask.githubusersearch.ui.details

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
 * Get Profile details (name, bio, repositories) from remote API, save in local DB and notify [DetailsFragment]
 */
class DetailsViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var userLocalModel: UserLocalModel
    @Inject lateinit var userRemoteModel: UserRemoteModel

    private val subscriptions: CompositeSubscription = CompositeSubscription()
    var userId = 0 // user server id from Fragment arguments
    val state = StateLiveData()

    /**
     * Load User profile info:
     * 1. load user from local ID to set avatar on UI
     * 2. check if user name is empty - load profile from remote API
     */

    fun getUserDetails() {
        if (state.value is ProfileLoaded) return@getUserDetails
        val s = Observable.fromCallable { userLocalModel.getProfile(userId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = StateProgress }
                .subscribe({ getProfileDetails(it) }, { state.value = StateError(it) })
        subscriptions.add(s)
    }

    private fun getProfileDetails(user: User) {
        state.value = UserLoaded(user)
        val s = getLocalProfileData(user)
                .concatWith(getRemoteProfileData(user))
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ state.value = ProfileLoaded(it) }, { state.value = StateError(it) })
        subscriptions.add(s)
    }

    private fun getLocalProfileData(user: User): Observable<User> = Observable.just(user)
            .filter { !it.name.isNullOrEmpty() }

    private fun getRemoteProfileData(user: User): Observable<User> = Observable.just(user)
            .filter { !it.login.isNullOrEmpty() }
            .flatMap { userRemoteModel.getProfile(it.login) }
            .doOnNext { userLocalModel.saveProfile(it) }
            .map { userLocalModel.getProfile(userId) }

    override fun onCleared() {
        super.onCleared()
        subscriptions.unsubscribe()
    }

}