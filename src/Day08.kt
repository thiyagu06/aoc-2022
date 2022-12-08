import aoc22.printIt
import java.io.File

fun main() {

    fun parseInput(): List<List<Int>> {
        return File("input.txt/day07.txt").readLines().map { row -> row.map { it.digitToInt() } }
    }

    fun star1(input: List<List<Int>>): Int {
        return input.mapIndexed { rowNum, row ->
            List(row.size) { isVisible(rowNum, it, input) }.count { it }
        }.sumOf { it }.printIt()

    }

    fun star2(input: List<List<Int>>): Int {
        return input.mapIndexed { rowNum, row ->
            row.mapIndexed { colNum, tree ->
                val currentColumn = input.map { row -> row[colNum] }
                val leftScore = input[rowNum].subList(0, colNum).reversed().findScore(tree)
                val rightScore = input[rowNum].subList(colNum + 1, input[rowNum].size).findScore(tree)
                val topScore = currentColumn.subList(0, rowNum).reversed().findScore(tree)
                val bottomScore = currentColumn.subList(rowNum + 1, currentColumn.size).findScore(tree)
                leftScore * rightScore * topScore * bottomScore
            }.maxOf { it }
        }.maxOf { it }.printIt()
    }

    val grid = parseInput()

    // test if implementation meets criteria from the description, like:
    check(star1(grid) == 1854)
    check(star2(grid) == 527340)
}


private fun List<Int>.findScore(element: Int): Int {
    var count = 0
    forEach { otherTree ->
        if (otherTree < element) {
            count += 1
        } else {
            return count + 1
        }
    }
    return count
}

fun isVisible(row: Int, col: Int, grid: List<List<Int>>): Boolean {

    if (row == 0 || row == grid.size - 1)
        return true
    if (col == 0 || col == grid.first().size - 1)
        return true

    val element = grid[row][col]
    val currentColumn = grid.map { r -> r[col] }
    when {
        (grid[row].subList(0, col).maxOf { it } < element) -> return true
        (grid[row].subList(col + 1, grid[row].size).maxOf { it } < element) -> return true
        (currentColumn.subList(0, row).maxOf { it } < element) -> return true
        (currentColumn.subList(row + 1, currentColumn.size).maxOf { it } < element) -> return true
    }
    return false
}