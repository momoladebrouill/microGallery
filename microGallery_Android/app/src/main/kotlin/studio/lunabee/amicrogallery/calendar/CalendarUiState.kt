package studio.lunabee.amicrogallery.calendar

import android.provider.ContactsContract
import studio.lunabee.amicrogallery.calendar.displayed.Display
import studio.lunabee.amicrogallery.calendar.displayed.MonthDisplay
import studio.lunabee.amicrogallery.calendar.displayed.PhotoDisplay
import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.Node
import studio.lunabee.microgallery.android.data.Picture

data class CalendarUiState(
    val years : List<String>,
    val monthsOfYears : Map<String, List<String>>,
    val photosOfMonth : Map<Pair<String,String>, List<Picture>>,
    val expandedMonths : Set<Pair<String,String>>,
) : PresenterUiState {
    fun getMonthsFromYear(year : String) : List<String> {
        return monthsOfYears.getOrDefault(year, listOf())

    }

    fun getPhotosFromMonth(year : String, month : String) : List<Picture>{
        return photosOfMonth.getOrDefault(Pair(year,month), listOf())
    }

    fun getItemsToShow(year : String) : List<Display>{
        return monthsOfYears
            .getOrDefault(year,listOf<String>())
            .map {month -> listOf(MonthDisplay(month)) +
                if(Pair(year,month) in expandedMonths)
                    photosOfMonth.getOrDefault(Pair(year,month),listOf()).map { PhotoDisplay(it)}
                else listOf()}
            .flatten()
    }

}
