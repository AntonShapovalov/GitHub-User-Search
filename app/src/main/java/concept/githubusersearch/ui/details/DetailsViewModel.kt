package concept.githubusersearch.ui.details

import android.arch.lifecycle.ViewModel
import concept.githubusersearch.dao.User
import concept.githubusersearch.datamodel.UserLocalModel
import concept.githubusersearch.datamodel.UserRemoteModel
import concept.githubusersearch.ui.ext.*
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
     * 2. in parallel:
     * 2.1 check if user name is empty - load profile from remote API
     * 2.2 check if repositories is empty - load repositories
     */
    fun getUserDetails() {
        val currentState = state.value
        if (currentState is ProfileLoaded && currentState.isName && currentState.isRepos) return
        val s = Observable.just(userId)
                .filter { it > 0 }
                .map { userLocalModel.getProfile(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = StateProgress }
                .subscribe({
                    state.value = UserLoaded(it)
                    getProfileDetails(it)
                    getRepos(it)
                }, { state.value = StateError(it) })
        subscriptions.add(s)
    }

    // get name and bio
    private fun getProfileDetails(user: User) {
        val s = getLocalProfile(user)
                .concatWith(getRemoteProfile(user))
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setProfileState(it, { it.isName = true }) }, { state.value = StateError(it) })
        subscriptions.add(s)
    }

    private fun getLocalProfile(user: User): Observable<User> = Observable.just(user)
            .filter { !it.name.isNullOrEmpty() }

    private fun getRemoteProfile(user: User): Observable<User> = Observable.just(user)
            .filter { !it.login.isNullOrEmpty() }
            .flatMap { userRemoteModel.getProfile(it.login) }
            .doOnNext { userLocalModel.saveProfile(it) }
            .map { user }

    // get repositories
    private fun getRepos(user: User) {
        val s = getLocalRepos(user)
                .concatWith(getRemoteRepos(user))
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setProfileState(it, { it.isRepos = true }) }, { state.value = StateError(it) })
        subscriptions.add(s)
    }

    private fun getLocalRepos(user: User): Observable<User> = Observable.just(user)
            .filter { user.repos.isNotEmpty() }

    private fun getRemoteRepos(user: User): Observable<User> = Observable.just(user)
            .filter { !it.login.isNullOrEmpty() }
            .flatMap { userRemoteModel.getRepos(it.login) }
            .doOnNext { userLocalModel.saveRepos(user, it) }
            .map { user }

    private fun setProfileState(user: User, action: (profileState: ProfileLoaded) -> Unit) {
        val currentState = state.value
        state.value = (currentState as? ProfileLoaded)?.copy()?.also(action) ?: ProfileLoaded(user).also(action)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.unsubscribe()
    }

}