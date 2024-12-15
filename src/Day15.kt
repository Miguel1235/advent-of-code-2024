private fun part1(input: String): Int {
    val (mapI, instructionsI) = input.split("\n\n")
    val mapSplit = mapI.split("\n")

    val instructionList = instructionsI.toList().filter { it != '\n' }.map {
        when (it) {
            '^' -> MoveInstruction.UP
            'v' -> MoveInstruction.DOWN
            '<' -> MoveInstruction.LEFT
            else -> MoveInstruction.RIGHT
        }
    }
    val startingPoint = findStartingPoint(mapSplit)


    val map = mapSplit.map { it.map {
        when (it) {
                'O' -> Tile(it, canBePushed = true)
                '#' -> Tile(it)
                else -> Tile('.', canWalk = true, canBePushed = true)
        }
    }.toMutableList()}.toMutableList()

//    println(map)
    prettyPrint(map, startingPoint)

//    moveRobot(MoveInstruction.UP, map, startingPoint)
    return 0
}

fun isInBounds(grid: List<List<Tile>>, x: Int, y: Int): Boolean {
    return x in grid.indices && y in grid[x].indices
}


fun swapTiles(grid: MutableList<MutableList<Tile>>, x1: Int, y1: Int, x2: Int, y2: Int) {
    val temp = grid[x1][y1]
    grid[x1][y1] = grid[x2][y2]
    grid[x2][y2] = temp
}

private fun prettyPrint(map: List<List<Tile>>, robot: Pair<Int, Int>) {
    for(r in map.indices) {
        for(c in map[r].indices) {
            print("${if(robot == Pair(r,c)) '@' else map[r][c]}")
        }
        println()
    }
}

enum class MoveInstruction(name: Char) {
    UP('^'), DOWN('v'), LEFT('<'), RIGHT('>')
}

private fun findStartingPoint(map: List<String>): Pair<Int, Int> {
    for (r in map.indices) {
        for (c in map[r].indices) {
            if (map[r][c] == '@') {
                return Pair(r, c)
            }
        }
    }
    error("Couldn't find the starting point :(")
}

data class Tile(val name: Char, val canBePushed: Boolean = false, val canWalk: Boolean = false) {
    override fun toString() = "$name"
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readOneLine("Day15_test")
    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)

//    val input = readInput("Day15")
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 