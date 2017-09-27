package bb.testask.githubusersearch.app

import android.app.Application
import bb.testask.githubusersearch.BuildConfig
import bb.testask.githubusersearch.api.ApiModule
import com.facebook.stetho.Stetho

/**
 * Instance of Application, provide Singleton dependencies via [AppComponent]
 */
class SearchApplication : Application() {

    val appComponent: AppComponent by lazy { buildAppComponent() }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }

    private fun buildAppComponent(): AppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .apiModule(ApiModule())
            .build()

}