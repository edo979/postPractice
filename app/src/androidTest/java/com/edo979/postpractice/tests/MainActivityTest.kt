package com.edo979.postpractice.tests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.edo979.data_local.db.post.PostDao
import com.edo979.postpractice.MainActivity
import com.edo979.postpractice.idling.ComposeCountingIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Inject
    lateinit var idlingResource: ComposeCountingIdlingResource

    @Inject
    lateinit var postDao: PostDao

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        composeTestRule.registerIdlingResource(idlingResource)
        runBlocking {
            postDao.deleteAllPosts()
        }
    }

    @After
    fun tearDown() {
        composeTestRule.unregisterIdlingResource(idlingResource)
    }
}