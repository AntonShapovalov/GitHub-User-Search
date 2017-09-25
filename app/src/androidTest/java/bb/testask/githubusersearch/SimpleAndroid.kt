package bb.testask.githubusersearch

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Simple test, to try something with Android context
 */
@RunWith(AndroidJUnit4::class)
class SimpleAndroid {
    @Test
    @Throws(Exception::class)
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("bb.testask.githubusersearch", appContext.packageName)
    }
}
