fun part1(memory: String): Long {
    val memoryRegex = Regex("""mul\(\d+,\d+\)""")
    val instructions = memoryRegex.findAll(memory)

    val numbersRegex = Regex("""\d+""")
    val numbers = instructions.map { instruction ->
        val matches = numbersRegex.findAll(instruction.value)
        matches.map { it.value.toLong() }.toList()
    }.toList()

    return numbers.fold(0L) { acc, (f, s) -> acc + (f * s) }
}

fun part2(memory: String): Long {
    val memoryRegex = Regex("""mul\(\d+,\d+\)|don't\(\)|do\(\)""")
    val instructions = memoryRegex.findAll(memory)
    var isMulEnabled = true
    val numbersRegex = Regex("""\d+""")

    val numbers = mutableListOf<List<Long>>()
    instructions.forEach { instruction ->
        val r = instruction.value

        when {
            r.contains("don't()") -> isMulEnabled = false
            r.contains("do()") -> isMulEnabled = true
            r.contains("mul") && isMulEnabled -> {
                val matches = numbersRegex.findAll(r)
                numbers.add(matches.map { it.value.toLong() }.toList())
            }
        }
    }

    return numbers.fold(0L) { acc, (f, s) -> acc + (f * s) }
}

fun main() {
    val testInput = readOneLine("Day03_test")
    check(part1(testInput) == 161L)
    check(part2(testInput) == 48L)

    val input = readOneLine("Day03")
    check(part1(input) == 189600467L)
    check(part2(input) == 107069718L)
}