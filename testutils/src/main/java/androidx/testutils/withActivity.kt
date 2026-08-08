package androidx.testutils

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import java.io.Closeable

/** Run [block] using [ActivityScenario.onActivity], returning the result of the block. */
inline fun <reified A : Activity, T : Any> ActivityScenarioRule<A>.withActivity(
    crossinline block: A.() -> T
): T = scenario.withActivity(block)

/** Run [block] using [ActivityScenario.onActivity], returning the result of the block. */
inline fun <reified A : Activity, T : Any> ActivityScenario<A>.withActivity(
    crossinline block: A.() -> T
): T {
    lateinit var value: T
    var err: Throwable? = null
    onActivity { activity ->
        try {
            value = block(activity)
        } catch (t: Throwable) {
            err = t
        }
    }
    err?.let { throw it }
    return value
}

inline fun <reified A : FragmentActivity> ActivityScenario<A>.waitForExecution(cycles: Int = 2) {
    try {
        for (i in 0 until cycles) {
            withActivity {}
        }
    } catch (throwable: Throwable) {
        throw RuntimeException(throwable)
    }
}

/**
 * Run [block] in a [use] block when using [ActivityScenario.launch], rather than just a [with]
 * block to ensure the Activity is closed once test is complete.
 */
fun <C : Closeable> withUse(closeable: C, block: C.() -> Unit) {
    closeable.use { block(it) }
}
