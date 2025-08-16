package com.pluu.sample.saved

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.withFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pluu.sample.saved.model.UserDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith

private val requiredUserDto = UserDto(id = 1, name = "Required Lambda")
private val optionUserDto = UserDto(id = 1, name = "Option Lambda")

@RunWith(AndroidJUnit4::class)
class FragmentInjectTest {

    private val newValue = UserDto(id = 1234, name = "New User")

    @Test
    fun requiredWithError() {
        val scenario = FragmentScenario.launch(
            fragmentClass = SampleFragment::class.java
        )
        scenario.withFragment {
            assertThrows(IllegalStateException::class.java) {
                requiredUser
            }
        }
    }

    @Test
    fun requiredWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val fragmentArgs = bundleOf(
            "requiredUser" to expectedValue
        )
        val scenario = FragmentScenario.launch(
            fragmentClass = SampleFragment::class.java,
            fragmentArgs = fragmentArgs
        )
        scenario.withFragment {
            assertEquals(expectedValue, requiredUser)
        }

        scenario.recreate().withFragment {
            assertEquals(expectedValue, requiredUser)
            updateUser(newValue)
            assertEquals(newValue, requiredUser)
        }

        scenario.recreate().withFragment {
            assertEquals(newValue, requiredUser)
        }
    }

    @Test
    fun requiredLambdaWithDefaultData() {
        val scenario = FragmentScenario.launch(
            fragmentClass = SampleFragment::class.java
        )
        scenario.withFragment {
            assertEquals(requiredUserDto, requiredUserLambda)
        }

        scenario.recreate().withFragment {
            assertEquals(requiredUserDto, requiredUserLambda)
            updateUser(newValue)
            assertEquals(newValue, requiredUserLambda)
        }
        scenario.recreate().withFragment {
            assertEquals(newValue, requiredUserLambda)
        }
    }

    @Test
    fun requiredLambdaWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val fragmentArgs = bundleOf(
            "requiredUserLambda" to expectedValue
        )
        val scenario = FragmentScenario.launch(
            fragmentClass = SampleFragment::class.java,
            fragmentArgs = fragmentArgs
        )
        scenario.withFragment {
            assertEquals(expectedValue, requiredUserLambda)
        }

        scenario.recreate().withFragment {
            assertEquals(expectedValue, requiredUserLambda)
            updateUser(newValue)
            assertEquals(newValue, requiredUserLambda)
        }
        scenario.recreate().withFragment {
            assertEquals(newValue, requiredUserLambda)
        }
    }

    @Test
    fun optionNull() {
        val scenario = FragmentScenario.launch(
            fragmentClass = SampleFragment::class.java
        )
        scenario.withFragment {
            assertNull(optionUser)
        }

        scenario.recreate().withFragment {
            assertNull(optionUser)
            updateOptionUser(newValue)
            assertEquals(newValue, optionUser)
        }
        scenario.recreate().withFragment {
            assertEquals(newValue, optionUser)
        }
    }

    @Test
    fun optionWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val fragmentArgs = bundleOf(
            "optionUser" to expectedValue
        )
        val scenario = FragmentScenario.launch(
            fragmentClass = SampleFragment::class.java,
            fragmentArgs = fragmentArgs
        )
        scenario.withFragment {
            assertEquals(expectedValue, optionUser)
        }

        scenario.recreate().withFragment {
            assertEquals(expectedValue, optionUser)
            updateOptionUser(newValue)
            assertEquals(newValue, optionUser)
        }
        scenario.recreate().withFragment {
            assertEquals(newValue, optionUser)
        }
    }

    @Test
    fun optionLambdaWithDefaultData() {
        val scenario = FragmentScenario.launch(
            fragmentClass = SampleFragment::class.java
        )
        scenario.withFragment {
            assertEquals(optionUserDto, optionUserLambda)
        }

        scenario.recreate().withFragment {
            assertEquals(optionUserDto, optionUserLambda)
            updateOptionUser(newValue)
            assertEquals(newValue, optionUserLambda)
        }
        scenario.recreate().withFragment {
            assertEquals(newValue, optionUserLambda)
        }
    }

    @Test
    fun optionLambdaWithInitData() {
        val expectedValue = UserDto(id = 1, name = "abcd")

        val fragmentArgs = bundleOf(
            "optionUserLambda" to expectedValue
        )
        val scenario = FragmentScenario.launch(
            fragmentClass = SampleFragment::class.java,
            fragmentArgs = fragmentArgs
        )
        scenario.withFragment {
            assertEquals(expectedValue, optionUserLambda)
        }

        scenario.recreate().withFragment {
            assertEquals(expectedValue, optionUserLambda)
            updateOptionUser(newValue)
            assertEquals(newValue, optionUserLambda)
        }
        scenario.recreate().withFragment {
            assertEquals(newValue, optionUserLambda)
        }
    }
}

