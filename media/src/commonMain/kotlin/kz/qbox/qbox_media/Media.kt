package kz.qbox.qbox_media

data class Media constructor(
    val name: String,
    val path: String,
    val preview: Bitmap,
    val type: MediaType
)
