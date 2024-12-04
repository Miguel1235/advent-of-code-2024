fun part1(input: List<List<Char>>): Int {
    val allWords = mutableListOf<String>()
    for (r in input.indices) {
        for (c in input[r].indices) {
            allWords.addAll(
                listOf(
                    "${input.getOrNull(r)?.getOrNull(c) ?: "."}" + "${
                        input.getOrNull(r)?.getOrNull(c + 1) ?: "."
                    }" + "${input.getOrNull(r)?.getOrNull(c + 2) ?: "."}" + "${
                        input.getOrNull(r)?.getOrNull(c + 3) ?: "."
                    }", "${input.getOrNull(r)?.getOrNull(c) ?: "."}" + "${
                        input.getOrNull(r)?.getOrNull(c - 1) ?: "."
                    }" + "${input.getOrNull(r)?.getOrNull(c - 2) ?: "."}" + "${
                        input.getOrNull(r)?.getOrNull(c - 3) ?: "."
                    }", "${input.getOrNull(r)?.getOrNull(c) ?: "."}" + "${
                        input.getOrNull(r - 1)?.getOrNull(c) ?: "."
                    }" + "${input.getOrNull(r - 2)?.getOrNull(c) ?: "."}" + "${
                        input.getOrNull(r - 3)?.getOrNull(c) ?: "."
                    }", "${input.getOrNull(r)?.getOrNull(c) ?: "."}" + "${
                        input.getOrNull(r + 1)?.getOrNull(c) ?: "."
                    }" + "${input.getOrNull(r + 2)?.getOrNull(c) ?: "."}" + "${
                        input.getOrNull(r + 3)?.getOrNull(c) ?: "."
                    }", "${input.getOrNull(r)?.getOrNull(c) ?: "."}" + "${
                        input.getOrNull(r - 1)?.getOrNull(c + 1) ?: "."
                    }" + "${input.getOrNull(r - 2)?.getOrNull(c + 2) ?: "."}" + "${
                        input.getOrNull(r - 3)?.getOrNull(c + 3) ?: "."
                    }", "${input.getOrNull(r)?.getOrNull(c) ?: "."}" + "${
                        input.getOrNull(r + 1)?.getOrNull(c - 1) ?: "."
                    }" + "${input.getOrNull(r + 2)?.getOrNull(c - 2) ?: "."}" + "${
                        input.getOrNull(r + 3)?.getOrNull(c - 3) ?: "."
                    }", "${input.getOrNull(r)?.getOrNull(c) ?: "."}" + "${
                        input.getOrNull(r + 1)?.getOrNull(c + 1) ?: "."
                    }" + "${input.getOrNull(r + 2)?.getOrNull(c + 2) ?: "."}" + "${
                        input.getOrNull(r + 3)?.getOrNull(c + 3) ?: "."
                    }", "${input.getOrNull(r)?.getOrNull(c) ?: "."}" + "${
                        input.getOrNull(r - 1)?.getOrNull(c - 1) ?: "."
                    }" + "${input.getOrNull(r - 2)?.getOrNull(c - 2) ?: "."}" + "${
                        input.getOrNull(r - 3)?.getOrNull(c - 3) ?: "."
                    }"
                )
            )
        }
    }
    return allWords.count { it == "XMAS" }
}

fun part2(input: List<List<Char>>): Int {
    var total = 0
    for (r in input.indices) {
        for (c in input[r].indices) {
            val x = "${input.getOrNull(r - 1)?.getOrNull(c - 1) ?: "."}" + "${
                input.getOrNull(r)?.getOrNull(c) ?: "."
            }" + "${input.getOrNull(r + 1)?.getOrNull(c + 1) ?: "."}"

            val y = "${input.getOrNull(r + 1)?.getOrNull(c - 1) ?: "."}" + "${
                input.getOrNull(r)?.getOrNull(c) ?: "."
            }" + "${input.getOrNull(r - 1)?.getOrNull(c + 1) ?: "."}"

            if ((x == "SAM" || x == "MAS") && (y == "SAM" || y == "MAS")) {
                total++
            }
        }
    }
    return total
}

fun main() {
    val testInput = readInput("Day04_test").map { it.toList() }
    check(part1(testInput) == 18)

    part2(testInput)

    val input = readInput("Day04").map { it.toList() }
    check(part1(input) == 2583)

    check(part2(input) == 1978)
}