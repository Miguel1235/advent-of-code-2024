fun part(memory: String, withDos: Boolean = false): Long {
    val memoryRegex = Regex("""mul\((\d+),(\d+)\)|don't\(\)|do\(\)""")
    var isMulEnabled = true

    return memoryRegex.findAll(memory).sumOf { instruction ->
        when (val r = instruction.value) {
            "don't()" -> {
                if (withDos) isMulEnabled = false
                0L
            }
            "do()" -> {
                if (withDos) isMulEnabled = true
                0L
            }

            else -> {
                if (isMulEnabled && r.startsWith("mul")) {
                    val (f, s) = Regex("""\d+""").findAll(r).map { it.value.toLong() }.toList()
                    f * s
                } else 0L
            }
        }
    }
}

fun main() {
    val testInput = readOneLine("Day03_test")
    check(part(testInput) == 161L)
    check(part(testInput, true) == 48L)

    val input = readOneLine("Day03")
    check(part(input) == 189600467L)
    check(part(input, true) == 107069718L)
}