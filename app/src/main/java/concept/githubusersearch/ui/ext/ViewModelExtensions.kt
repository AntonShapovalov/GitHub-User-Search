package concept.githubusersearch.ui.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import concept.githubusersearch.dao.User
import concept.githubusersearch.ui.details.DetailsViewModel
import concept.githubusersearch.ui.search.SearchViewModel
import java.lang.RuntimeException
import javax.inject.Inject

/**
 * General State of all ViewModel: Idle, Progress, Error
 * Each ViewModel can add own State like OwnState:ViewModelState
 */
sealed class ViewModelState

object StateIdle : ViewModelState()
object StateProgress : ViewModelState()
data class StateError(val throwable: Throwable) : ViewModelState()

data class SearchRestored(val query: String) : ViewModelState()
data class UsersLoaded(val query: String, val users: List<User>) : ViewModelState()

data class UserLoaded(val user: User) : ViewModelState() // avatarUrl
data class ProfileLoaded(val user: User, var isName: Boolean = false, var isRepos: Boolean = false) : ViewModelState() // name, bio and repositories

/**
 * ViewModelState LiveData - to init default state value
 */
class StateLiveData(state: ViewModelState = StateIdle) : MutableLiveData<ViewModelState>() {
    init {
        value = state
    }
}

/**
 * Provide instance of [SearchViewModel] or [DetailsViewModel]
 */
class ViewModelFactory @Inject constructor(
        private val searchViewModel: SearchViewModel,
        private val detailsViewModel: DetailsViewModel
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isInstance(searchViewModel) -> searchViewModel as T
        modelClass.isInstance(detailsViewModel) -> detailsViewModel as T
        else -> throw RuntimeException("Unknown ViewModel, inject it in constructor")
    }
}
