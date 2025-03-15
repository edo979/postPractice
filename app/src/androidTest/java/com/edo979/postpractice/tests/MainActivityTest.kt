package com.edo979.postpractice.tests

import android.util.Log
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescriptionExactly
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import com.edo979.data_local.db.post.PostDao
import com.edo979.postpractice.MainActivity
import com.edo979.postpractice.idling.ComposeCountingIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    @Test
    fun testDisplayList() {
        composeTestRule.onNodeWithText("title1").assertIsDisplayed()
        composeTestRule.onNodeWithText("title2").assertIsDisplayed()
        composeTestRule.onNodeWithText("title3").assertIsDisplayed()
        composeTestRule.onNodeWithText("title4").assertIsDisplayed()
        composeTestRule.onAllNodesWithText("name1").assertCountEquals(2)
        composeTestRule.onAllNodesWithText("name2").assertCountEquals(2)
        composeTestRule.onNodeWithTag("tabRow").assertIsDisplayed()
        composeTestRule.onNodeWithTag("tab0").assertIsDisplayed()
        composeTestRule.onNodeWithTag("tab0").assertIsSelected()
        composeTestRule.onNodeWithTag("tab1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("tab1").assertIsNotSelected()
        composeTestRule.onNodeWithTag("postList").onChildren().assertCountEquals(4)
    }

    @Test
    fun testSwipeTabs() {
        composeTestRule.onNodeWithTag("postListPager").performTouchInput { swipeLeft() }
        composeTestRule.onNodeWithTag("tab1").assertIsSelected()
        composeTestRule.onNodeWithTag("tab0").assertIsNotSelected()
        composeTestRule.onNodeWithTag("postList").onChildren().assertCountEquals(0)

        composeTestRule.onNodeWithTag("postListPager").performTouchInput { swipeRight() }
        composeTestRule.onNodeWithTag("tab0").assertIsSelected()
        composeTestRule.onNodeWithTag("tab1").assertIsNotSelected()
        composeTestRule.onNodeWithTag("postList").onChildren().assertCountEquals(4)
    }

    @Test
    fun testSearchPost() {
        composeTestRule.onNodeWithTag("searchBar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("searchBar").performTextInput("e")
        composeTestRule.onNodeWithTag("postList").onChildren().assertCountEquals(4)
        composeTestRule.onNodeWithTag("searchBar").performTextInput("1")
        runBlocking {
            // using debounce in flow for search query. Search will be active only on 2 ore more characters
            // or empty string
            delay(550L)
            composeTestRule.awaitIdle()
            composeTestRule.onNodeWithTag("postList").onChildren().assertCountEquals(1)

            composeTestRule.onNodeWithTag("searchBar").performTextReplacement("e")
            delay(550L)
            composeTestRule.awaitIdle()
            composeTestRule.onNodeWithTag("postList").onChildren().assertCountEquals(1)

            composeTestRule.onNodeWithTag("searchBar").performTextReplacement("")
            delay(550L)
            composeTestRule.awaitIdle()
            composeTestRule.onNodeWithTag("postList").onChildren().assertCountEquals(4)
        }
    }

    @Test
    fun testFavoritePosts() {
        composeTestRule.onNodeWithTag("postList").onChildren().onFirst().performClick()
        composeTestRule.onNodeWithText("name1: author").assertIsDisplayed()
//        composeTestRule.onNode(hasContentDescriptionExactly("Heart icon")).isDisplayed()
//        composeTestRule.onNode(hasContentDescriptionExactly("Border heart icon")).isNotDisplayed()

//        composeTestRule.onNodeWithTag("favoriteButton").performClick()
//
//        composeTestRule.onNode(hasContentDescriptionExactly("Heart icon")).isNotDisplayed()
//        composeTestRule.onNode(hasContentDescriptionExactly("Border heart icon")).isDisplayed()
    }
}