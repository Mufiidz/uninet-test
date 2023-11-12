package id.my.mufidz.mealrecipe.base.component

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.runtime.Composable

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> FlowRowScope.items(
    items: List<T>,
    itemContent: @Composable FlowRowScope.(value: T) -> Unit
) {
    repeat(items.size) {
        itemContent(items[it])
    }
}