package studio.lunabee.amicrogallery.utils

import kotlin.random.Random

fun durstenfeld(n: Int): Pair<Int, Int> = when (n) {
    1 -> Pair(0, 1)
    else -> {
        val (prec, precPrec) = durstenfeld(n - 1)
        Pair((n - 1) * (prec + precPrec), prec)
    }
}

fun dSeries(n: Int): Int = if (n == 0) 1 else durstenfeld(n).first

fun <T> swap(tab: Array<T>, a: Int, b: Int) {
    val temp = tab[a]
    tab[a] = tab[b]
    tab[b] = temp
}

fun randomDerangement(n: Int): Array<Int> {
    val tab = Array(n) { it }
    val mark = mutableSetOf<Int>()
    var i = n - 1
    var u = n - 1
    while (u >= 2) {
        if (i !in mark) {
            var j = Random.nextInt(0, i - 1)
            while (j in mark) j = Random.nextInt(0, i - 1)
            swap(tab, i, j)
            val p = Random.nextFloat()
            if (p < (u - 1) * dSeries(u - 2).toFloat() / dSeries(u).toFloat()) {
                mark.add(j)
                u--
            }
            u--
        }
        i--
    }
    return tab
}

inline fun <reified T> derange(a: Array<T>): Array<T> {
    val perm = randomDerangement(a.size)
    return Array(a.size) { a[perm[it]] }
}
