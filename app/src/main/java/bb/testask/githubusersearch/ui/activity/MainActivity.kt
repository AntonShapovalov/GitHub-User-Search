package bb.testask.githubusersearch.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import bb.testask.githubusersearch.R
import bb.testask.githubusersearch.app.SearchApplication
import bb.testask.githubusersearch.ui.ext.showLaunchFragment

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
