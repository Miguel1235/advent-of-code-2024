import kotlin.math.abs

private fun part1(input: List<List<Char>>): Int {
    val antennas = findAntennas(input)
    val antinodes: MutableList<Pair<Int, Int>> = mutableListOf()
    for (antenna in antennas) {
        val combinations = obtainCombinations(antenna.value)

        for (combination in combinations) {
            val fv = combination.first
            val sv = combination.second

            val diff = obtainAbsolute(fv, sv)
            if (fv.second < sv.second) {
                antinodes.add(Pair(fv.first - diff.first, fv.second - diff.second))
                antinodes.add(Pair(sv.first + diff.first, sv.second + diff.second))
            } else {
                antinodes.add(Pair(fv.first - diff.first, fv.second + diff.second))
                antinodes.add(Pair(sv.first + diff.first, sv.second - diff.second))
            }
        }
    }

    return countTotal(input, antinodes)
}

private val countTotal = { input: List<List<Char>>, antinodes: List<Pair<Int,Int>> ->
    input.indices.sumOf { r ->
        input[0].indices.count { c ->
            Pair(r, c) in antinodes
        }
    }
}

private val obtainCombinations = { pairs:  List<Pair<Int, Int>> -> pairs.flatMapIndexed { i, firstPair ->
    pairs.drop(i + 1).map { secondPair -> firstPair to secondPair } } }


private fun part2(input: List<List<Char>>): Int {
    val antennas = findAntennas(input)
    val antinodes: MutableList<Pair<Int, Int>> = mutableListOf()
    for (antenna in antennas) {
        val combinations = obtainCombinations(antenna.value)

        for (combination in combinations) {
            val fv = combination.first
            val sv = combination.second

            val diff = obtainAbsolute(fv, sv)

            val maxR = input.size
            antinodes.add(fv)
            antinodes.add(sv)

            if (fv.second < sv.second) {
                var lastPairD = Pair(sv.first + diff.first, sv.second + diff.second)
                for (i in 0..maxR) {
                    antinodes.add(lastPairD)
                    lastPairD = Pair(lastPairD.first + diff.first, lastPairD.second + diff.second)
                }
                var lastPairU = Pair(sv.first - diff.first, sv.second - diff.second)
                for (i in 0..maxR) {
                    antinodes.add(lastPairU)
                    lastPairU = Pair(lastPairU.first - diff.first, lastPairU.second - diff.second)
                }
            } else {
                var lastPairD = Pair(sv.first + diff.first, sv.second - diff.second)
                antinodes.add(lastPairD)
                for (i in 0..maxR) {
                    antinodes.add(lastPairD)
                    lastPairD = Pair(lastPairD.first + diff.first, lastPairD.second - diff.second)
                }
                var lastPairU = Pair(sv.first - diff.first, sv.second + diff.second)
                for (i in 0..maxR) {
                    antinodes.add(lastPairU)
                    lastPairU = Pair(lastPairU.first - diff.first, lastPairU.second + diff.second)
                }
            }
        }
    }

    return countTotal(input, antinodes)
}

private fun obtainAbsolute(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(abs(p1.first - p2.first), abs(p1.second - p2.second))
}

private fun findAntennas(input: List<List<Char>>): Map<Char, List<Pair<Int, Int>>> {
    val result = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

    for (row in input.indices) {
        for (col in input[row].indices) {
            val char = input[row][col]
            if (char != '.') {
                result.computeIfAbsent(char) { mutableListOf() }.add(Pair(row, col))
            }
        }
    }
    return result
}

fun main() {
    val testInput = readInput("Day08_test").map { it.toList() }
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08").map { it.toList() }
    check(part1(input) == 244)
    check(part2(input) == 912)
}
 