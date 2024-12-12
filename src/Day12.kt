data class GardenPlot(val name: Char, val titles: List<Pair<Int, Int>>, val area: Int) {
    val perimeter: Int
        get() {
            val directions = listOf(
                Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1)
            )

            var perimeter = 0
            for ((x, y) in this.titles) {
                for ((dx, dy) in directions) {
                    val neighbor = Pair(x + dx, y + dy)
                    if (neighbor !in this.titles) {
                        perimeter++
                    }
                }
            }
            return perimeter
        }
    val corners: Int
        get() {
            var corners = 0
            for ((r, c) in titles) {
                val nup = Pair(r - 1, c)
                val ndown = Pair(r + 1, c)
                val nr = Pair(r, c + 1)
                val nl = Pair(r, c - 1)

                if (nup !in titles && nr !in titles) corners++
                if (nr !in titles && ndown !in titles) corners++
                if (ndown !in titles && nl !in titles) corners++
                if (nl !in titles && nup !in titles) corners++
                if (Pair(r - 1, c - 1) !in titles && nup in titles && nl in titles) corners++
                if (Pair(r - 1, c + 1) !in titles && nup in titles && nr in titles) corners++
                if (Pair(r + 1, c + 1) !in titles && ndown in titles && nr in titles) corners++
                if (Pair(r + 1, c - 1) !in titles && ndown in titles && nl in titles) corners++

            }
            return corners
        }
}

private fun part(input: List<String>, isPart1: Boolean = true): Int {
    val map = input.map { it.toList() }
    val plantTypes = map.flatten().toSet()

    val allPlots = mutableListOf<GardenPlot>()

    for (plantType in plantTypes) {
        val starterTitle = findPlant(map, plantType)
        for (title in starterTitle) {
            val allTitles = expandTitles(map, title)

            val sortedPairs = allTitles.sortedWith(compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second })

            val garden = GardenPlot(plantType, sortedPairs, sortedPairs.size)

            if (!allPlots.contains(garden)) {
                allPlots.add(garden)
            }
        }
    }
    var p1 = 0
    var p2 = 0
    for (plot in allPlots) {
        p1 += plot.area * plot.perimeter
        p2 += plot.area * plot.corners
    }
    return if (isPart1) p1 else p2
}

private fun findPlant(map: List<List<Char>>, plantType: Char): List<Pair<Int, Int>> {
    val pairs = mutableListOf<Pair<Int, Int>>()
    for (row in map.indices) {
        for (col in map[row].indices) {
            if (plantType == map[row][col]) {
                pairs.add(Pair(row, col))
            }
        }
    }
    return pairs
//    error("no plant found")
}


private fun expandTitles(map: List<List<Char>>, starter: Pair<Int, Int>): List<Pair<Int, Int>> {
    val queue = mutableListOf<Pair<Int, Int>>()
    val visited = mutableSetOf<Pair<Int, Int>>()
    queue.add(starter)
    while (queue.isNotEmpty()) {
        val extracted = queue.removeLast()
        visited.add(extracted)
        val nbs = checkNeighbors(map, extracted)
        val notVisited = nbs.filter { !visited.contains(it) }
        queue.addAll(notVisited)
    }
    return visited.toList()
}

private fun checkNeighbors(map: List<List<Char>>, starter: Pair<Int, Int>): List<Pair<Int, Int>> {
    val (r, c) = starter
    return listOfNotNull(
        map.getOrNull(r + 1)?.getOrNull(c)?.let { Pair(r + 1, c) },
        map.getOrNull(r - 1)?.getOrNull(c)?.let { Pair(r - 1, c) },
        map.getOrNull(r)?.getOrNull(c + 1)?.let { Pair(r, c + 1) },
        map.getOrNull(r)?.getOrNull(c - 1)?.let { Pair(r, c - 1) },
    ).filter { (r, c) -> map.getOrNull(r)?.getOrNull(c) == map[starter.first][starter.second] }.toSet().toList()

}

fun main() {
    val testInput = readInput("Day12_test")
    check(part(testInput) == 1930)
    check(part(testInput, false) == 1206)

    val input = readInput("Day12")
    check(part(input) == 1465112)
    part(input, false).println()
}
 