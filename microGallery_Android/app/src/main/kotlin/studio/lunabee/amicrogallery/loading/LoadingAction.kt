package studio.lunabee.amicrogallery.loading

import com.lunabee.lbcore.model.LBResult
import studio.lunabee.microgallery.android.domain.Node

sealed interface LoadingAction {
    class FoundData() : LoadingAction
    class Reload() : LoadingAction
    data class Error(val errorMessage: String?) : LoadingAction
}
