fun parseInput(input: List<String>): Pair<List<List<Int>>, List<String>> {
    val updates = mutableListOf<List<Int>>()
    val rules = mutableListOf<String>()
    for(line in input) {
        if(line.contains(",")) updates.add(line.split(",").map { it.toInt()} )
        else if(line.contains("|")) rules.add(line)
    }
    return Pair(updates, rules)
}

fun isUpdateValid(update: List<Int>, rules: List<String>): Boolean {
    for(i in update.indices) {
        for(j in i+1 until update.size) {
            val rule = "${update[i]}|${update[j]}"

            if(rule !in rules) return false
        }
        for(k in i-1 downTo 0) {
            val rule = "${update[k]}|${update[i]}"
            if(rule !in rules) return false
        }
    }
    return true
}

fun obtainUpdates(updates: List<List<Int>>, rules: List<String>, validOnes: Boolean = true): List<List<Int>> {
    val valid = mutableListOf<List<Int>>()
    val invalid = mutableListOf<List<Int>>()

    for(update in updates) {
        if(isUpdateValid(update, rules)) {
            valid.add(update)
        } else {
            invalid.add(update)
        }
    }
    return if(validOnes) valid else invalid
}


fun part1(updates: List<List<Int>>, rules: List<String>): Int {
    return obtainUpdates(updates, rules).sumOf { it[it.size/2] }
}

private fun missing(left: Int, right: Int, rules: List<String>): Boolean {
    return "$left|$right" !in rules
}

fun part2(updates: List<List<Int>>, rules: List<String>): Int {
    val invalidOnes = obtainUpdates(updates, rules, false).map { it.toMutableList() }
    val validOnes = mutableListOf<List<Int>>()

    for(update in invalidOnes) {
        check@ while (true) {
            for (li in 0..<update.lastIndex) {
                val left = update[li]
                val ri = li + 1
                val right = update[ri]

                if (missing(left, right, rules)) {
                    update[li] = right
                    update[ri] = left
                    continue@check
                }
            }
            break
        }
        validOnes.add(update)
    }
    return validOnes.sumOf { it[it.size/2] }
}

fun main() {
    val testInput = readInput("Day05_test")
    val (testUpdates, testRules) = parseInput(testInput)
    check(part1(testUpdates, testRules)==143)
    check(part2(testUpdates, testRules)==123)

    val input = readInput("Day05")
    val (updates, rules) = parseInput(input)
    check(part1(updates, rules)==5275)
    check(part2(updates, rules)==6191)
}
