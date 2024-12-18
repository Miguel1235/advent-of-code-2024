private class Gh(
    private val map: List<List<Vertex>>,
) {
    val directions = listOf(
        Pair(0,-1),
        Pair(1,0),
        Pair(0,1),
        Pair(-1,0)
    )

    val nodes: Map<Vertex, List<Vertex>> = buildMap {
        for (r in map.indices) {
            for (c in map[r].indices) {
                val node = map[r][c]
                val nbs = mutableListOf<Vertex>()
                for ((dx, dy) in directions) {
                    val nb = map.getOrNull(r + dx)?.getOrNull(c + dy)
                    if (nb != null) nbs.add(nb)
                }
                put(node, nbs.filter { it.name != '#' })
            }
        }
    }

    fun bfs(start: Vertex, end: Vertex): List<Vertex> {
        val visited =  mutableSetOf<Vertex>()
        val queue: ArrayDeque<List<Vertex>> = ArrayDeque() // Stores paths
        queue.add(listOf(start))
        while(queue.isNotEmpty()) {
            val path = queue.removeFirst()
            val node = path.last()

            if (node == end) return path
            if(node !in visited) {
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
    val reducedBytes = input.map {
        val (r, c) = it.split(",").map { it.toInt() }
        Pair(r,c)
    }.take(bytes)

    val grid = obtainGrid(size, reducedBytes)
    val gh = Gh(grid)

    return gh.bfs(grid[0][0], grid[size][size]).size - 1
}


private fun prettyPrint(map: List<List<Vertex>>) {
    for(r in map.indices) {
        for(c in map[r].indices) {
            print(map[r][c])
        }
        println()
    }
    println()
}

private fun part2(input: List<String>, size: Int = 70): Pair<Int, Int> {
    val reducedBytes = input.map {
        val (r, c) = it.split(",").map { it.toInt() }
        Pair(r,c)
    }

    var total = 1
    try {
        while(true) {
            val grid = obtainGrid(size, reducedBytes.take(total))
            val gh = Gh(grid)

            gh.bfs(grid[0][0], grid[size][size]).size - 1
            total++
        }
    } catch (e: Exception) {
        return reducedBytes[total-1]
    }
}

private fun obtainGrid(size: Int, reducedBytes: List<Pair<Int, Int>>): List<List<Vertex>> {
    return buildList {
        for (r in 0..size) {
            val row = buildList {
                for (c in 0..size) {
                    val position = Pair(c, r)
                    add(Vertex(if (reducedBytes.contains(position)) '#' else '.', position))
                }
            }
            add(row)
        }
    }
}

fun main() {
    val testInput = readInput("Day18_test")
    check(part1(testInput, 6, 12) == 22)
    check(part2(testInput, 6) == Pair(6,1))

    val input = readInput("Day18")
    check(part1(input) == 372)
    check(part2(input) == Pair(25,6))
}
 