import kotlin.math.abs

private val obtainValues = { regex: Regex, input: String -> regex.find(input)?.groupValues!!.takeLast(2).map { it.toDouble() } }

private val calculateCost = { deltaXA: Double, deltaYA: Double, deltaXB: Double, deltaYB: Double, x: Double, y: Double ->
    // crammer rule
    val determinant = deltaXA * deltaYB - deltaXB * deltaYA
    val a = abs((x * deltaYB - y * deltaXB) / determinant)
    val b = abs((x * deltaYA - y * deltaXA) / determinant)
    if (a.rem(1.0) == 0.0 && b.rem(1.0) == 0.0) (3u * a.toULong() + b.toULong()) else 0u
}

private val part = { input: List<String>, part1: Boolean ->
    val regexXY = Regex("""X\+(\d+), Y\+(\d+)""")
    val prizeRegex = Regex("""X=(\d+), Y=(\d+)""")
    val prizeOffset = 10000000000000
    input.chunked(4).sumOf { (i1, i2, i3, _) ->
        val (xa, ya) = obtainValues(regexXY, i1)
        val (xb, yb) = obtainValues(regexXY, i2)
        val (xp, yp) = obtainValues(prizeRegex, i3)
        calculateCost(xa, ya, xb, yb, if (part1) xp else xp + prizeOffset, if (part1) yp else yp + prizeOffset)
    }
}

fun main() {
    val testInput = readInput("Day13_test")
    check(part(testInput, true) == 480.toULong())
    check(part(testInput, false) == 875318608908.toULong())

    val input = readInput("Day13")
    check(part(input, true) == 29598.toULong())
    check(part(input, false) == 93217456941970.toULong())
}
 