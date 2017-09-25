package bb.testask.githubusersearch.ui.activity

import bb.testask.githubusersearch.app.AppComponent
import bb.testask.githubusersearch.ui.search.SearchFragment
import dagger.Component

/**
 * Provide [ViewScope] dependencies based on [AppComponent]
 */
@ViewScope
@Component(dependencies = arrayOf(AppComponent::class))
interface MainComponent {

    fun inject(searchFragment: SearchFragment)

}