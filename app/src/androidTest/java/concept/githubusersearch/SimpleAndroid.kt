package concept.githubusersearch

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Simple test, to try something with Android context
 */
@RunWith(AndroidJUnit4::class)
class SimpleAndroid {

    @Test
    @Throws(Exception::class)
    fun test() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("concept.githubusersearch", appContext.packageName)
    }

}
