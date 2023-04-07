
package kz.qbox.qbox_media.picker

import dev.icerock.moko.permissions.PermissionsController
import kz.qbox.qbox_media.Bitmap
import kz.qbox.qbox_media.FileMedia
import kz.qbox.qbox_media.Media

internal const val DEFAULT_MAX_IMAGE_WIDTH = 1024
internal const val DEFAULT_MAX_IMAGE_HEIGHT = 1024

expect interface MediaPickerController {
    val permissionsController: PermissionsController

    suspend fun pickImage(source: MediaSource): Bitmap
    suspend fun pickImage(source: MediaSource, maxWidth: Int, maxHeight: Int): Bitmap
    suspend fun pickMedia(): Media
    suspend fun pickFiles(): FileMedia
}
