package id.my.mufidz.authtest.base.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun EditTextField(
    value: String,
    modifier: Modifier = Modifier,
    label: String = "",
    hint: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value,
        onValueChange,
        modifier,
        label = { Text(label) },
        placeholder = { Text(hint) },
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        singleLine = true,
        visualTransformation = visualTransformation,
        isError = isError
    )
}