package concept.githubusersearch.ui.activity

import concept.githubusersearch.app.AppComponent
import concept.githubusersearch.ui.details.DetailsFragment
import concept.githubusersearch.ui.search.SearchFragment
import dagger.Component

/**
 * Provide [ViewScope] dependencies based on [AppComponent]
 */
@ViewScope
@Component(dependencies = arrayOf(AppComponent::class))
interface MainComponent {

    fun inject(searchFragment: SearchFragment)

    fun inject(detailsFragment: DetailsFragment)

}