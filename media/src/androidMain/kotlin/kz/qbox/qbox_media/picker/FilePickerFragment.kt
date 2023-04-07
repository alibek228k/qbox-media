
package kz.qbox.qbox_media.picker

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.OpenableColumns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kz.qbox.qbox_media.FileMedia
import java.io.File

class FilePickerFragment : Fragment() {
    init {
        @Suppress("DEPRECATION")
        retainInstance = true
    }

    private val codeCallbackMap = mutableMapOf<Int, CallbackData>()

    @SuppressLint("Range")
    private val pickDocument =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            val callbackData = codeCallbackMap[0] ?: return@registerForActivityResult
            codeCallbackMap.remove(0)

            val callback = callbackData.callback

            if (uri == null) {
                callback.invoke(Result.failure(CanceledException()))
                return@registerForActivityResult
            }

            if (uri.path == null) {
                callback.invoke(Result.failure(java.lang.IllegalStateException("File is null")))
                return@registerForActivityResult
            }

            val fileNameWithExtension = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
                val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
                cursor?.use {
                    if (!it.moveToFirst()) null
                    else it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } else null

            uri.path?.let { path ->
                val file = File(path)
                val name = file.name
                val byteArray = requireContext()
                    .contentResolver
                    .openInputStream(uri)
                    ?.readBytes() ?: ByteArray(0)

                callback(
                    Result.success(
                        FileMedia(
                            fileNameWithExtension ?: name,
                            uri.toString(),
                            byteArray
                        )
                    )
                )
            }
        }


    fun pickFile(callback: (Result<FileMedia>) -> Unit) {
        val requestCode = codeCallbackMap.keys.maxOrNull() ?: 0

        codeCallbackMap[requestCode] = CallbackData(callback)

        pickDocument.launch(
            arrayOf(
                "application/pdf",
                "application/octet-stream",
                "application/doc",
                "application/msword",
                "application/ms-doc",
                "application/vnd.ms-excel",
                "application/vnd.ms-powerpoint",
                "application/json",
                "application/zip",
                "text/plain",
                "text/html",
                "text/xml",
                "audio/mpeg",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            )
        )
    }

    class CallbackData(val callback: (Result<FileMedia>) -> Unit)
}
