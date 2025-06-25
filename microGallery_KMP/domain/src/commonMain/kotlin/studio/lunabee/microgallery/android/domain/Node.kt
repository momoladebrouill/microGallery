package studio.lunabee.microgallery.android.domain

// Node in the hierarchy tree
sealed class Node {
    abstract val name: String
    override fun toString(): String {
        return name
    }
}
