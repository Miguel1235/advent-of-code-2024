import kotlin.math.abs

private val part1 = { reports: List<String> ->
    reports.count { report ->
        val level = report.split(" ").map(String::toInt)
        isTheLevelSafe(level)
    }
}

private val part2 = { reports: List<String> ->
    reports.fold(0) { acc, report ->
        val level = report.split(" ").map(String::toInt)
        if (isTheLevelSafe(level)) {
            acc + 1
        } else {
            val canBeMadeSafe = level.indices.any { idx ->
                isTheLevelSafe(level.filterIndexed { i, _ -> i != idx })
            }
            if (canBeMadeSafe) acc + 1 else acc
        }
    }
}

val isStep = { level: List<Int>, increasing: Boolean -> level.zipWithNext().all { (f, s) -> if (increasing) f < s else f > s } }
val areNormalIncreases = { level: List<Int> -> level.zipWithNext().all { (f, s) -> abs(f - s) <= 3 } }
val isTheLevelSafe = { level: List<Int> -> (isStep(level, true) || isStep(level, false)) && areNormalIncreases(level) }


fun main() {
    val testReports = readInput("Day02_test")
    check(part1(testReports) == 2)
    check(part2(testReports) == 4)

    val reports = readInput("Day02")
    check(part1(reports) == 606)
    check(part2(reports) == 644)
}