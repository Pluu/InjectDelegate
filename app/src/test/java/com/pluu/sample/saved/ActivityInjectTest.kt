package com.pluu.sample.saved

import androidx.activity.ComponentActivity
import com.pluu.sample.saved.model.UserDto
import com.pluu.sample.saved.utils.savedInject
import com.pluu.sample.saved.utils.savedInjectNonNull
import com.pluu.utils.buildIntent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

private val requiredUserDto = UserDto(id = 1, name = "Required Lambda")
private val optionUserDto = UserDto(id = 1, name = "Option Lambda")

@RunWith(RobolectricTestRunner::class)
class ActivityInjectTest {

    private val newValue = UserDto(id = 1234, name = "New User")

    @Test
    fun requiredWithError() {
        Robolectric.buildActivity(SampleActivity::class.java).use { controller ->
            controller.setup()
            with(controller.get()) {
                assertThrows(IllegalStateException::class.java) {
                    requiredUser
                }
            }
        }
    }

    @Test
    fun requiredWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val intent = RuntimeEnvironment.getApplication().buildIntent<SampleActivity>(
            "requiredUser" to expectedValue
        )
        Robolectric.buildActivity(SampleActivity::class.java, intent).use { controller ->
            controller.setup()
            with(controller.get()) {
                assertEquals(expectedValue, requiredUser)
            }

            with(controller.recreate().get()) {
                assertEquals(expectedValue, requiredUser)
                updateUser(newValue)
                assertEquals(newValue, requiredUser)
            }
            with(controller.recreate().get()) {
                assertEquals(newValue, requiredUser)
            }
        }
    }

    @Test
    fun requiredLambdaWithDefaultData() {
        Robolectric.buildActivity(SampleActivity::class.java).use { controller ->
            controller.setup()

            with(controller.get()) {
                assertEquals(requiredUserDto, requiredUserLambda)
            }

            with(controller.recreate().get()) {
                assertEquals(requiredUserDto, requiredUserLambda)
                updateUser(newValue)
                assertEquals(newValue, requiredUserLambda)
            }
            with(controller.recreate().get()) {
                assertEquals(newValue, requiredUserLambda)
            }
        }
    }

    @Test
    fun requiredLambdaWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val intent = RuntimeEnvironment.getApplication().buildIntent<SampleActivity>(
            "requiredUserLambda" to expectedValue
        )
        Robolectric.buildActivity(SampleActivity::class.java, intent).use { controller ->
            controller.setup()
            with(controller.get()) {
                assertEquals(expectedValue, requiredUserLambda)
            }

            with(controller.recreate().get()) {
                assertEquals(expectedValue, requiredUserLambda)
                updateUser(newValue)
                assertEquals(newValue, requiredUserLambda)
            }
            with(controller.recreate().get()) {
                assertEquals(newValue, requiredUserLambda)
            }
        }
    }

    @Test
    fun optionNull() {
        Robolectric.buildActivity(SampleActivity::class.java).use { controller ->
            controller.setup()
            with(controller.get()) {
                assertNull(optionUser)
            }

            with(controller.recreate().get()) {
                assertNull(optionUser)
                updateOptionUser(newValue)
                assertEquals(newValue, optionUser)
            }
            with(controller.recreate().get()) {
                assertEquals(newValue, optionUser)
            }
        }
    }

    @Test
    fun optionWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val intent = RuntimeEnvironment.getApplication().buildIntent<SampleActivity>(
            "optionUser" to expectedValue
        )
        Robolectric.buildActivity(SampleActivity::class.java, intent).use { controller ->
            controller.setup()
            with(controller.get()) {
                assertEquals(expectedValue, optionUser)
            }

            with(controller.recreate().get()) {
                assertEquals(expectedValue, optionUser)
                updateOptionUser(newValue)
                assertEquals(newValue, optionUser)
            }
            with(controller.recreate().get()) {
                assertEquals(newValue, optionUser)
            }
        }
    }

    @Test
    fun optionLambdaWithDefaultData() {
        Robolectric.buildActivity(SampleActivity::class.java).use { controller ->
            controller.setup()

            with(controller.get()) {
                assertEquals(optionUserDto, optionUserLambda)
            }

            with(controller.recreate().get()) {
                assertEquals(optionUserDto, optionUserLambda)
                updateOptionUser(newValue)
                assertEquals(newValue, optionUserLambda)
            }
            with(controller.recreate().get()) {
                assertEquals(newValue, optionUserLambda)
            }
        }
    }

    @Test
    fun optionLambdaWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val intent = RuntimeEnvironment.getApplication().buildIntent<SampleActivity>(
            "optionUserLambda" to expectedValue
        )
        Robolectric.buildActivity(SampleActivity::class.java, intent).use { controller ->
            controller.setup()

            with(controller.get()) {
                assertEquals(expectedValue, optionUserLambda)
            }

            with(controller.recreate().get()) {
                assertEquals(expectedValue, optionUserLambda)
                updateOptionUser(newValue)
                assertEquals(newValue, optionUserLambda)
            }
            with(controller.recreate().get()) {
                assertEquals(newValue, optionUserLambda)
            }
        }
    }
}

class SampleActivity : ComponentActivity() {
    var requiredUser by savedInjectNonNull<UserDto>()
    var requiredUserLambda by savedInjectNonNull<UserDto> { requiredUserDto }

    var optionUser by savedInject<UserDto>()
    var optionUserLambda by savedInject<UserDto> { optionUserDto }

    fun updateUser(value: UserDto) {
        requiredUser = value
        requiredUserLambda = value
    }

    fun updateOptionUser(value: UserDto?) {
        optionUser = value
        optionUserLambda = value
    }
}
