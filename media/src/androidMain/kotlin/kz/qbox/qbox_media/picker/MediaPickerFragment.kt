
package kz.qbox.qbox_media.picker

import android.annotation.SuppressLint
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kz.qbox.qbox_media.MediaFactory
import kz.qbox.qbox_media.Media

class MediaPickerFragment : Fragment() {

    private val codeCallbackMap = mutableMapOf<Int, CallbackData>()

    init {
        @Suppress("DEPRECATION")
        retainInstance = true
    }

    @SuppressLint("Range")
    private val mediaPicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        val callbackData = codeCallbackMap[0] ?: return@registerForActivityResult
        codeCallbackMap.remove(0)

        val callback = callbackData.callback

        if (uri == null) {
            callback.invoke(Result.failure(CanceledException()))
            return@registerForActivityResult
        }

        val context = this.context
        if (context == null) {
            callback(Result.failure(IllegalStateException("context unavailable")))
            return@registerForActivityResult
        }

        val result = kotlin.runCatching {
            MediaFactory.create(context, uri)
        }
        callback.invoke(result)
    }

    fun pickVideo(callback: (Result<Media>) -> Unit) {
        val requestCode = codeCallbackMap.keys.maxOrNull() ?: 0

        codeCallbackMap[requestCode] = CallbackData(callback)

        mediaPicker.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
        )
    }

    fun pickMedia(callback: (Result<Media>) -> Unit) {
        val requestCode = codeCallbackMap.keys.maxOrNull() ?: 0

        codeCallbackMap[requestCode] = CallbackData(callback)

        mediaPicker.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
        )
    }

    class CallbackData(val callback: (Result<Media>) -> Unit)
}
