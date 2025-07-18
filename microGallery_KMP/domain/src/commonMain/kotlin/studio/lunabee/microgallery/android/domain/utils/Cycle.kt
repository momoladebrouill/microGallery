package studio.lunabee.microgallery.android.domain.utils

fun <T> Array<T>.cycle(): Iterator<T> = object : Iterator<T> {
    private var index = 0
    override fun hasNext() = true // Always true
    override fun next(): T {
        val value = this@cycle[index]
        index = (index + 1) % this@cycle.size
        return value
    }
}