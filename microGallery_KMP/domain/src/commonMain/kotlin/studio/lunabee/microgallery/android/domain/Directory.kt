package studio.lunabee.microgallery.android.domain

data class Directory(
    override val name: String,
    val content: List<Node>,
) : Node()
