package studio.lunabee.microgallery.android.data

// Node in the hierarchy tree
sealed class Node {
    abstract val name: String
    override fun toString(): String {
        return name
    }
}
