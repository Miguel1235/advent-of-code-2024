private fun part1(input: List<String>): Long {
    val trueValues = mutableListOf<Long>()

    for (line in input) {
        val (targetS, numbersS) = line.split(":")
        val target = targetS.toLong()
        val numbers = numbersS.trim().split(" ").map { it.toLong() }
        val combinations = generateCombinations(listOf("*", "+"), numbers.size-1)


        for(combination in combinations) {
            var total = numbers[0]
            for(i in 1 until numbers.size) {
                val operator = combination[i-1]

                when(operator) {
                    "*" -> total *= numbers[i]
                    "+" -> total += numbers[i]
                }
            }
            if(total == target) {
//                println("The target is $target and the numbers are $numbers, with ops: $combinations")
                trueValues.add(target)
                break

            }
        }
    }
    return trueValues.sum()
}

fun generateCombinations(operations: List<String>, size: Int): List<List<String>> {
    if (size <= 0) return emptyList()
    if (size == 1) return operations.map { listOf(it) }

    val combinations = mutableListOf<List<String>>()

    for (op in operations) {
        val subCombinations = generateCombinations(operations, size - 1)
        for (sub in subCombinations) {
            combinations.add(listOf(op) + sub)
        }
    }

    return combinations
}

fun main() {
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)

    val input = readInput("Day07")
    check(part1(input) == 5837374519342L)
}