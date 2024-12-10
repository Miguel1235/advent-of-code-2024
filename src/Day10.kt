data class Node(val row: Int, val col: Int) {
    override fun toString(): String {
        return "$row:$col"
    }
}
data class Graph(val nodes: Map<Node, List<Node>>)

private fun obtainPositions(input: List<String>): Map<Node, Int> {
    val positions = mutableMapOf<Node, Int>()
    for(row in input.indices) {
        for(col in input[row].indices) {
            positions[Node(row, col)] = input[row][col].digitToInt()
        }
    }
    return positions
}


private fun parseInput(positions: Map<Node, Int>): Graph {
    val nodes = mutableMapOf<Node, MutableList<Node>>()
    for ((node, value) in positions) {
        val neighbors = listOf(
            Node(node.row + 1, node.col),
            Node(node.row - 1, node.col),
            Node(node.row, node.col + 1),
            Node(node.row, node.col - 1)
        )
            .filter { it in positions }
            .filter { value + 1 == positions[(Node(it.row, it.col))] }
        nodes[node] = neighbors.toMutableList()
    }
    return Graph(nodes)
}

private fun findStartNodes(positions: Map<Node, Int>): List<Node> {
    val startNodes = mutableListOf<Node>()
    for ((node, value) in positions) {
        if(value == 0) startNodes.add(node)
    }
    return startNodes
}

private fun findPaths(graph: Graph, start: Node, positions: Map<Node, Int>): List<List<Node>> {
    val paths = mutableListOf<List<Node>>()

    fun dfs(current: Node, visited: MutableSet<Node> = mutableSetOf(), path: MutableList<Node> = mutableListOf()) {
        if (visited.contains(current)) return

        visited.add(current)
        path.add(current)

        if (positions[current] == 9) {
            paths.add(path.toList())
        } else {
            graph.nodes[current]?.forEach { neighbor ->
                dfs(neighbor, visited.toMutableSet(), path.toMutableList())
            }
        }
    }
    dfs(start)

    return paths
}

private fun part1(input: List<String>): Int {
    val positions = obtainPositions(input)
    val graph = parseInput(positions)

    return findStartNodes(positions).sumOf {
        findPaths(graph, it, positions)
            .flatten()
            .filter { positions[it] == 9 }
            .toSet()
            .count()
    }
}

private fun part2(input: List<String>): Int {
    val positions = obtainPositions(input)
    val graph = parseInput(positions)

    return findStartNodes(positions).sumOf {
        findPaths(graph, it, positions).size
    }
}

fun main() {
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    val input = readInput("Day10")
    check(part1(input) == 694)
    check(part2(input) == 1497)
}
 