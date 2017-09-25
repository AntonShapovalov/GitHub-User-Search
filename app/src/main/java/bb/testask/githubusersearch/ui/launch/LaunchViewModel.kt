package bb.testask.githubusersearch.ui.launch

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import bb.testask.githubusersearch.app.SearchApplication
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Set launch screen delay and loads resources (Dagger components) in parallel
 */
class LaunchViewModel(application: Application) : AndroidViewModel(application) {

    private val launchDelay = 2L // show launch screen at least n seconds
    private val delayTimeUnit = TimeUnit.SECONDS

    private var subscription: Subscription? = null
    val state = LaunchStateLiveData()

    /**
     * Init Dagger application component and notify ready with [launchDelay]
     */
    fun launch() {
        val delay = Observable.timer(launchDelay, delayTimeUnit)
        val launch = Observable.just(getApplication<SearchApplication>())
                .map { it.appComponent }.map { 1L } // build Dagger app component
        subscription = Observable.zip(delay, launch, { _, _ -> state })
                .map { it.value?.copy(isCompleted = true) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ state.value = it }, { it.printStackTrace() })
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.unsubscribe()
    }

}

class LaunchStateLiveData(state: LaunchState = LaunchState()) : MutableLiveData<LaunchState>() {
    init {
        value = state
    }
}

data class LaunchState(val isCompleted: Boolean = false)

