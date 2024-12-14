import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Thread.sleep
import javax.imageio.ImageIO

private fun part1(input: List<String>, tall: Int = 7, wide: Int = 11): Int {
    val regex = Regex("""p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""")

    val robots = mutableListOf<Robot>()
    val bathRoom = List(tall) { List(wide) { "." } }

    for (line in input) {
        val (c, r, cv, rv) = regex.find(line)!!.groupValues.takeLast(4).map { it.toInt() }
        val robot = Robot(Pair(r, c), Pair(rv, cv))
        val seconds = 100
        val sq = generateSequence(robot) {
            val (r, c) = it.position
            val (rv, cv) = it.velocity

            var newC = c + cv
            var newR = r + rv

            if (newR < 0) {
                newR += tall
            }

            if (newR >= tall) {
                newR -= tall
            }

            if (newC < 0) {
                newC += wide
            }
            if (newC >= wide) {
                newC -= wide
            }
            Robot(Pair(newR, newC), Pair(rv, cv))
        }.take(seconds + 1)
        robots.add(sq.last())
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
    val bathRoom = List(tall) { List(wide) { "." } }
    val secondRobots: MutableMap<Int, MutableList<Robot>> = mutableMapOf()

    for (line in input) {
        val (c, r, cv, rv) = regex.find(line)!!.groupValues.takeLast(4).map { it.toInt() }
        val robot = Robot(Pair(r, c), Pair(rv, cv))
        val seconds = 10_000
        val sq = generateSequence(robot) {
            val (r, c) = it.position
            val (rv, cv) = it.velocity

            var newC = c + cv
            var newR = r + rv

            if (newR < 0) {
                newR += tall
            }
            if (newR >= tall) {
                newR -= tall
            }
            if (newC < 0) {
                newC += wide
            }
            if (newC >= wide) {
                newC -= wide
            }
            Robot(Pair(newR, newC), Pair(rv, cv))
        }.take(seconds + 1).toList()
        for(second in sq.indices) {
            val rb = sq[second]
            secondRobots.putIfAbsent(second, mutableListOf())
            secondRobots[second]?.add(rb)
        }
    }

    for((s,a) in secondRobots.entries) {
        println("SECOND: $s")
        printBath(bathRoom, a.map { it.position }, s)
    }
    return 0
}
data class Robot(val position: Pair<Int, Int>, val velocity: Pair<Int, Int>)

private fun printBath(bathRoom: List<List<String>>, robots: List<Pair<Int, Int>>, imageName: Int) {

    val cellSize = 20
    val width = bathRoom[0].size * cellSize
    val height = bathRoom.size * cellSize

    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()

    graphics.color = Color.BLACK
    graphics.fillRect(0, 0, width, height)

    graphics.color = Color.RED
    for ((r, c) in robots) {
        val x = c * cellSize
        val y = r * cellSize
        graphics.fillOval(x + cellSize / 4, y + cellSize / 4, cellSize / 2, cellSize / 2)
    }

    // Clean up graphics
    graphics.dispose()

    // Save the image to the specified file path
    val outputFile = File("/Users/miguel/Downloads/pics/${String.format("%05d", imageName)}.png")
    ImageIO.write(image, "png", outputFile)
}

fun main() {
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 12)

    val input = readInput("Day14")
    check(part1(input, 103, 101) == 221655456)
}
 