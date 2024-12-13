import kotlin.math.abs

private fun part1(input: List<String>, part1: Boolean = true): ULong {
    val regexXY = Regex("""X\+(\d+), Y\+(\d+)""")
    val prizeRegex = Regex("""X=(\d+), Y=(\d+)""")

    var total: ULong = 0u
    val prizeOffset = 10000000000000
    for (i in 0..input.lastIndex step 4) {
        val (xa, ya) = obtainValues(regexXY, input[i])
        val (xb, yb) = obtainValues(regexXY, input[i + 1])
        val (xp, yp) = obtainValues(prizeRegex, input[i + 2])

        total += calculateCost(xa, ya, xb, yb, if (part1) xp else xp + prizeOffset, if (part1) yp else yp + prizeOffset)
    }
    return total
}

val obtainValues = { regex: Regex, input: String -> regex.find(input)?.groupValues!!.takeLast(2).map { it.toDouble() } }

private fun calculateCost(
    deltaXA: Double,
    deltaYA: Double,
    deltaXB: Double,
    deltaYB: Double,
    x: Double,
    y: Double
): ULong {
    // crammer
    val determinant = deltaXA * deltaYB - deltaXB * deltaYA
    val a = abs((x * deltaYB - y * deltaXB) / determinant)
    val b = abs((x * deltaYA - y * deltaXA) / determinant)

    return if (a.rem(1.0) == 0.0 && b.rem(1.0) == 0.0) {
        (3u * a.toULong() + b.toULong())
    } else 0u
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 480.toULong())
    check(part1(testInput, false) == 875318608908.toULong())

    val input = readInput("Day13")
    check(part1(input) == 29598.toULong())
    check(part1(input, false) == 93217456941970.toULong())
}
 