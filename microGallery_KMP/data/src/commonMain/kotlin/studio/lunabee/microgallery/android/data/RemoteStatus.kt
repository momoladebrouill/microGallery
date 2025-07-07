package studio.lunabee.microgallery.android.data

data class RemoteStatus(
    val isPlugged: Boolean, // is usb disk plugged
    val temperature: Int, // temperature of CPU
    val quantityHighRes: Int, // number of Pictures in highRes
    val quantityLowRes: Int, // number of Pictures in lowRes
)
