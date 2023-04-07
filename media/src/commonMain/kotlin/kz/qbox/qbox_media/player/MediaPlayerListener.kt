
package kz.qbox.qbox_media.player

interface MediaPlayerListener {
    fun onReady()
    fun onVideoCompleted()
    // TODO HIGH add onPlay, onPause events
}
