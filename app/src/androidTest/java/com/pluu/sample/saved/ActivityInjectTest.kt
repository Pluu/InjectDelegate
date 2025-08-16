package com.pluu.sample.saved

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.testutils.withActivity
import com.pluu.sample.saved.model.UserDto
import com.pluu.utils.buildIntent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityInjectTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val newValue = UserDto(id = 1234, name = "New User")

    @Test
    fun requiredWithError() {
        with(launchActivity<SampleActivity>()) {
            withActivity {
                assertThrows(IllegalStateException::class.java) {
                    requiredUser
                }
            }
        }
    }

    @Test
    fun requiredWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val intent = context.buildIntent<SampleActivity>(
            "requiredUser" to expectedValue
        )
        with(launchActivity<SampleActivity>(intent)) {
            withActivity {
                assertEquals(expectedValue, requiredUser)
            }
            recreate()
            withActivity {
                assertEquals(expectedValue, requiredUser)
                updateUser(newValue)
                assertEquals(newValue, requiredUser)
            }
            recreate()
            withActivity {
                assertEquals(newValue, requiredUser)
            }
        }
    }

    @Test
    fun requiredLambdaWithDefaultData() {
        with(launchActivity<SampleActivity>()) {
            withActivity {
                assertEquals(requiredUserDto, requiredUserLambda)
            }
            recreate()
            withActivity {
                assertEquals(requiredUserDto, requiredUserLambda)
                updateUser(newValue)
                assertEquals(newValue, requiredUserLambda)
            }
            recreate()
            withActivity {
                assertEquals(newValue, requiredUserLambda)
            }
        }
    }

    @Test
    fun requiredLambdaWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val intent = context.buildIntent<SampleActivity>(
            "requiredUserLambda" to expectedValue
        )
        with(launchActivity<SampleActivity>(intent)) {
            withActivity {
                assertEquals(expectedValue, requiredUserLambda)
            }
            recreate()
            withActivity {
                assertEquals(expectedValue, requiredUserLambda)
                updateUser(newValue)
                assertEquals(newValue, requiredUserLambda)
            }
            recreate()
            withActivity {
                assertEquals(newValue, requiredUserLambda)
            }
        }
    }

    @Test
    fun optionNull() {
        with(launchActivity<SampleActivity>()) {
            withActivity {
                assertNull(optionUser)
            }
            recreate()
            withActivity {
                assertNull(optionUser)
                updateOptionUser(newValue)
                assertEquals(newValue, optionUser)
            }
            recreate()
            withActivity {
                assertEquals(newValue, optionUser)
            }
        }
    }

    @Test
    fun optionWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val intent = context.buildIntent<SampleActivity>(
            "optionUser" to expectedValue
        )
        with(launchActivity<SampleActivity>(intent)) {
            withActivity {
                assertEquals(expectedValue, optionUser)
            }
            recreate()
            withActivity {
                assertEquals(expectedValue, optionUser)
                updateOptionUser(newValue)
                assertEquals(newValue, optionUser)
            }
            recreate()
            withActivity {
                assertEquals(newValue, optionUser)
            }
        }
    }

    @Test
    fun optionLambdaWithDefaultData() {
        with(launchActivity<SampleActivity>()) {
            withActivity {
                assertEquals(optionUserDto, optionUserLambda)
            }
            recreate()
            withActivity {
                assertEquals(optionUserDto, optionUserLambda)
                updateOptionUser(newValue)
                assertEquals(newValue, optionUserLambda)
            }
            recreate()
            withActivity {
                assertEquals(newValue, optionUserLambda)
            }
        }
    }

    @Test
    fun optionLambdaWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val intent = context.buildIntent<SampleActivity>(
            "optionUserLambda" to expectedValue
        )
        with(launchActivity<SampleActivity>(intent)) {
            withActivity {
                assertEquals(expectedValue, optionUserLambda)
            }
            recreate()
            withActivity {
                assertEquals(expectedValue, optionUserLambda)
                updateOptionUser(newValue)
                assertEquals(newValue, optionUserLambda)
            }
            recreate()
            withActivity {
                assertEquals(newValue, optionUserLambda)
            }
        }
    }
}
