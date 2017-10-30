package concept.githubusersearch

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
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
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("bb.testask.githubusersearch", appContext.packageName)
    }

}
