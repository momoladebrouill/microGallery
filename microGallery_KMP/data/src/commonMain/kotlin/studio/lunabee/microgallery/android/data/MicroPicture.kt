package studio.lunabee.microgallery.android.data

data class MicroPicture(
    val id: Long,
    val name: String,
    val year: MYear,
    val month: MMonth,
    val highResPaths: List<String>,
    val lowResPaths: List<String>,
)
