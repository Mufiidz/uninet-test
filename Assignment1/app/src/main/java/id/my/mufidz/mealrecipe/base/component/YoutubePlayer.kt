package id.my.mufidz.mealrecipe.base.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.regex.Pattern

@Composable
fun YouTubePlayer(
    url: String,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier,
) {
    val youtubeId = getYoutubeId(url)
    AndroidView(modifier = modifier, factory = {
        YouTubePlayerView(context = it).apply {
            lifecycleOwner.lifecycle.addObserver(this)

            val youTubePlayerListener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youtubeId?.let { id -> youTubePlayer.cueVideo(id, 0f) }
                }
            }
            addYouTubePlayerListener(youTubePlayerListener)
        }
    })
}

private fun getYoutubeId(youtubeUrl: String): String? {
    val regex =
        "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|(?:be-nocookie|be)\\.com\\/(?:watch|[\\w]+\\?(?:feature=[\\w]+.[\\w]+\\&)?v=|v\\/|e\\/|embed\\/|live\\/|shorts\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)"
    val pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(youtubeUrl)
    return if (matcher.find()) matcher.group(1) else null
}