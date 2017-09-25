package bb.testask.githubusersearch.ui.activity

import android.app.Activity
import android.app.FragmentTransaction
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import bb.testask.githubusersearch.R
import bb.testask.githubusersearch.ui.launch.LaunchFragment
import bb.testask.githubusersearch.ui.search.SearchFragment

/**
 * Extensions for [Activity]
 */
const val LAUNCH_FRAGMENT_TAG = "LAUNCH_FRAGMENT_TAG"
const val SEARCH_FRAGMENT_TAG = "SEARCH_FRAGMENT_TAG"

fun FragmentActivity.showLaunchFragment() {
    var fragment = getFragment(LAUNCH_FRAGMENT_TAG)
    if (fragment == null) {
        fragment = LaunchFragment()
        addFragment(R.id.fragment_container, fragment, LAUNCH_FRAGMENT_TAG)
    }
}

fun FragmentActivity.showSearchFragment() {
    var fragment = getFragment(SEARCH_FRAGMENT_TAG)
    if (fragment == null) {
        fragment = SearchFragment()
        replaceFragment(R.id.fragment_container, fragment, SEARCH_FRAGMENT_TAG)
    }
}

private fun FragmentActivity.getFragment(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

private fun FragmentActivity.addFragment(containerId: Int, fragment: Fragment, tag: String) = supportFragmentManager
        .beginTransaction().add(containerId, fragment, tag).commit()

private fun FragmentActivity.replaceFragment(
        containerId: Int,
        newFragment: Fragment,
        tag: String,
        isAddToBackStack: Boolean = false,
        backStackTag: String? = null
) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    if (isAddToBackStack) transaction.addToBackStack(backStackTag ?: tag)
    transaction.replace(containerId, newFragment, tag)
    transaction.commit()
}
