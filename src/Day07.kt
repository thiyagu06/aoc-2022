import aoc22.printIt
import java.io.File

fun main() {

    fun parseInput(file: String): List<String> {
        return File(file).readLines()
    }

    fun part1(): Long {
        val input = parseInput("input.txt/day07.txt")
        val dirs = mutableListOf(Dir(null))
        var current: Dir = dirs.first()

        input.drop(1).forEach { line ->
            when {
                line == "$ cd .." -> current = current.parent!!
                line.startsWith("$ cd") -> {
                    Dir(current).also { current.children += it; dirs += it; current = it }
                }
                line[0].isDigit() -> current.filesSize += line.substringBefore(" ").toLong()
            }
        }
        return dirs.filter { it.totalSize <= 100_000 }.sumOf { it.totalSize }.printIt()
    }

    fun part2(): Long {
        val input = parseInput("input.txt/day07.txt")
        val dirs = mutableListOf(Dir(null))
        var current: Dir = dirs.first()

        input.drop(1).forEach { line ->
            when {
                line == "$ cd .." -> current = current.parent!!
                line.startsWith("$ cd") -> {
                    Dir(current).also { current.children += it; dirs += it; current = it }
                }
                line[0].isDigit() -> current.filesSize += line.substringBefore(" ").toLong()
            }
        }

        val missing = 30_000_000 - (70_000_000 - dirs.first().totalSize)

        return dirs.filter { it.totalSize >= missing }.minOf { it.totalSize }.printIt()
    }

    // test if implementation meets criteria from the description, like:
    check(part1() == 1086293.toLong())
    check(part2() == 366028.toLong())
}


class Dir(val parent: Dir?, var children: List<Dir> = emptyList()) {
    var filesSize = 0L
    val totalSize: Long get() = filesSize + children.sumOf { it.totalSize }
}