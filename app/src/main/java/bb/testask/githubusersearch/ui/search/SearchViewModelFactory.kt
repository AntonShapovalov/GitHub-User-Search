package bb.testask.githubusersearch.ui.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Provide instance of [SearchViewModel]
 */
class SearchViewModelFactory @Inject constructor(private val searchViewModel: SearchViewModel) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>?): T = searchViewModel as T

}