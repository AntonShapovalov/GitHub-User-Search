package concept.githubusersearch.app

import concept.githubusersearch.api.ApiModule
import concept.githubusersearch.dao.DaoSession
import concept.githubusersearch.datamodel.UserLocalModel
import concept.githubusersearch.datamodel.UserRemoteModel
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