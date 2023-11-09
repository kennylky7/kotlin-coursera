package games.gameOfFifteen

import board.*
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)


class GameOfFifteen(private val initializer: GameOfFifteenInitializer): Game {
    private val board: GameBoard<Int?> = createGameBoard<Int?>(4)

    override fun initialize() {
        initializer.initialPermutation.let {
            var listCount = 0
            for (row in 1 .. board.width )  {
                for (col in 1 .. board.width) {
                    val cell = Cell(row, col)
                    if (row == 4 && col == 4)
                        board[cell] = null
                    else {
                        board[cell] = it[listCount]
                        listCount++
                    }

                    if (listCount == it.size)
                        return
                }
            }
        }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        var expectedValue = 1
        for (row in 1 .. board.width )  {
            for (col in 1 .. board.width) {
                val currentCell = Cell(row, col)
                val currentValue = board[currentCell]
                if (row == board.width && col == board.width ) {
                    if( currentValue != null)  return false
                }
                else  {
                    if (currentValue != expectedValue)
                        return false

                    expectedValue++
                }
            }
        }

        return true
    }

    override fun processMove(direction: Direction) {

        with(board as SquareBoard) {
            val emptyCell = board.filter { it == null }.first()
            emptyCell.getNeighbour(direction.reversed())?.let {
                board[emptyCell] = board[it]
                board[it] = null
            }
        }

    }

    override fun get(i: Int, j: Int): Int? = board[Cell(i,j)]


}