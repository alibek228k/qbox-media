package kz.qbox.qbox_media

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform