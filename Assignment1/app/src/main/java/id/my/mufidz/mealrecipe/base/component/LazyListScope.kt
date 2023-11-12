package id.my.mufidz.mealrecipe.base.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalLayoutApi::class)
inline fun <T> LazyListScope.itemsGridFlow(
    row: Int = Int.MAX_VALUE,
    items: List<T>,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) {
    item {
        FlowRow(modifier, horizontalArrangement, verticalArrangement, row) {
            items(items = items) { itemContent(it) }
        }
    }
}

fun <T> LazyListScope.visibilityContent(
    items: List<T>,
    content: LazyListScope.(List<T>) -> Unit
) {
    if (items.isNotEmpty()) {
        content(items)
    }
}