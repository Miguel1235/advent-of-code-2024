import kotlin.math.abs

private fun part1(input: List<String>, part1: Boolean = true): ULong {
    val regexXY = Regex("""X\+(\d+), Y\+(\d+)""")
    val prizeRegex = Regex("""X=(\d+), Y=(\d+)""")
    val prizeOffset = 10000000000000

    return input.chunked(4).sumOf { (i1, i2, i3, _) ->
        val (xa, ya) = obtainValues(regexXY, i1)
        val (xb, yb) = obtainValues(regexXY, i2)
        val (xp, yp) = obtainValues(prizeRegex, i3)
        calculateCost(xa, ya, xb, yb, if (part1) xp else xp + prizeOffset, if (part1) yp else yp + prizeOffset)
    }
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

fun main() {
    val testInput = readInput("Day13_test")
    part1(testInput, true)
    check(part1(testInput) == 480.toULong())
    check(part1(testInput, false) == 875318608908.toULong())

    val input = readInput("Day13")
    check(part1(input) == 29598.toULong())
    check(part1(input, false) == 93217456941970.toULong())
}
 