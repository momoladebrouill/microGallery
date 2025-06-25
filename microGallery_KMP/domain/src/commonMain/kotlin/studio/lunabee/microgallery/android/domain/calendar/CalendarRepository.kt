package studio.lunabee.microgallery.android.domain.calendar

import studio.lunabee.microgallery.android.domain.Node

interface CalendarRepository {
    suspend fun fetchRootNode(): Node
    suspend fun getRootNode() : Node
}
