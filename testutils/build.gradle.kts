plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.pluu.sample.testutils"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    api(libs.androidx.test.core.ktx)
    api(libs.androidx.junit)
    api(libs.androidx.fragment.testing)
}