package aoc22.day05

import aoc22.printIt
import java.io.File
import java.util.TreeMap

fun main() {

    fun parseInput(file: String):  String {
        return File(file).readText()
    }

    fun star1(): Long {
        val input = parseInput("input/day06.txt")
        return findStartOfMarker(input, 4)
    }

      fun star1(): Long {
        val input = parseInput("input/day06.txt")
        return findStartOfMarker(input, 14)
    }


    // test if implementation meets criteria from the description, like:
    check(part1() == 1625.toLong())
    check(part2() == 2250.toLong())
}

private fun findStartOfMarker(input: String, windowSize: Int): Long {
    input.windowed(windowSize).forEachIndexed { index, s ->
        if (s.toSet().size == windowSize)
            return (windowSize + index).toLong().printIt()
    }
    throw Error("could not found marker")
}