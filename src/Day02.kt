import java.io.File

fun main() {

    fun parseInput(file: String): List<String> {
        return File(file).readLines()
    }

    fun part1(): Int {
        val input = parseInput("input/day02a.txt")
        return input.asSequence()
            .map { arrayOf(it.first(), it.last()) }
            .sumOf {
                val move = it[1] - 'X' + 1

                val round = when ((it[1] - 'X') - (it[0] - 'A')) {
                    0 -> 3 
                    1, -2 -> 6
                    else -> 0
                }

                move + round
            }
    }

    fun part2(): Int {
        val input = parseInput("input/day02b.txt")
        return input.asSequence()
            .map { arrayOf(it.first(), it.last()) }
            .sumOf {
                val round = (it[1] - 'X') * 3

                val other = it[0] - 'A'
                val move = when (round) {
                    0 -> other - 1 
                    6 -> other + 1 
                    else -> other
                }.mod(3)

                move + round
            }
    }

    // test if implementation meets criteria from the description, like:
    check(part1() == 12772)
    check(part2() == 11618)
}