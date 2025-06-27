package studio.lunabee.microgallery.android.data

data class Picture(
    override val name: String,
    val id: Long?,
    val fullResPath: String?,
    val lowResPath: String?,
    val year: String?,
    val month: String?
) : Node()
