package studio.lunabee.amicrogallery.utils

fun String.isInt() : Boolean{
    return this != ""  && all {it in "0123456789"}
}