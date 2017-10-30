package concept.githubusersearch.app

import android.content.Context
import concept.githubusersearch.dao.DaoMaster
import concept.githubusersearch.dao.DaoSession
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provide local (DB, context) dependencies for [AppComponent]
 */
@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideDaoSession(): DaoSession {
        val db = DaoMaster.DevOpenHelper(context, "search-db").writableDb
        return DaoMaster(db).newSession()
    }

}