package bb.testask.githubusersearch.ui.launch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bb.testask.githubusersearch.R
import bb.testask.githubusersearch.ui.activity.showSearchFragment


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
        viewModel.state.observe(this, Observer { onLaunchCompleted(it) })
        viewModel.launch()
    }

    private fun onLaunchCompleted(state: LaunchState?) {
        if (state?.isCompleted == true) activity.showSearchFragment()
    }

}