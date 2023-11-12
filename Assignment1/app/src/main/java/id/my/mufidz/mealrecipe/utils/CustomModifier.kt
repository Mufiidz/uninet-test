package id.my.mufidz.mealrecipe.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp

fun Modifier.fillWidthOfParent(parentPadding: Dp) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(
            constraints.copy(
                maxWidth = constraints.maxWidth + 2 * parentPadding.roundToPx(),
            ),
        )
        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    },
)