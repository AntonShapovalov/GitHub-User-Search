package bb.testask.githubusersearch.ui.ext

import android.app.Activity
import android.app.FragmentTransaction
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import bb.testask.githubusersearch.R
import bb.testask.githubusersearch.ui.details.DetailsFragment
import bb.testask.githubusersearch.ui.details.setParams
import bb.testask.githubusersearch.ui.launch.LaunchFragment
import bb.testask.githubusersearch.ui.search.SearchFragment

/**
 * Extensions for [Activity]
 */

const val LAUNCH_FRAGMENT_TAG = "LAUNCH_FRAGMENT_TAG"
const val SEARCH_FRAGMENT_TAG = "SEARCH_FRAGMENT_TAG"
const val DETAILS_FRAGMENT_TAG = "DETAILS_FRAGMENT_TAG"

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

fun FragmentActivity.showDetailsFragment(userId: Int) {
    var fragment = getFragment(DETAILS_FRAGMENT_TAG)
    if (fragment == null) {
        fragment = DetailsFragment().setParams(userId)
        replaceFragment(R.id.fragment_container, fragment, DETAILS_FRAGMENT_TAG, isAddToBackStack = true, backStackTag = SEARCH_FRAGMENT_TAG)
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

fun Activity.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Activity.hideKeyboard() = currentFocus?.let {
    val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(it.windowToken, 0)
}

fun Fragment.showError(throwable: Throwable, messageId: Int, action: () -> Unit) {
    action()
    throwable.printStackTrace()
    activity.showToast(throwable.message ?: getString(messageId))
}