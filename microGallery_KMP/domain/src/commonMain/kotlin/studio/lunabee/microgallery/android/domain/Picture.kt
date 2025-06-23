package studio.lunabee.microgallery.android.domain

data class Picture(
    override val name : String,
    val fullPath : String?
): Node()