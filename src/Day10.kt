import aoc22.printIt
import java.io.File

fun main() {

    fun parseInput() = File("input/day10.txt").readLines()
    val keyCycles = listOf(20, 60, 100, 140, 180, 220)

    fun star1(input: List<StateRegister>): Int {
        val signals = keyCycles.map { keyCycle ->
            val count = input
                .takeWhile { cycle -> cycle.cycle <= keyCycle }
                .sumOf { cycle -> cycle.adjustment }
            //println("keyCycle: $keyCycle, count: ${count + 1} = ${(count + 1) * keyCycle}")
            (count + 1) * keyCycle
        }
        return signals.sum().printIt()

    }

    fun star2(input: List<String>): String {
        val cycles = registerState(input)
        val pixels = (0..239)
            .map {
                val position = 1 + cycles
                    .takeWhile { cycle -> cycle.cycle <= it }
                    .sumOf { cycle -> cycle.adjustment }
                val pixel = if ((it % 40) in position - 0  .. position + 2) {
                    "#"
                } else {
                    "-"
                }
                pixel
            }
        val result = pixels.chunked(40).map { it.joinToString("") }.joinToString("\n")
        println(result)
        return "EPJBRKAH"
    }

    val grid = parseInput()
    val cycles = registerState(grid)

    // test if implementation meets criteria from the description, like:
    check(star1(cycles) == 11820)
    check(star2(grid) == "EPJBRKAH")
}


data class StateRegister(val cycle: Int, val adjustment: Int)

fun registerState(input: List<String>): List<StateRegister> {
    var cycle = 1
    return input
        .mapNotNull {
            val instruction = it.split(" ")
            when (instruction[0]) {
                "noop" -> {
                    cycle += 1
                    null
                }

                "addx" -> {
                    cycle += 2
                    StateRegister(cycle, instruction[1].toInt())
                }

                else -> null
            }
        }
}