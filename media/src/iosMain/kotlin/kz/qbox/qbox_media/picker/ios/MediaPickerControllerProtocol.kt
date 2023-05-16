package kz.qbox.qbox_media.picker.ios

import dev.icerock.moko.permissions.PermissionsController
import kz.qbox.qbox_media.picker.MediaSource
import kz.qbox.qbox_media.Bitmap
import kz.qbox.qbox_media.FileMedia
import kz.qbox.qbox_media.Media

interface MediaPickerControllerProtocol {
    val permissionsController: PermissionsController

    suspend fun pickImage(source: MediaSource): Bitmap
    suspend fun pickImage(source: MediaSource, maxWidth: Int, maxHeight: Int): Bitmap
    suspend fun pickMedia(): Media
    suspend fun pickFiles(): FileMedia
}
