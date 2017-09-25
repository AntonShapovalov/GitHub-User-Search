package bb.testask.githubusersearch.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import bb.testask.githubusersearch.R

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) showLaunchFragment()
    }

}
