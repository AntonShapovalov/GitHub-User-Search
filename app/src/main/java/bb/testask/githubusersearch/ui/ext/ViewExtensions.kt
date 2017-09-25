package bb.testask.githubusersearch.ui.ext

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ViewFlipper

/**
 * Extensions for UI elements
 */

fun ViewFlipper.progress() = let { displayedChild = 0 }

fun ViewFlipper.noUsersFound() = let { displayedChild = 1 }

fun ViewFlipper.empty() = let { displayedChild = 2 }

fun RecyclerView.initList(adapter: RecyclerView.Adapter<*>, orientation: Int) {
    val layoutManager = LinearLayoutManager(context, orientation, false)
    layoutManager.isSmoothScrollbarEnabled = true
    this.layoutManager = layoutManager
    this.adapter = adapter
}
