package bb.testask.githubusersearch.app

import bb.testask.githubusersearch.api.ApiModule
import bb.testask.githubusersearch.datamodel.UserRemoteModel
import dagger.Component
import javax.inject.Singleton

/**
 * Provide Application scope dependencies
 */
@Singleton
@Component(modules = arrayOf(ApiModule::class))
interface AppComponent {

    fun userRemoteModel(): UserRemoteModel

}