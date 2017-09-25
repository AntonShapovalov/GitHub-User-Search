package bb.testask.githubusersearch.datamodel

import bb.testask.githubusersearch.api.ApiModule
import bb.testask.githubusersearch.api.DaggerApiTestComponent
import bb.testask.githubusersearch.model.UserEntry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import rx.observers.TestSubscriber
import javax.inject.Inject

/**
 * Test for [UserRemoteModel]
 */
class UserRemoteModelTest {

    @Inject lateinit var userRemoteModel: UserRemoteModel

    @Before
    fun setUp() {
        DaggerApiTestComponent.builder()
                .apiModule(ApiModule())
                .build()
                .inject(this)
    }

    @Test
    fun getUsers() {
        val testSubscriber = TestSubscriber<List<UserEntry>>()
        userRemoteModel.getUsers("antonshapovalov")
                .doOnNext {
                    Assert.assertTrue(it.isNotEmpty())
                    System.out.println(it[0])
                }
                .subscribe(testSubscriber)
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        testSubscriber.assertUnsubscribed()
    }

}