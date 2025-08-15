package com.pluu.sample.saved

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pluu.sample.saved.model.UserDto
import com.pluu.sample.saved.ui.theme.SavedSampleTheme
import com.pluu.sample.saved.utils.savedInject
import com.pluu.sample.saved.utils.savedInjectNonNull
import com.pluu.utils.startActivity
import timber.log.Timber

class VariableTestActivity : ComponentActivity() {
    private lateinit var beforeRequiredUser: UserDto
    private var requiredUser by savedInjectNonNull<UserDto>()

    private lateinit var beforeRequiredUserLambda: UserDto
    private var requiredUserLambda by savedInjectNonNull<UserDto> {
        UserDto(id = 2, name = "lambda")
    }

    private var beforeOptionUser: UserDto? = null
    private var optionUser by savedInject<UserDto>()

    private var beforeOptionUserLambda: UserDto? = null
    private var optionUserLambda by savedInject<UserDto> {
        UserDto(id = 3, name = "option lambda")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("Before")
        beforeRequiredUser = requiredUser
        beforeRequiredUserLambda = requiredUserLambda
        beforeOptionUser = optionUser
        beforeOptionUserLambda = optionUserLambda

        if (savedInstanceState == null) {
            Timber.d("Update new data")
            requiredUser = UserDto(id = 200, name = "new user")
            requiredUserLambda = requiredUser
            optionUser = null
            optionUserLambda = null
        }

        enableEdgeToEdge()
        setContent {
            SavedSampleTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Sample(
                        title = "Required",
                        before = "$beforeRequiredUser",
                        after = "$requiredUser"
                    )
                    Sample(
                        title = "Required Lambda (default)",
                        before = "$beforeRequiredUserLambda",
                        after = "$requiredUserLambda"
                    )
                    Sample(
                        title = "Optional",
                        before = "$beforeOptionUser",
                        after = "$optionUser"
                    )
                    Sample(
                        title = "Optional Lambda (default)",
                        before = "$beforeOptionUserLambda",
                        after = "$optionUserLambda"
                    )
                }
            }
        }
    }

    @Composable
    private fun Sample(title: String, before: String, after: String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "Before\n$before",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "After\n$after",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }

    }

    companion object {
        fun start(context: Context) {
            context.startActivity<VariableTestActivity>(
                "requiredUser" to UserDto(id = 1, name = "required")
            )
        }
    }
}