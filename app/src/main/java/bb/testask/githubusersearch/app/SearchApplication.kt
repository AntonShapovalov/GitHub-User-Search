package bb.testask.githubusersearch.app

import android.app.Application
import bb.testask.githubusersearch.api.ApiModule

/**
 * Instance of Application, provide Singleton dependencies via [AppComponent]
 */
class SearchApplication : Application() {

    val appComponent: AppComponent by lazy { buildAppComponent() }

    private fun buildAppComponent(): AppComponent = DaggerAppComponent.builder()
            .apiModule(ApiModule())
            .build()

}