package concept.githubusersearch.api

import concept.githubusersearch.datamodel.UserRemoteModelTest
import dagger.Component
import javax.inject.Singleton

/**
 * Provide dependencies for [UserRemoteModelTest]
 */
@Singleton
@Component(modules = arrayOf(ApiModule::class))
interface ApiTestComponent {

    fun inject(test: UserRemoteModelTest)

}