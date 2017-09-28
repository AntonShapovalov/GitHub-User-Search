package bb.testask.githubusersearch.ui.details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bb.testask.githubusersearch.R
import bb.testask.githubusersearch.dao.Repo
import bb.testask.githubusersearch.ui.ext.gone
import bb.testask.githubusersearch.ui.ext.show
import kotlinx.android.synthetic.main.list_item_repo.view.*

/**
 * Recycler adapter for Repo List in [DetailsFragment]
 */
class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    private val items = ArrayList<Repo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_repo, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = items[position]
        val view = holder.itemView
        view.textName.text = repo.name
        view.textDesc.apply { if (repo.description.isNullOrEmpty()) gone() else show(); text = repo.description }
        view.textLanguage.apply { if (repo.language.isNullOrEmpty()) gone() else show(); text = repo.language }
        view.listDivider.apply { if (position == items.size - 1) gone() else show() }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(repos: List<Repo>) {
        items.clear()
        items.addAll(repos)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}