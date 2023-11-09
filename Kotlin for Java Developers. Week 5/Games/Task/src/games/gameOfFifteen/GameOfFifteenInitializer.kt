package games.gameOfFifteen

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val list = (1.. 15).shuffled().toMutableList()
        if (!isEven(list)) makeEven(list)
        list

    }

    private fun makeEven(list: MutableList<Int>) {
        var isSwapped = false
        for (current in 1 until list.size) {
            val prev = current - 1
            val currentValue = list[current]

            if (list[prev] > currentValue) {
                list[prev + 1] = list[prev]
                isSwapped = true
            }
            list[prev + 1] = currentValue
            if (isSwapped)
                break
        }
    }
}

