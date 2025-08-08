package studio.lunabee.amicrogallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.compose.rememberNavController
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme
import studio.lunabee.amicrogallery.android.core.ui.theme.coreEnableEdgeToEdge
import studio.lunabee.amicrogallery.snackbar.CoreSnackBarView

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            coreEnableEdgeToEdge(isSystemInDarkTheme())
            val navHostController = rememberNavController()
            MicroGalleryTheme {
                Box {
                    RootDrawer(
                        navHostController = navHostController,
                    )
                    Box(
                        modifier = Modifier
                            .zIndex(1f)
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .imePadding(),
                    ) {
                        CoreSnackBarView()
                    }
                }
            }
        }
    }
}
