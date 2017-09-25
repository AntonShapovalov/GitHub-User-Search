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

    private val launchDelay = 3L // show launch screen at least n seconds
    private val delayTimeUnit = TimeUnit.SECONDS

    private var launchSubscription: Subscription? = null
    val state = StateLiveData()

    /**
     * Init Dagger application component and notify ready with [launchDelay]
     */
    fun launch() {
        val delay = Observable.timer(launchDelay, delayTimeUnit)
        val launch = Observable.just(getApplication<SearchApplication>())
                .map { it.appComponent }.map { 1L } // build Dagger app component
        launchSubscription = Observable.zip(delay, launch, { _, _ -> state })
                .map { it.value?.copy(isCompleted = true) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { it.printStackTrace() }
                .subscribe { state.value = it }
    }

    override fun onCleared() {
        super.onCleared()
        launchSubscription?.unsubscribe()
    }

}

class StateLiveData(state: LaunchState = LaunchState()) : MutableLiveData<LaunchState>() {
    init {
        value = state
    }
}

data class LaunchState(val isCompleted: Boolean = false)

