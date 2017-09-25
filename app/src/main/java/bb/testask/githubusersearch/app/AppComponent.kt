package bb.testask.githubusersearch.app

import bb.testask.githubusersearch.api.ApiModule
import bb.testask.githubusersearch.dao.DaoSession
import bb.testask.githubusersearch.datamodel.UserLocalModel
import bb.testask.githubusersearch.datamodel.UserRemoteModel
import dagger.Component
import javax.inject.Singleton

/**
 * Provide Application scope dependencies
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class))
interface AppComponent {

    fun daoSession(): DaoSession

    fun userLocalModel(): UserLocalModel

    fun userRemoteModel(): UserRemoteModel

}