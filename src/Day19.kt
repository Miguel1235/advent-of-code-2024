private fun part1(input: List<String>): Int {
    val towels = input.first().split(",").map { it.trim() }
    val words = input.takeLast(input.size-2)


    return words.sumOf { canFormWord(towels, it) }

}

fun canFormWord(towels: List<String>, word: String): Int {
    val wordLength = word.length
    val dp = BooleanArray(wordLength + 1) { false }
    dp[0] = true // an empty string can always be formed

    for (i in 1..wordLength) {
        for (towel in towels) {
            val startIndex = i - towel.length
            if (i >= towel.length && word.substring(startIndex, i) == towel && dp[startIndex]) {
                dp[i] = true
                break
            }
        }
    }
    return if(dp[wordLength]) 1 else 0
}


private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
//    val testInput = readInput("Day19_test")
//    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)

    val input = readInput("Day19")
    check(part1(input) == 353)
//    check(part2(input) == 0)
}
 