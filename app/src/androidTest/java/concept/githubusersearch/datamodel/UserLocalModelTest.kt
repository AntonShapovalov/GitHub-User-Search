package concept.githubusersearch.datamodel

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import concept.githubusersearch.app.DaggerAppTestComponent
import concept.githubusersearch.app.SearchApplication
import concept.githubusersearch.dao.DaoSession
import concept.githubusersearch.model.ProfileResponse
import concept.githubusersearch.model.RepoEntry
import concept.githubusersearch.model.UserEntry
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
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as SearchApplication
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
        saveRepos()
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

    private fun saveRepos() {
        val response = listOf(RepoEntry(id = 27, name = dummy, language = dummy))
        val user = userLocalModel.getProfile(id)
        userLocalModel.saveRepos(user, response)
        val repos = user.repos
        assertEquals(1, repos.size)
    }

    @After
    fun tearDown() = userLocalModel.clear()

}