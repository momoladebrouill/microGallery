package studio.lunabee.amicrogallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.navigation.compose.rememberNavController
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme
import studio.lunabee.amicrogallery.android.core.ui.theme.coreEnableEdgeToEdge



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            coreEnableEdgeToEdge(isSystemInDarkTheme())
            val navHostController = rememberNavController()
            MicroGalleryTheme {
                RootDrawer(
                    navHostController = navHostController,
                )
            }
        }
    }
}
