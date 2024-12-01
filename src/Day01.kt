import kotlin.math.abs

fun main() {
    val part1 = { input: List<Pair<Int, Int>> ->  input.sumOf {(lv, rv) -> abs(lv-rv)} }
    val part2 = { input: List<Pair<Int, Int>> -> input.sumOf { (lv, _) -> lv * input.count { it.second == lv } }}

    fun parseInput(input: List<String>): List<Pair<Int, Int>> {
        val (l,r) = input
            .map { line -> line.split(" ").filter { it.isNotEmpty() } }.map { Pair(it[0].toInt(), it[1].toInt()) }
            .unzip()
        return l.sorted().zip(r.sorted())
    }

    val testInput = parseInput(readInput("Day01_test"))
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = parseInput(readInput("Day01"))
    check(part1(input) == 2066446)
    check(part2(input) == 24931009)
}
