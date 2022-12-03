import java.io.File

fun main() {

    fun parseInput(file: String): List<String> {
        return File(file).readLines()
    }

    fun Char.priority(): Int {
        return when (this) {
            in 'a'..'z' -> this - 'a' + 1
            in 'A'..'Z' -> this - 'A' + 27
            else -> 0
        }
    }

    fun part1(): Int {
        val lines = parseInput("input/day03a.txt")
        return lines.sumOf { it ->
            val a = it.substring(0, it.length / 2)
            val b = it.substring(it.length / 2)
            a.first { it in b }.priority()
        }.printIt()
    }

    fun part2(): Int {
        val lines = parseInput("input/day03b.txt")
        return lines.chunked(3).sumOf { values ->
            val (a, b, c) = values
            a.first { it in b && it in c }.priority()
        }

    }

    // test if implementation meets criteria from the description, like:
    check(part1() == 7875)
    check(part2() == 2479)
}
