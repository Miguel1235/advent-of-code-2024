import java.util.*

val directions = listOf(
    Pair(0,-1),
    Pair(1,0),
    Pair(0,1),
    Pair(-1,0)
)

private data class Node(val r: Int, val c: Int, val name: Char) {
    override fun toString(): String {
        return "$name"
    }
}

private data class Graph(val nodes: Map<Node, List<Node>>) {
    fun findNode(target: Char): Node {
        for(node in nodes) {
            if(node.key.name == target) {
                return node.key
            }
        }
        error("Cannot find target :(")
    }

    fun shortestPath(start: Node, end: Node): Int {
        val visited = mutableSetOf<Triple<Int, Int, Pair<Int, Int>>>()
        val queue = PriorityQueue<State>()

        queue.add(State(start, 0, directions[2])) // east is always the first node

        while (queue.isNotEmpty()) {
            val (currentNode, currentCost, currentDir) = queue.poll()
            if (currentNode == end) return currentCost

            if (!visited.add(Triple(currentNode.r, currentNode.c, currentDir))) continue

            // Iterate through neighbors
            nodes[currentNode]?.forEach {
                val newDir = Pair(it.r - currentNode.r, it.c - currentNode.c)
                val totalCost = currentCost + 1 + if (newDir != currentDir) 1000 else 0
                queue.add(State(it, totalCost, newDir))
            }
        }
        error("No possible path!")
    }
}

private data class State(val node: Node, val cost: Int, val direction: Pair<Int, Int>) : Comparable<State> {
    override fun compareTo(other: State) = this.cost - other.cost
}

private fun makeGraph(input: List<List<Node>>): Graph {
    val nodes = mutableMapOf<Node, List<Node>>()

    for(r in input.indices) {
        for(c in input[r].indices) {
            val nbs = mutableListOf<Node>()
            for((dr, dc) in directions) {
                val nb = input.getOrNull(r+dr)?.getOrNull(c+dc)
                if(nb != null) nbs.add(nb)
            }
            nodes[input[r][c]] = nbs.filter { it.name != '#' }.filterNotNull()
        }
    }
    return Graph(nodes)
}

private fun part1(input: List<String>): Int {
    val inputNodes: MutableList<List<Node>> = mutableListOf()
    for(row in input.indices) {
        val rNodes = mutableListOf<Node>()
        for(col in input[row].indices) {
            val name = input[row][col]
            rNodes.add(Node(row, col,name ))
        }
        if(rNodes.size > 0) inputNodes.add(rNodes)

    }
    val gh = makeGraph(inputNodes)

    val start = gh.findNode('S')
    val end = gh.findNode('E')
    return gh.shortestPath(start, end)
}

fun main() {
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 11048)
//    check(part2(testInput) == 0)

    val input = readInput("Day16")
    check(part1(input) == 88468)
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 