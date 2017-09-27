package bb.testask.githubusersearch.ui.ext

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ViewFlipper

/**
 * Extensions for UI elements
 */
fun View.show() = let { visibility = View.VISIBLE }

fun View.hide() = let { visibility = View.INVISIBLE }

fun View.gone() = let { visibility = View.GONE }

fun ViewFlipper.progress() = let { displayedChild = 0 }

fun ViewFlipper.message() = let { displayedChild = 1 }

fun ViewFlipper.empty() = let { displayedChild = 2 }

fun RecyclerView.initList(adapter: RecyclerView.Adapter<*>, orientation: Int) {
    val layoutManager = LinearLayoutManager(context, orientation, false)
    layoutManager.isSmoothScrollbarEnabled = true
    this.layoutManager = layoutManager
    this.adapter = adapter
}
