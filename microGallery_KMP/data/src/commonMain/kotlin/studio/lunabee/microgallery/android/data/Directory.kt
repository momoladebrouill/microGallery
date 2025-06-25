package studio.lunabee.microgallery.android.data

data class Directory(
    override val name: String,
    val content: List<Node>,
) : Node()
