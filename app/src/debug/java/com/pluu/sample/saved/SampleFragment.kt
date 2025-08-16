package com.pluu.sample.saved

import androidx.fragment.app.Fragment
import com.pluu.sample.saved.model.UserDto
import com.pluu.sample.saved.utils.savedInject
import com.pluu.sample.saved.utils.savedInjectNonNull

class SampleFragment : Fragment() {
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
