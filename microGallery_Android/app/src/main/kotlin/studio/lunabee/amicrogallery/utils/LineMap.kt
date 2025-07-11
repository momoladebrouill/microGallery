package studio.lunabee.amicrogallery.utils

class LineMap<V>(
    private val forward: Map<Float, V> = mapOf(),
    private val backward: Map<V, Float> = mapOf(),
) {
    operator fun plus(map: Pair<Float, V>?): LineMap<V> {
        if (map == null) {
            return this
        }
        val key = map.first
        val value = map.second
        val newForward = forward + (key to value)
        return LineMap(
            newForward,
            backward + (value to key),
        )
    }

    operator fun minus(valueToRemove: V): LineMap<V> {
        return LineMap(
            forward = forward.filter { (key, value) -> value != valueToRemove },
            backward = backward.filter { (key, value) -> key != valueToRemove },
        )
    }

    fun contains(key: Float): Boolean {
        return forward.contains(key)
    }

    fun valuesOrdered(): List<Float> {
        return forward.keys.sorted()
    }

    fun justAfter(key: Float): Float {
        val valuesSorted = valuesOrdered()
        assert(key in valuesSorted)
        val indexOfKey = valuesSorted.indexOf(key)
        return when (indexOfKey) {
            valuesSorted.size - 1 -> valuesSorted[indexOfKey] + 1.0f
            else -> (valuesSorted[indexOfKey + 1] + valuesSorted[indexOfKey]) / 2.0f
        }
    }

    fun areFollowing(keyA: Float, keyB: Float): Boolean { // they are following if no one is in between
        return !forward.keys.any { keyA < it && it < keyB }
    }

    operator fun get(key: Float): V? = forward[key]
    fun getByValue(value: V): Float? = backward[value]
}

fun <V> emptyLineMap(): LineMap<V> {
    return LineMap(emptyMap(), emptyMap())
}
