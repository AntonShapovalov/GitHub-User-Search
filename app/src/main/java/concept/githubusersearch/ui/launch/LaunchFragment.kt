package concept.githubusersearch.ui.launch

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import concept.githubusersearch.R
import concept.githubusersearch.ui.ext.*
import kotlinx.android.synthetic.main.fragment_launch.*


/**
 * Launch Screen
 */
class LaunchFragment : Fragment() {

    private lateinit var viewModel: LaunchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_launch, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LaunchViewModel::class.java)
        viewModel.state.observe(this, Observer { onStateChanged(it) })
        viewModel.launch()
    }

    private fun onStateChanged(state: ViewModelState?) = when (state) {
        is StateIdle -> activity?.showSearchFragment()
        is StateError -> showError(state.throwable, R.string.error_message_launch) { progress.hide() }
        else -> {
        }
    }

}