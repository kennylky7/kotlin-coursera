package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */

/*
    You can use the following algorithm to check the given permutation.

    Let P is a permutation function on a range of numbers 1..n.
    For a pair (i, j) of elements such that i < j , if P(i) > P(j),
    then the permutation is said to invert the order of (i, j).
    The number of such inverted pairs is the parity of the permutation.

    If permutation inverts even number of such pairs, it is an even permutation;
    else it is an odd permutation.

    basically insertion sort
*/

fun isEven(permutation: List<Int>): Boolean {
    val array = permutation.toMutableList()

    var invertedPosNum = 0
    for (current in 1 until array.size) {
        var prev = current - 1
        val currentValue = array[current]

        while (prev >= 0 && array[prev] > currentValue) {
            array[prev + 1] = array[prev]
            invertedPosNum++
            prev--
        }
        array[prev + 1] = currentValue
    }

    println(array)

    return invertedPosNum % 2 == 0
}