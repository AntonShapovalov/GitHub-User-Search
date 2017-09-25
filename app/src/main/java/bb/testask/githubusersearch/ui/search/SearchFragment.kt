package bb.testask.githubusersearch.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import bb.testask.githubusersearch.R
import bb.testask.githubusersearch.dao.User
import bb.testask.githubusersearch.ui.activity.MainActivity
import bb.testask.githubusersearch.ui.ext.*
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

/**
 * Search screen
 */
class SearchFragment : Fragment() {

    @Inject lateinit var factory: SearchViewModelFactory

    private lateinit var viewModel: SearchViewModel
    private val adapter: UserListAdapter = UserListAdapter { showUserDetails(it) }
    private val queryKey: String = "QUERY_KEY"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userList.initList(adapter, LinearLayoutManager.VERTICAL)
        //
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(QueryTextListener {
            viewModel.search(it)
            activity.hideKeyboard()
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).mainComponent.inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
        viewModel.state.observe(this, Observer { onStateChanged(it) })
        savedInstanceState?.getString(queryKey)?.let { viewModel.search(it) }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        with(viewModel.state.value) {
            if (this is SearchState.Success) outState?.putString(queryKey, this.query)
        }
        super.onSaveInstanceState(outState)
    }

    private fun onStateChanged(state: SearchState?) {
        when (state) {
            is SearchState.Progress -> flipper.progress()
            is SearchState.Success -> setUsers(state.users)
            is SearchState.Error -> showError(state.t.message)
            else -> flipper.empty()
        }
    }

    private fun setUsers(users: List<User>) {
        if (users.isEmpty()) {
            flipper.noUsersFound()
            adapter.clearItems()
        } else {
            flipper.empty()
            adapter.setItems(users)
        }
    }

    private fun showError(message: String?) {
        flipper.empty()
        activity.showToast(message ?: getString(R.string.error_message))
    }

    private fun showUserDetails(userId: Int) {

    }

}

class QueryTextListener(private val action: (String) -> Unit) : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query.isNotBlank()) action(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

}

