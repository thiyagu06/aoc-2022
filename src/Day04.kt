import java.io.File

fun main() {

    private val input = return File("input/day04.txt").readLines()

     private val pattern = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()

    private val pairs = input.map { pattern.matchEntire(it)!!.destructured }
        .map { (x1, x2, y1, y2) -> (x1.toInt()..x2.toInt()).toSet() to (y1.toInt()..y2.toInt()).toSet() }

    fun star1() = pairs.count { it.first.containsAll(it.second) || it.second.containsAll(it.first) }

    fun star2() = pairs.count { (it.first intersect it.second).isNotEmpty() }

    // test if implementation meets criteria from the description, like:
    check(star1() == 441)
    check(star2() == 4)
}
