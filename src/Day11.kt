import aoc22.printIt
import java.io.File

fun main() {

     fun parseInput() = File("input/day11.txt").readLines()

    val input = parseInput().chunkedBy { it.isBlank() }.map { from(it) }
    fun star1(): Long {
        val monkeys = input.map { it.copy() }
        return calculateMonkeyBusiness(monkeys, rounds = 20) { it / 3 }
    }

    fun star2(): Long {
        val monkeys = input.map { it.copy() }
        val mod = monkeys.map { it.divisibleBy }.reduce { a, b -> a * b }
        return calculateMonkeyBusiness(monkeys, rounds = 10000) { it % mod }
    }

    // test if implementation meets criteria from the description, like:
    check(star1().printIt() == 120756L)
    check(star2().printIt() == 39109444654L)

}

data class Monkey(
    private val startingItems: List<Long>,
    private val operation: (old: Long) -> Long,
    val divisibleBy: Long,
    private val ifTrueMonkey: Int,
    private val ifFalseMonkey: Int
) {
    var itemInspectionCount = 0L
    private val items = ArrayDeque(startingItems)

    private fun receiveItem(value: Long) {
        items.add(value)
    }

    fun inspectItems(monkeys: List<Monkey>, calmDown: (Long) -> Long) {
        while (items.isNotEmpty()) {
            val inspect = items.removeFirst()
            itemInspectionCount++
            val afterInspection = operation(inspect)
            val afterCalmDown = calmDown(afterInspection)
            val giveToMonkey =
                if (afterCalmDown % divisibleBy == 0L) ifTrueMonkey
                else ifFalseMonkey
            monkeys[giveToMonkey].receiveItem(afterCalmDown)
        }
    }
}

private fun calculateMonkeyBusiness(monkeys: List<Monkey>, rounds: Int, calmDown: (Long) -> Long): Long {
    repeat(rounds) {
        monkeys.forEach { it.inspectItems(monkeys, calmDown) }
    }
    return monkeys
        .map { it.itemInspectionCount }
        .sortedDescending()
        .take(2)
        .fold(1) { acc, i -> acc * i }
}

fun from(lines: List<String>): Monkey {
    val startingItems = lines[1]
        .removePrefix("  Starting items: ")
        .split(", ")
        .map { it.toLong() }

    val operation = lines[2]
        .removePrefix("  Operation: new = old ")
        .split(' ')
        .let { (operator, n) ->
            when (operator) {
                "+" -> { old: Long -> old + n.toLong() }
                "*" -> {
                    if (n == "old") { old: Long -> old * old }
                    else { old: Long -> old * n.toLong() }
                }

                else -> error("Unknown operator $operator")
            }
        }

    val divisibleBy = lines[3].substringAfterLast(' ').toLong()
    val ifTrue = lines[4].substringAfterLast(' ').toInt()
    val ifFalse = lines[5].substringAfterLast(' ').toInt()

    return Monkey(startingItems, operation, divisibleBy, ifTrue, ifFalse)
}

fun <T> List<T>.chunkedBy(selector: (T) -> Boolean): List<List<T>> =
    fold(mutableListOf(mutableListOf<T>())) { acc, item ->
        if (selector(item)) acc.add(mutableListOf())
        else acc.last().add(item)
        acc
    }