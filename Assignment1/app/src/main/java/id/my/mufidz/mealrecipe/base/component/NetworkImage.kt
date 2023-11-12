package id.my.mufidz.mealrecipe.base.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) = CoilImage(
    imageModel = { url },
    modifier = modifier,
    imageOptions = ImageOptions(contentScale = contentScale),
    component = rememberImageComponent {
        +CrossfadePlugin(
            duration = 500
        )
        +ShimmerPlugin(
            baseColor = Color.Gray,
            highlightColor = Color.LightGray
        )
    }
)