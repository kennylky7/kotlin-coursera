package playground

fun filterNonZero(list: List<Int>) = list.filter { it != 0 }

fun filterNonZeroGenerated(list: List<Int>): List<Int> {
    TODO()
}

fun main(args: Array<String>) {
    val list = listOf(1, 2, 3)

    println(filterNonZero(list).toString() == "[1, 2, 3]")
    println(filterNonZeroGenerated(list).toString()  == "[1, 2, 3]")
}

inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> {
    val destination = ArrayList<T>()
    for (element in this) {
        if (predicate(element)) {
            destination.add(element)
        }
    }
    return destination
}