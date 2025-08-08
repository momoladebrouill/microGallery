package studio.lunabee.amicrogallery.settings

import coil3.imageLoader
import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.snackbar.CoreSnackBarData
import studio.lunabee.amicrogallery.snackbar.SnackBarManager
import studio.lunabee.amicrogallery.snackbar.SnackbarType
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.core.LbcTextSpec.*
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.settings.usecase.EmptyPhotoDbUseCase
import studio.lunabee.microgallery.android.domain.settings.usecase.SetSettingsUseCase
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository
import studio.lunabee.microgallery.android.domain.status.usecase.SetStatusUseCase

class SettingsReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (SettingsAction) -> Unit,
    val settingsRepository: SettingsRepository,
    val setStatusUseCase: SetStatusUseCase,
    val snackBarManager: SnackBarManager,
    val setSettingsUseCase: SetSettingsUseCase,
    val emptyPhotoDbUseCase: EmptyPhotoDbUseCase,
) : LBSingleReducer<SettingsUiState, SettingsNavScope, SettingsAction>() {

    override suspend fun reduce(
        actualState: SettingsUiState,
        action: SettingsAction,
        performNavigation: (SettingsNavScope.() -> Unit) -> Unit,
    ): ReduceResult<SettingsUiState> {
        return when (action) {
            is SettingsAction.JumpBack -> actualState withSideEffect {
                setSettingsUseCase(actualState.data)
                performNavigation {
                    jumpBack()
                }
            }

            SettingsAction.JumpDashBoard -> actualState withSideEffect {
                setSettingsUseCase(actualState.data)
                performNavigation {
                    jumpDashBoard()
                }
            }

            SettingsAction.JumpUntimed -> actualState withSideEffect {
                setSettingsUseCase(actualState.data)
                performNavigation {
                    jumpUntimed()
                }
            }

            is SettingsAction.ToggleIpv6 -> actualState.copy(
                data = actualState.data.copy(
                    useIpv6 = !(actualState.data.useIpv6),
                ),
            ).asResult()

            is SettingsAction.ToggleViewInHD -> actualState.copy(
                data = actualState.data.copy(
                    viewInHD = !actualState.data.viewInHD,
                ),
            ).asResult()

            is SettingsAction.Clear -> actualState withSideEffect {
                snackBarManager.showSnackBar(
                    CoreSnackBarData(
                        message = StringResource(R.string.clearing_cache),
                        type = SnackbarType.Default,
                    ),
                )
                val imageLoader = action.context.imageLoader
                imageLoader.memoryCache?.clear()
                emptyPhotoDbUseCase()
                emitUserAction(SettingsAction.JumpDashBoard)
            }

            is SettingsAction.GotRemoteStatus -> actualState.copy(remoteStatus = action.status).asResult()

            is SettingsAction.SetIpv4 -> actualState.copy(data = actualState.data.copy(ipv4 = action.ipv4)).asResult()
            is SettingsAction.SetIpv6 -> actualState.copy(data = actualState.data.copy(ipv6 = action.ipv6)).asResult()
            is SettingsAction.GotData ->
                actualState.copy(data = action.data).asResult()

            SettingsAction.ResetSettings -> actualState withSideEffect {
                setSettingsUseCase(SettingsData())
            }

            SettingsAction.GetRemote -> actualState withSideEffect {
                setStatusUseCase()
            }
        }
    }
}
