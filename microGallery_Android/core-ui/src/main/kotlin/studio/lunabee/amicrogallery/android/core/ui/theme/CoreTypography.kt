package studio.lunabee.amicrogallery.android.core.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import studio.lunabee.amicrogallery.core.ui.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs,
)

val curlyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Comic Neue"),
        fontProvider = provider,
    ),
)

val sharpFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Oswald"),
        fontProvider = provider,
    ),
)

object CoreTypography {
    val header = TextStyle(
        fontFamily = sharpFontFamily,
        fontSize = 42.sp,
        fontWeight = FontWeight.W900,
    )
    val title = TextStyle(
        fontFamily = curlyFontFamily,
        fontSize = 22.sp,
        fontWeight = FontWeight.W700,
    )

    val action = TextStyle(
        fontFamily = sharpFontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.W600,
    )

    val labelBold = TextStyle(
        fontFamily = curlyFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.W600,
    )

    val bodyBold = TextStyle(
        fontFamily = curlyFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
    )

    val body = TextStyle(
        fontFamily = curlyFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
    )
}
