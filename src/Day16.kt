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

private data class Graph(val nodes: Map<Node, List<Node>>)

private fun part1(input: List<String>): Int {

    val inputNodes: MutableList<List<Node>> = mutableListOf()
    for(row in input.indices) {
        val rNodes = mutableListOf<Node>()
        for(col in input[row].indices) {
            val name = input[row][col]
                rNodes.add(Node(row, col,name ))
        }
        if(rNodes.size > 0) {
            inputNodes.add(rNodes)
        }
    }

    val gh = makeGraph(inputNodes)
    val (rs, cs) = findStartingPoint(gh, 'S')
    val (re, ce) = findStartingPoint(gh, 'E')

    return 0
}

private fun findPath() {

}

private fun makeGraph(input: List<List<Node>>): Graph {
    val nodes = mutableMapOf<Node, MutableList<Node>>()

    for(r in input.indices) {
        for(c in input[r].indices) {
            val nbs = mutableListOf<Node>()
            for((dr, dc) in directions) {
                val nb = input.getOrNull(r+dr)?.getOrNull(c+dc)
                if(nb != null) nbs.add(nb)
            }
            nodes[input[r][c]] = nbs
        }
    }

    return Graph(nodes)
}

private fun findStartingPoint(graph: Graph, target: Char): Pair<Int, Int> {
    for(node in graph.nodes) {
        if(node.key.name == target) {
            return Pair(node.key.r, node.key.c)
        }
    }
//    for(r in maze.indices) {
//        for(c in maze[r].indices) {
//            if(maze[r][c] == target) return Pair(r,c)
//        }
//    }
    error("Cannot find target :(")
}



fun main() {
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)

//    val input = readInput("Day16")
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 