package bb.testask.githubusersearch.ui.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bb.testask.githubusersearch.R
import bb.testask.githubusersearch.dao.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_user.view.*

/**
 * Recycler adapter for User List in [SearchFragment]
 */
class UserListAdapter(private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private val items = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = items[position]
        val view = holder.itemView
        view.setOnClickListener { onItemClick(user.serverId) }
        view.textLogin.text = user.login
        Glide.with(view.context).load(user.avatarUrl).into(view.imageAvatar)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(users: List<User>) {
        items.clear()
        items.addAll(users)
        notifyDataSetChanged()
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}