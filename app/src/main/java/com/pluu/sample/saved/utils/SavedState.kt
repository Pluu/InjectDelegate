@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER", "UNCHECKED_CAST")

package com.pluu.sample.saved.utils

import android.os.Bundle

fun <T> Bundle.get(key: String): T? {
    return get(key) as? T
}