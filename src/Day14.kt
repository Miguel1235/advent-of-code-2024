val robotSequence = { robot: Robot, tall: Int, wide: Int ->  generateSequence(robot) {
    val (r, c) = it.position
    val (rv, cv) = it.velocity
    var newC = c + cv
    var newR = r + rv
    if (newR < 0) newR += tall
    if (newR >= tall) newR -= tall
    if (newC < 0) newC += wide
    if (newC >= wide) newC -= wide
    Robot(Pair(newR, newC), Pair(rv, cv))
}}

data class Robot(val position: Pair<Int, Int>, val velocity: Pair<Int, Int>)


private fun part1(input: List<String>, tall: Int = 7, wide: Int = 11): Int {
    val regex = Regex("""p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""")
    val robots = mutableListOf<Robot>()
    for (line in input) {
        val (c, r, cv, rv) = regex.find(line)!!.groupValues.takeLast(4).map { it.toInt() }
        val robot = Robot(Pair(r, c), Pair(rv, cv))
        val seconds = 100
        val sq = robotSequence(robot, tall, wide).take(seconds + 1).toList()
        robots.add(sq.take(seconds + 1).last())
    }

    val results = robots.groupBy { it.position }.mapValues { it.value.size }
    val hl = wide / 2
    val vl = tall / 2

    val quadrantSum = MutableList(4) { 0 }
    for ((k, v) in results) {
        when {
            k.first < vl && k.second < hl -> quadrantSum[0] += v
            k.first < vl && k.second > hl -> quadrantSum[1] += v
            k.first > vl && k.second < hl -> quadrantSum[2] += v
            k.first > vl && k.second > hl -> quadrantSum[3] += v
        }
    }
    val r = quadrantSum.fold(1) { acc, value -> acc * value }
    return r
}

private fun part2(input: List<String>, tall: Int = 7, wide: Int = 11): Int {
    val regex = Regex("""p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""")
    val secondRobots: MutableMap<Int, MutableList<Robot>> = mutableMapOf()

    for (line in input) {
        val (c, r, cv, rv) = regex.find(line)!!.groupValues.takeLast(4).map { it.toInt() }
        val robot = Robot(Pair(r, c), Pair(rv, cv))
        val seconds = 10_000
        val sq = robotSequence(robot, tall, wide).take(seconds + 1).toList()
        for(second in sq.indices) {
            val rb = sq[second]
            secondRobots.putIfAbsent(second, mutableListOf())
            secondRobots[second]?.add(rb)
        }
    }

    for((s,a) in secondRobots.entries) {
        val positions = a.map { it.position }
        val positionsSet = positions.toSet()
        if(positions.size == positionsSet.size) return s
    }
    error("No solution found!")
}

fun main() {
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 12)

    val input = readInput("Day14")
    check(part1(input, 103, 101) == 221655456)
    check(part2(input, 103,101) == 7858)
}
 