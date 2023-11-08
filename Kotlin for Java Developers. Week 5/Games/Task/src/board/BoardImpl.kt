package board

import board.Direction.*
import board.SquareBoard.SquareBoard.getCellKey

class SquareBoardImpl(inWidth: Int) : SquareBoard {

    private val cellMap = hashMapOf<String, Cell>()
    override val width: Int = inWidth

    init {
        for (row in 1..width) {
            for (col in 1..width) {
                val keyStr = getCellKey(row, col)
                cellMap[keyStr] = Cell(row, col)
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return cellMap[getCellKey(i, j)]
    }

    override fun getCell(i: Int, j: Int): Cell {
        return getCellOrNull(i, j) ?: throw IllegalArgumentException("Cell[$i, $j] not exist")
    }

    override fun getAllCells(): Collection<Cell> {
        val resultList = mutableListOf<Cell>()
        for (row in 1..width) {
            for (col in 1..width) {
                val keyStr = getCellKey(row, col)
                cellMap[keyStr]?.let { resultList.add(it) }
            }
        }
        return resultList.toList()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val resultList = mutableListOf<Cell>()
        for (col in jRange) {
            val keyStr = getCellKey(i, col)
            cellMap[keyStr].let {
                if (it != null) resultList += it
            }
        }
        return resultList
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val resultList = mutableListOf<Cell>()
        for (row in iRange) {
            val keyStr = getCellKey(row, j)
            cellMap[keyStr].let {
                if (it != null) resultList += it
            }
        }
        return resultList
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        val (row, col) = when (direction) {
            UP -> this.i - 1 to this.j
            DOWN -> this.i + 1 to this.j
            LEFT -> this.i to this.j - 1
            RIGHT -> this.i to this.j + 1
        }

        return if (row < 1 || row > width) null
        else if (col < 1 || col > width) null
        else cellMap[getCellKey(row, col)]
    }

}

class GameBoardImpl<T>(inSqBoard : SquareBoard) :GameBoard<T>, SquareBoard by inSqBoard{

    val valueMap = hashMapOf<Cell, T?>()
    val sqBoard = inSqBoard

    init {
        for (row in 1..width) {
            for (col in 1..width) {
                valueMap[Cell(row, col)] = null
            }
        }
    }
    override fun get(cell: Cell): T? {
        return valueMap[cell]
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return valueMap.filterValues(predicate).size == sqBoard.width * sqBoard.width
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return valueMap.filterValues(predicate).keys.isNotEmpty()
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return valueMap.filterValues(predicate).keys.firstOrNull()
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return valueMap.filterValues(predicate).keys
    }

    override fun set(cell: Cell, value: T?) {
        valueMap.remove(cell)
        valueMap[cell] = value
    }


}



fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> {
    val sqBoard = createSquareBoard(width)
    return GameBoardImpl<T>(sqBoard)
}

