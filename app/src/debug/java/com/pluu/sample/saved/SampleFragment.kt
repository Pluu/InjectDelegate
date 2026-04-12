package com.pluu.sample.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.pluu.sample.saved.model.UserDto
import com.pluu.sample.saved.utils.savedInject
import com.pluu.sample.saved.utils.savedInjectNonNull
import timber.log.Timber

class SampleFragment : Fragment() {
    var requiredUser by savedInjectNonNull<UserDto>()
    var requiredUserLambda by savedInjectNonNull<UserDto> { requiredUserDto }

    var optionUser by savedInject<UserDto>()
    var optionUserLambda by savedInject<UserDto> { optionUserDto }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
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
