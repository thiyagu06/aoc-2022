fun main() {
    fun calculateCalories(input: List<String>) : List<Int> {
        val calories = mutableListOf<Int>()
        var accumulator = 0
        input.forEach {
            if (it.isBlank()) {
                calories.add(accumulator)
                accumulator = 0
            }else {
                accumulator += it.toInt()
            }
        }
        return calories
    }

    fun part1(input: List<String>): Int {
        return calculateCalories(input).maxOrNull()!!
    }

    fun part2(input: List<String>): Int {
        return calculateCalories(input).reversed().take(3).sum()
    }

    println(part1(readInput("input/day01a")))
    println(part2(readInput("input/day01b")))
}
