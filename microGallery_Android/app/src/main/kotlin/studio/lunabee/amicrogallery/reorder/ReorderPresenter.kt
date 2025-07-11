package studio.lunabee.amicrogallery.reorder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.utils.emptyLineMap

import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer

class ReorderPresenter(
    savedStateHandle: SavedStateHandle,
) : LBSinglePresenter<ReorderUiState, ReorderNavScope, ReorderAction>() {

    private val params: ReorderDestination = savedStateHandle.toRoute()

    override val flows: List<Flow<ReorderAction>> = listOf()

    override fun initReducer(): LBSingleReducer<ReorderUiState, ReorderNavScope, ReorderAction> {
        return ReorderReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction
        )
    }

    override fun getInitialState(): ReorderUiState = ReorderUiState(
        picturesNotPlaced = setOf(
            "https://services.google.com/fh/files/misc/qq10.jpeg",
            "https://services.google.com/fh/files/misc/qq9.jpeg",
            "https://services.google.com/fh/files/misc/qq8.jpeg",
            "https://pethelpful.com/.image/w_3840,q_auto:good,c_fill,ar_4:3/MTk2NzY3MjA5ODc0MjY5ODI2/top-10-cutest-cat-photos-of-all-time.jpg",
            "https://i.ytimg.com/vi/vH8kYVahdrU/maxresdefault.jpg"
        ),
        picturesQQty = 5,
        putPicture = fun (index : Float, url : String){emitUserAction(ReorderAction.PutPicture(index, url))},
        picturesInSlots = emptyLineMap<String?>() + (0.0f to null),
    )


    @RequiresApi(Build.VERSION_CODES.N)
    override val content: @Composable (ReorderUiState) -> Unit = { ReorderScreen(it) }
}