package bb.testask.githubusersearch.datamodel

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import bb.testask.githubusersearch.app.DaggerAppTestComponent
import bb.testask.githubusersearch.app.SearchApplication
import bb.testask.githubusersearch.dao.DaoSession
import bb.testask.githubusersearch.model.ProfileResponse
import bb.testask.githubusersearch.model.UserEntry
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Test for [UserLocalModel]
 */
@RunWith(AndroidJUnit4::class)
class UserLocalModelTest {

    @Inject lateinit var userLocalModel: UserLocalModel
    @Inject lateinit var daoSession: DaoSession

    private val dummy = "dummy"
    private val id = 25

    @Before
    fun setUp() {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as SearchApplication
        DaggerAppTestComponent.builder()
                .appComponent(app.appComponent)
                .build()
                .inject(this)
        userLocalModel.clear()
    }

    @Test
    fun saveRemoteData() {
        saveUsers()
        saveProfile()
    }

    private fun saveUsers() {
        val response = listOf(UserEntry(login = dummy, id = id, avatarUrl = dummy))
        userLocalModel.saveUsers(query = dummy, users = response)
        // tables row count
        with(daoSession) {
            assertEquals(userDao.loadAll().size, 1)
            assertEquals(queryDao.loadAll().size, 1)
            assertEquals(query2UserDao.loadAll().size, 1)
        }
        // user values
        val users = userLocalModel.getUsers(query = dummy)
        assertEquals(1, users.size)
        val user = users[0]
        assertEquals(id, user.serverId)
        assertEquals(dummy, user.login)
    }

    private fun saveProfile() {
        val response = ProfileResponse(login = dummy, id = id, name = dummy, bio = null)
        userLocalModel.saveProfile(response)
        val user = userLocalModel.getProfile(id)
        Assert.assertNotNull(user)
        assertEquals(dummy, user.name)
    }

    @After
    fun tearDown() {
        userLocalModel.clear()
    }

}