package com.pluu.sample.saved

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.pluu.sample.saved.model.UserDto
import com.pluu.sample.saved.utils.savedInject
import com.pluu.sample.saved.utils.savedInjectNonNull
import timber.log.Timber

class SampleActivity : ComponentActivity() {
    var requiredUser by savedInjectNonNull<UserDto>()
    var requiredUserLambda by savedInjectNonNull<UserDto> { requiredUserDto }

    var optionUser by savedInject<UserDto>()
    var optionUserLambda by savedInject<UserDto> { optionUserDto }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
    }

    fun updateUser(value: UserDto) {
        requiredUser = value
        requiredUserLambda = value
    }

    fun updateOptionUser(value: UserDto?) {
        optionUser = value
        optionUserLambda = value
    }
}
