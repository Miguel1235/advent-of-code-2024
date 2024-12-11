private fun part1(input: List<String>, loops: Int = 25): Long {
    val stones = input.first().split(" ").map { it.toLong() }
    var stoneMap = stones.toSet().associateWith { stone -> stones.count { it == stone }.toLong() }.toMutableMap()

    repeat(loops) {
        val newStones = mutableMapOf<Long, Long>()
        for((stone, amount) in stoneMap) {
            when {
                stone == 0L -> newStones[1] = (newStones[1] ?: 0) + amount
                stone.toString().length % 2 == 0 -> {
                    val strRep = stone.toString()
                    val mid = strRep.length / 2
                    val stone1 = strRep.substring(0, mid).toLong()
                    val stone2 = strRep.substring(mid).toLong()
                    newStones[stone1] = (newStones[stone1] ?: 0) + amount
                    newStones[stone2] = (newStones[stone2] ?: 0) + amount
                }
                else -> newStones[2024 * stone] = (newStones[2024 * stone] ?: 0) + amount
            }
        }
        stoneMap = newStones
    }
    return stoneMap.values.sum()
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 55312L)

    val input = readInput("Day11")
    check(part1(input) == 186175L)
    part1(input, 75).println()
}
 