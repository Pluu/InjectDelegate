package com.pluu.sample.saved

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.pluu.sample.saved.model.UserDto
import com.pluu.sample.saved.utils.savedInject
import com.pluu.utils.buildIntent
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class InjectTest {
    @Test
    fun defaultCheck() {
        Robolectric.buildActivity(SampleActivity::class.java).use { controller ->
            controller.setup()
            val expectedValue = UserDto(id = 999, name = "Default User")

            with(controller.get()) {
                assertEquals(expectedValue, user)
            }

            val newValue = UserDto(id = 200, name = "New User")
            with(controller.recreate().get()) {
                assertEquals(expectedValue, user)

                updateUser(newValue)
                assertEquals(newValue, user)
            }
            with(controller.recreate().get()) {
                assertEquals(newValue, user)
            }
        }
    }

    @Test
    fun intentCheck() {
        val expectedValue = UserDto(id = 200, name = "Intent User")

        val intent = RuntimeEnvironment.getApplication().buildIntent<SampleActivity>(
            "user" to expectedValue
        )
        Robolectric.buildActivity(SampleActivity::class.java, intent).use { controller ->
            controller.setup()
            with(controller.get()) {
                assertEquals(expectedValue, user)
            }

            with(controller.recreate().get()) {
                assertEquals(expectedValue, user)
            }
        }
    }
}

class SampleActivity : ComponentActivity() {
    var user by savedInject {
        UserDto(id = 999, name = "Default User")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun updateUser(value: UserDto) {
        this.user = value
    }
}
