package studio.lunabee.microgallery.android.domain.photoviewer

class UrlIndex {
    private var _value: Int = 0

    operator fun plus(other: Int): Int {
        return _value + other
    }

    fun increment() {
        _value += 1
    }

    fun get(): Int {
        return _value
    }
}
