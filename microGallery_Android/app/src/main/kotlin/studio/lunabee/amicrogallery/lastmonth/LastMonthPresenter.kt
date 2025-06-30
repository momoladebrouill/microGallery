package studio.lunabee.amicrogallery.lastmonth

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.lastMonth.LastMonthRepository
import java.time.LocalDate

class LastMonthPresenter(
    val lastMonthRepository: LastMonthRepository,
) : LBSinglePresenter<LastMonthUiState, LastMonthNavScope, LastMonthAction>() {

    override val flows: List<Flow<LastMonthAction>> = listOf()

    override fun initReducer(): LBSingleReducer<LastMonthUiState, LastMonthNavScope, LastMonthAction> {
        return LastMonthReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
        )
    }

    init {
        grabPicturesList()
    }

    fun grabPicturesList() {
        viewModelScope.launch {
            val now = LocalDate.now()
            val photos: List<Picture> = lastMonthRepository.getLastMonthPictures(
                year = now.year.toString(),
                month = "%02d".format(now.monthValue),
            )
            emitUserAction(LastMonthAction.GotTheList(photos))
        }
    }

    override fun getInitialState(): LastMonthUiState = LastMonthUiState(listOf<Picture>())

    override val content: @Composable (LastMonthUiState) -> Unit = { LastMonthScreen(it, fireAction = ::emitUserAction) }
}
