package com.pluu.sample.saved.utils

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.savedstate.SavedState
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.read
import androidx.savedstate.savedState
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T> ComponentActivity.savedInject(
    noinline init: () -> T? = { null }
): ReadWriteProperty<Any?, T?> =
    InjectDelegate(savedStateRegistry, { intent?.extras }, init)

inline fun <reified T> Fragment.savedInject(
    noinline init: () -> T? = { null }
): ReadWriteProperty<Any?, T?> =
    InjectDelegate(savedStateRegistry, { arguments }, init)

inline fun <reified T : Any> ComponentActivity.savedInjectNonNull(
    noinline init: () -> T = { throw IllegalStateException(">>>>") }
): ReadWriteProperty<Any?, T> =
    InjectDelegate(savedStateRegistry, { intent?.extras }, init)

inline fun <reified T : Any> Fragment.savedInjectNonNull(
    noinline init: () -> T = { throw IllegalStateException(">>>>") }
): ReadWriteProperty<Any?, T> =
    InjectDelegate(savedStateRegistry, { arguments }, init)

/**
 * [SavedStateRegistry]에 값을 저장하고 복원할 수 있도록 하는 프로퍼티 위임입니다.
 *
 * 이 위임은 Activity 및 Fragment와 같은 Android Component와 함께 작동하도록 설계되었습니다.
 * Configuration change 또는 프로세스 종료 시 상태를 저장하고 복원합니다.
 *
 * @param T 값의 유형.
 * @property registry 상태를 저장하고 복원하는 데 사용할 [SavedStateRegistry] 인스턴스
 * @property bundle [SavedState]에서 값을 찾을 수 없는 경우, 검색할 수 있는 초기 [Bundle] (예: Activity의 Intent extras / Fragment의 arguments)을 제공하는 lambda 함수
 * @property init 찾을 수 없는 경우, 기본 초기 값을 제공하는 lambda 함수
 */
class InjectDelegate<T>(
    private val registry: SavedStateRegistry,
    private val bundle: () -> Bundle?,
    private val init: () -> T?,
) : ReadWriteProperty<Any?, T>, SavedStateRegistry.SavedStateProvider {

    private object UNINITIALIZED

    private var cachedKey: String = ""
    private var cachedValue: Any? = UNINITIALIZED

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (cachedValue == UNINITIALIZED) {
            val qualifiedKey = getQualifiedKey(property)
            registry.registerSavedStateProvider(qualifiedKey, provider = this)
            cachedKey = qualifiedKey
            cachedValue = loadInitialValue(qualifiedKey)
        }
        @Suppress("UNCHECKED_CAST")
        return cachedValue as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (cachedValue == UNINITIALIZED) {
            val qualifiedKey = getQualifiedKey(property)
            registry.registerSavedStateProvider(qualifiedKey, provider = this)
            cachedKey = qualifiedKey
        }
        cachedValue = value
    }

    override fun saveState(): SavedState {
        // Don't save anything if the value was never even accessed.
        if (cachedValue == UNINITIALIZED) return savedState()

        // Using `putNull` distinguishes a saved `null` from a state that was never saved.
        @Suppress("UNCHECKED_CAST")
        val typedValue = cachedValue as? T ?: return savedState { putNull(key = "") }

        return bundleOf(cachedKey to typedValue)
    }

    private fun loadInitialValue(qualifiedKey: String): T? {
        val restored = registry.consumeRestoredStateForKey(qualifiedKey)
        if (restored == null) {
            @Suppress("UNCHECKED_CAST")
            return (bundle()?.get<T>(qualifiedKey) ?: init())
        }

        // Check for the special marker used in `saveState()` for a `null` value.
        if (restored.read { isNull(key = "") && size() == 1 }) return null

        @Suppress("UNCHECKED_CAST")
        return restored.get<T>(qualifiedKey) ?: init()
    }

    /**
     * Generates a unique key for the property to avoid collisions in the [SavedStateRegistryOwner].
     */
    private fun getQualifiedKey(property: KProperty<*>): String {
        return property.name
    }
}