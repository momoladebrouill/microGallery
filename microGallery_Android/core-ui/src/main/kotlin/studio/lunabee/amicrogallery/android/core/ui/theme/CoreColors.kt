package studio.lunabee.amicrogallery.android.core.ui.theme

import androidx.compose.ui.graphics.Color

object CoreColorPalette {
    val darkGreen = Color(0xFF797D62)
    val lightGreen = Color(0xFF9B9B7A)
    val softPink = Color(0xFFD9AE94)
    val warmYellow = Color(0xFFF1DCA7)
    val mustardYellow = Color(0xFFFFCB69)
    val milkChocolateBrown = Color(0XFFD08C60)
    val greyBrown = Color(0xFF997B66)
}

interface CoreColorTheme {
    val main: Color
    val background: Color
    val onMain: Color
    val onBackground: Color
    val disabled: Color
}

object CoreColorLightTheme : CoreColorTheme {
    override val main: Color = CoreColorPalette.greyBrown
    override val background: Color = CoreColorPalette.warmYellow
    override val onMain: Color = CoreColorPalette.mustardYellow
    override val onBackground: Color = CoreColorPalette.greyBrown
    override val disabled: Color = CoreColorPalette.softPink
}

object CoreColorDarkTheme : CoreColorTheme {
    override val main: Color = CoreColorPalette.darkGreen
    override val background: Color = CoreColorPalette.greyBrown
    override val onMain: Color
        get() = CoreColorPalette.lightGreen
    override val onBackground: Color
        get() = CoreColorPalette.milkChocolateBrown
    override val disabled: Color
        get() = CoreColorPalette.greyBrown
}
