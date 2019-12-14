package concept.githubusersearch.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import concept.githubusersearch.R
import concept.githubusersearch.app.SearchApplication
import concept.githubusersearch.ui.ext.showLaunchFragment

class MainActivity : FragmentActivity() {

    val mainComponent: MainComponent by lazy { buildMainComponent() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) showLaunchFragment()
    }

    private fun buildMainComponent(): MainComponent = DaggerMainComponent.builder()
            .appComponent((application as SearchApplication).appComponent)
            .build()

}
