package aoc22.day05

import aoc22.printIt
import java.io.File
import java.util.TreeMap

fun main() {

    fun parseInput(file: String): List<String> {
        return File(file).readLines()
    }

    fun part1(): String {
        val lines = parseInput("input/day05.txt")
        val (stacks, commands) = parseStacksAndCommands(lines)

        commands.forEach {
            val (crateFrom, crateTo, crateCount) = parseCommand(it)

            val temp = ArrayList<Char>()
            for (i in 0 until crateCount) {
                temp.add(stacks[crateFrom].removeFirst())
            }
            temp.forEach { el ->
                stacks[crateTo].addFirst(el)
            }
        }
        val result = StringBuilder()
        stacks.forEach { result.append(it.first()) }
        return result.toString().printIt()
    }

    fun part2(): String {
        val lines = parseInput("input/day05.txt")
        val (stacks, commands) = parseStacksAndCommands(lines)

        commands.forEach {
            val (crateFrom, crateTo, crateCount) = parseCommand(it)

            val temp = ArrayList<Char>()
            for (i in 0 until crateCount) {
                temp.add(stacks[crateFrom].removeFirst())
            }
            stacks[crateTo].addAll(0, temp)
        }
        val result = StringBuilder()
        stacks.forEach { result.append(it.first()) }
        return result.toString().printIt()
    }

    // test if implementation meets criteria from the description, like:
    check(part1() == "TPGVQPFDH")
    check(part2() == "DMRDFRHHH")
}

private fun parseStacksAndCommands(input: List<String>): Pair<ArrayList<ArrayDeque<Char>>, List<String>> {
    val stacks = ArrayList<ArrayDeque<Char>>()

    val indexOfInputSplit = input.indexOf("")
    var stacksInput = input.subList(0, indexOfInputSplit)
    val commands = input.subList(indexOfInputSplit + 1, input.size)

    val stacksCount = getStacksCount(stacksInput)
    stacksInput = stacksInput.subList(0, stacksInput.size - 1)
    initStacks(stacksCount, stacks)

    populateStacks(stacksCount, input, indexOfInputSplit, stacksInput, stacks)
    return Pair(stacks, commands)
}


private fun initStacks(stacksCount: Int, stacks: ArrayList<ArrayDeque<Char>>) {
    for (i in 0 until stacksCount) {
        stacks.add(ArrayDeque())
    }
}

private fun getStacksCount(stacksInput: List<String>) =
    stacksInput.last().split(" ").last { it.isNotBlank() }.toInt()

private fun populateStacks(
    stacksCount: Int,
    input: List<String>,
    indexOfInputSplit: Int,
    stacksInput: List<String>,
    stacks: ArrayList<ArrayDeque<Char>>
) {
    val indexMap = TreeMap<Int, Int>()
    for (i in 1..stacksCount) {
        indexMap[i] = input[indexOfInputSplit - 1].indexOf(i.toString())
    }

    for (i in stacksInput.size - 1 downTo 0) {
        var lineStack = stacksInput[i]
        while (lineStack.length <= indexMap.get(stacksCount)!!) {
            lineStack += " "
        }

        for ((k, j) in (1..stacksCount).withIndex()) {
            val element = lineStack[indexMap[j]!!]
            if (element != ' ') {
                stacks[k].addFirst(element)
            }
        }

    }
}

private fun parseCommand(it: String): Triple<Int, Int, Int> {
    val crateFrom = (it.substringAfter("from ").substringBefore(" to")).toInt() - 1
    val crateTo = (it.substringAfter("to ")).toInt() - 1
    val crateCount = it.substringAfter("move ").substringBefore(" from").toInt()
    return Triple(crateFrom, crateTo, crateCount)
}