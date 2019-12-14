package concept.githubusersearch.ui.details

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import concept.githubusersearch.R
import concept.githubusersearch.dao.User
import concept.githubusersearch.ui.activity.MainActivity
import concept.githubusersearch.ui.ext.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.layout_avatar.*
import kotlinx.android.synthetic.main.layout_flipper.*
import kotlinx.android.synthetic.main.layout_profile.*
import javax.inject.Inject

const val USER_ID_KEY = "USER_ID_KEY"
fun DetailsFragment.setParams(userId: Int) = apply { arguments = Bundle().apply { putInt(USER_ID_KEY, userId) } }

/**
 * Details screen
 */
class DetailsFragment : Fragment() {

    @Inject lateinit var factory: ViewModelFactory

    private lateinit var viewModel: DetailsViewModel
    private val adapter = RepoListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repoList.initList(adapter, LinearLayoutManager.VERTICAL)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).mainComponent.inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        viewModel.userId = arguments?.getInt(USER_ID_KEY) ?: savedInstanceState?.getInt(USER_ID_KEY) ?: 0
        viewModel.state.observe(this, Observer { onStateChanged(it) })
        viewModel.getUserDetails()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.apply { putInt(USER_ID_KEY, viewModel.userId) }
        super.onSaveInstanceState(outState)
    }

    private fun onStateChanged(state: ViewModelState?) {
        when (state) {
            is StateProgress -> flipper.progress()
            is UserLoaded -> setAvatar(state.user) // only avatar available
            is ProfileLoaded -> setProfileInfo(state) // name, bio or repos loaded
            is StateError -> showError(state.throwable, R.string.error_message_details, { flipper.empty() })
            else -> flipper.empty()
        }
    }

    private fun setProfileInfo(state: ProfileLoaded) {
        if (state.isName && state.isRepos) flipper.empty() // hide progress when all data loaded
        val user = state.user
        setAvatar(state.user)
        textName.text = if (user.name.isNullOrEmpty()) user.login else user.name
        textBio.apply { if (user.bio.isNullOrEmpty()) gone() else show(); text = user.bio }
        if (state.isRepos) adapter.setItems(user.repos)
    }

    private fun setAvatar(user: User) = Glide.with(imageAvatar.context)
            .load(user.avatarUrl)
            .into(imageAvatar)

}