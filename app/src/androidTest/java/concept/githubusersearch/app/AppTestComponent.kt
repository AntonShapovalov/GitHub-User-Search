package concept.githubusersearch.app

import concept.githubusersearch.datamodel.UserLocalModelTest
import concept.githubusersearch.ui.activity.ViewScope
import dagger.Component

/**
 * Provide dependencies for [UserLocalModelTest]
 */
@ViewScope
@Component(dependencies = arrayOf(AppComponent::class))
interface AppTestComponent {

    fun inject(test: UserLocalModelTest)

}