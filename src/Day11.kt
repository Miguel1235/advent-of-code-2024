private fun part(input: List<String>, loops: Int = 25): Long {
    val stones = input.first().split(" ").map { it.toLong() }
    var stoneMap = stones.associateWith { stone -> stones.count { it == stone }.toLong() }.toMutableMap()

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

fun main() {
    val testInput = readInput("Day11_test")
    check(part(testInput) == 55312L)
    check(part(testInput, 75) == 65601038650482)

    val input = readInput("Day11")
    check(part(input) == 186175L)
    check(part(input, 75) ==220566831337810)
}
 