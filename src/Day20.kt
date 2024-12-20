import java.util.concurrent.atomic.AtomicInteger


private fun part1(input: List<String>): Int {
    data class Node(val name: Char, val position: Pair<Int, Int>) {
        override fun toString() = "$name"
    }

    class Gh(private var map: List<List<Node>>) {
        val directions = listOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0))
//        val nodes: Map<Node, List<Node>> = buildMap {
//            map.forEachIndexed { r, row ->
//                row.forEachIndexed { c, node ->
//                    val neighbors = directions.mapNotNull { (dx, dy) -> map.getOrNull(r + dx)?.getOrNull(c + dy) }
////                        .filter { it.name != '#' }
//                    put(node, neighbors)
//                }
//            }
//        }

        val nodes: Map<Node, List<Node>>
            get() = buildMap {
            map.forEachIndexed { r, row ->
                row.forEachIndexed { c, node ->
                    val neighbors = directions.mapNotNull { (dx, dy) -> map.getOrNull(r + dx)?.getOrNull(c + dy) }
                    put(node, neighbors)
                }
            }
        }

        fun prettyPrint() {
            for(row in map.indices) {
                for(col in map[row].indices) {
                    print(map[row][col])
                }
                println("")
            }
        }

        fun findNode(name: Char): Node {
            for(row in map.indices) {
                for(col in map[row].indices) {
                    val node = map[row][col]
                    if(node.name == name) return node
                }
            }
            error("Ups, cannot find the node :(")
        }

        fun createNewGrid(target: Pair<Int, Int>, grid: List<List<Node>>) {
            map = buildList {
                for (r in grid.indices) {
                    val row = buildList {
                        for (c in grid[r].indices) {
                            val node = if(grid[r][c].position != target) grid[r][c] else Node('.', target)
                            add(node)
                        }
                    }
                    add(row)
                }
            }
        }

        fun bfs(start: Node, end: Node): Int {
            val visited = mutableSetOf<Node>()
//            val queue: ArrayDeque<List<Node>> = ArrayDeque() // Stores paths
//            queue.add(listOf(start))

            val queue: ArrayDeque<Pair<Node, Int>> = ArrayDeque() // Stores the node and its distance from the start
            queue.add(start to 0) // Initial distance is 0


            while (queue.isNotEmpty()) {
                val (node, steps) = queue.removeFirst()

                if (node == end) return steps
                if (node !in visited) {
                    visited.add(node)
                    nodes[node]?.forEach { neighbor ->
                        if(neighbor.name != '#') queue.addLast(neighbor to steps + 1) // Increment the step count
                    }
                }
            }
            throw Exception("Ups there was an error")
        }
    }


    val grid = input.mapIndexed { r, line -> line.mapIndexed { c, char -> Node(char, Pair(r, c)) } }

    val gh = Gh(grid)

    val startNode = gh.findNode('S')
    val endNode = gh.findNode('E')
    val basicSeconds = gh.bfs(startNode, endNode)
    println(basicSeconds)

    var total = 0
    for(row in grid.indices) {
        for(col in grid[row].indices) {
            println("$row:$col")
            val node = grid[row][col]
            if(node.name == 'S' || node.name == 'E') continue
            gh.createNewGrid(Pair(row,col), grid)

            val r = gh.bfs(startNode, endNode)

            val save = basicSeconds-r
            if(save >= 100) total++

        }
    }
    println("There are $total cheats that allow you to save more than 100 ps!")
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
//    val testInput = readInput("Day20_test")
//    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)
     
    val input = readInput("Day20")
    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 