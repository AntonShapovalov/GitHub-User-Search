package bb.testask.githubusersearch.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bb.testask.githubusersearch.R
import bb.testask.githubusersearch.dao.User
import bb.testask.githubusersearch.ui.activity.MainActivity
import bb.testask.githubusersearch.ui.ext.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.layout_flipper.*
import javax.inject.Inject

const val USER_ID_KEY = "USER_ID_KEY"
fun DetailsFragment.setParams(userId: Int) = apply { arguments = Bundle().apply { putInt(USER_ID_KEY, userId) } }

/**
 * Details screen
 */
class DetailsFragment : Fragment() {

    @Inject lateinit var factory: ViewModelFactory

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_details, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).mainComponent.inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        viewModel.userId = savedInstanceState?.getInt(USER_ID_KEY) ?: arguments?.getInt(USER_ID_KEY) ?: 0
        viewModel.state.observe(this, Observer { onStateChanged(it) })
        viewModel.getUserDetails()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.apply { putInt(USER_ID_KEY, viewModel.userId) }
        super.onSaveInstanceState(outState)
    }

    private fun onStateChanged(state: ViewModelState?) {
        when (state) {
            is StateProgress -> flipper.progress()
            is UserLoaded -> setAvatar(state.user) // only avatar available
            is ProfileLoaded -> setProfileInfo(state.user) // name and bio loaded
            is StateError -> showError(state.throwable, R.string.error_message_details, { flipper.empty() })
            else -> flipper.empty()
        }
    }

    private fun setProfileInfo(user: User) {
        flipper.empty()
        setAvatar(user)
        textName.text = if (user.name.isNullOrEmpty()) user.login else user.name
        textBio.text = user.bio
    }

    private fun setAvatar(user: User) = Glide.with(imageAvatar.context).load(user.avatarUrl).into(imageAvatar)

}