package studio.lunabee.microgallery.android.data

data class Picture(
    override val name: String,
    val id: Long = 0,
    val fullResPath: String?,
    val lowResPath: String?,
    val year: MYear?,
    val month: MMonth?,
) : Node()
