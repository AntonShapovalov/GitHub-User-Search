package bb.testask.githubusersearch.app

import bb.testask.githubusersearch.datamodel.UserLocalModelTest
import bb.testask.githubusersearch.ui.activity.ViewScope
import dagger.Component

/**
 * Provide dependencies for [UserLocalModelTest]
 */
@ViewScope
@Component(dependencies = arrayOf(AppComponent::class))
interface AppTestComponent {

    fun inject(test: UserLocalModelTest)

}