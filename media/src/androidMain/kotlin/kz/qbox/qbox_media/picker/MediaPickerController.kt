
package kz.qbox.qbox_media.picker

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import dev.icerock.moko.permissions.PermissionsController
import kz.qbox.qbox_media.Bitmap
import kz.qbox.qbox_media.FileMedia
import kz.qbox.qbox_media.Media

actual interface MediaPickerController {
    actual val permissionsController: PermissionsController

    actual suspend fun pickImage(source: MediaSource): Bitmap
    actual suspend fun pickImage(source: MediaSource, maxWidth: Int, maxHeight: Int): Bitmap
    actual suspend fun pickMedia(): Media
    actual suspend fun pickFiles(): FileMedia

    fun bind(lifecycle: Lifecycle, fragmentManager: FragmentManager)

    companion object {
        operator fun invoke(
            permissionsController: PermissionsController,
            pickerFragmentTag: String = "MediaControllerPicker",
            filePickerFragmentTag: String = "FileMediaControllerPicker"
        ): MediaPickerController {
            return MediaPickerControllerImpl(
                permissionsController = permissionsController,
                pickerFragmentTag = pickerFragmentTag,
                filePickerFragmentTag = filePickerFragmentTag
            )
        }
    }
}
