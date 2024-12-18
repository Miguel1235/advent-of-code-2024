private class Gh(private val map: List<List<Vertex>>) {
    val directions = listOf(
        Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0)
    )

    val nodes = buildMap {
        map.forEachIndexed { r, row ->
            row.forEachIndexed { c, node ->
                val neighbors = directions.mapNotNull { (dx, dy) -> map.getOrNull(r + dx)?.getOrNull(c + dy) }
                    .filter { it.name != '#' }
                put(node, neighbors)
            }
        }
    }

    fun bfs(start: Vertex, end: Vertex): List<Vertex> {
        val visited = mutableSetOf<Vertex>()
        val queue: ArrayDeque<List<Vertex>> = ArrayDeque() // Stores paths
        queue.add(listOf(start))
        while (queue.isNotEmpty()) {
            val path = queue.removeFirst()
            val node = path.last()

            if (node == end) return path
            if (node !in visited) {
                visited.add(node)
                nodes[node]?.forEach { neighbor ->
                    queue.addLast(path + neighbor)
                }
            }
        }
        throw Exception("Ups there was an error")
    }
}

private data class Vertex(val name: Char, val position: Pair<Int, Int>) {
    override fun toString() = "$name"
}

private fun part1(input: List<String>, size: Int = 70, bytes: Int = 1024): Int {
    val reducedBytes = reduceBytes(input)
    val grid = obtainGrid(size, reducedBytes.take(bytes))
    return Gh(grid).bfs(grid[0][0], grid[size][size]).size - 1
}

val reduceBytes = { input: List<String> ->
    input.map {
        val (r, c) = it.split(",").map { it.toInt() }
        Pair(r, c)
    }
}

private fun part2(input: List<String>, size: Int = 70): Pair<Int, Int> {
    val reducedBytes = reduceBytes(input)
    var total = 1
    try {
        while (true) {
            val grid = obtainGrid(size, reducedBytes.take(total))
            Gh(grid).bfs(grid[0][0], grid[size][size]).size - 1
            total++
        }
    } catch (e: Exception) {
        return reducedBytes[total - 1]
    }
}

private val obtainGrid = { size: Int, reducedBytes: List<Pair<Int, Int>> ->
    List(size + 1) { r ->
        List(size + 1) { c ->
            val position = c to r
            Vertex(if (position in reducedBytes) '#' else '.', position)
        }
    }
}

fun main() {
    val testInput = readInput("Day18_test")
    check(part1(testInput, 6, 12) == 22)
    check(part2(testInput, 6) == Pair(6, 1))

    val input = readInput("Day18")
    check(part1(input) == 372)
    check(part2(input) == Pair(25, 6))
}
 