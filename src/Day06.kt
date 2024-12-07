private fun part(input: List<List<Char>>): Int {
    val allCords: MutableList<Pair<Int, Int>> = mutableListOf(findCurrentCord(input))
    var currentDirection = Directions.UP

    while(true) {
//        prettyPrint(input, currentDirection, allCords.last())
        if(isOutOfBounds(input, currentDirection, allCords.last())) {
            break
        }
        if(canMoveGuard(input, allCords.last(), currentDirection)) {
            val newCords = moveGuard(allCords.last(), currentDirection)
            allCords.add(newCords)
        } else {
            currentDirection = changeDirection(currentDirection)
        }
    }
    return allCords.toSet().count()
}

private fun part2(input: List<List<Char>>): Int {
    var isLoop = true
    var totalLoops = 0
    for(r in input.indices) {
        for(c in input[r].indices) {
            val allCords: MutableList<Pair<Int, Int>> = mutableListOf(findCurrentCord(input))
            var currentDirection = Directions.UP
            val newInput = input.toMutableList().map { it.toMutableList() }
            newInput[r][c] = 'M'

            for(i in 0 until 10000) {
//                prettyPrint(newInput, currentDirection, allCords.last())
                if(isOutOfBounds(newInput, currentDirection, allCords.last())) {
                    isLoop = false
                    break
                }
                if(canMoveGuard(newInput, allCords.last(), currentDirection)) {
                    val newCords = moveGuard(allCords.last(), currentDirection)
                    allCords.add(newCords)
                } else {
                    currentDirection = changeDirection(currentDirection)
                }
                isLoop = true
            }
            if(isLoop) {
                totalLoops++
            }
        }
    }
    return totalLoops
}

private fun isOutOfBounds(input: List<List<Char>>,dir: Directions, cord: Pair<Int, Int>): Boolean {
    return when (dir) {
        Directions.UP -> {
            input.getOrNull(cord.first-1)?.getOrNull(cord.second) == null
        }
        Directions.LEFT -> {
             input.getOrNull(cord.first)?.getOrNull(cord.second-1) == null

        }
        Directions.RIGHT -> {
            input.getOrNull(cord.first)?.getOrNull(cord.second+1) == null
        }
        Directions.DOWN -> {
            input.getOrNull(cord.first+1)?.getOrNull(cord.second) == null
        }
    }
}

private fun prettyPrint(input: List<List<Char>>, position: Directions, cord: Pair<Int, Int>) {
    println("*******START*******")
    for(r in input.indices) {
        for(c in input[r].indices) {
            if(cord.first == r && cord.second == c) {
                print(position.symbol)
            } else {
                print(input[r][c])
            }
        }
        println()
    }
    println("********END********")
}

private fun changeDirection(dir: Directions): Directions {
    return when(dir) {
        Directions.DOWN -> Directions.LEFT
        Directions.UP -> Directions.RIGHT
        Directions.LEFT -> Directions.UP
        Directions.RIGHT -> Directions.DOWN
    }
}

private enum class Directions(val symbol: Char) {
    UP('^'),
    DOWN('v'),
    LEFT('<'),
    RIGHT('>')
}

private fun findCurrentCord(input: List<List<Char>>): Pair<Int, Int> {
    for (r in input.indices) {
        for (c in input[r].indices) {
            if (input[r][c] in Directions.entries.map { it.symbol }) {
                return Pair(r,c)
            }
        }
    }
    return Pair(0,0)
}

private fun moveGuard(cord: Pair<Int, Int>, dir: Directions): Pair<Int, Int> {
    return when (dir) {
        Directions.UP -> {
            Pair(cord.first-1, cord.second)
        }
        Directions.LEFT -> {
            Pair(cord.first, cord.second-1)
        }
        Directions.RIGHT -> {
            Pair(cord.first, cord.second+1)

        }
        Directions.DOWN -> {
            Pair(cord.first+1, cord.second)
        }
    }

}

private fun canMoveGuard(input: List<List<Char>>, cord: Pair<Int, Int>, dir: Directions): Boolean {
    when (dir) {
        Directions.UP -> {
            val nextCell = input.getOrNull(cord.first-1)?.getOrNull(cord.second)
            return nextCell == '.' || nextCell == '^'
        }
        Directions.LEFT -> {
            val nextCell = input.getOrNull(cord.first)?.getOrNull(cord.second-1)
            return nextCell == '.' || nextCell == '^'

        }
        Directions.RIGHT -> {
            val nextCell = input.getOrNull(cord.first)?.getOrNull(cord.second+1)
            return nextCell == '.' || nextCell == '^'
        }
        Directions.DOWN -> {
            val nextCell = input.getOrNull(cord.first+1)?.getOrNull(cord.second)
            return nextCell == '.' || nextCell == '^'
        }
    }
}

fun main() {
    val testInput = readInput("Day06_test").map { it.toList() }
    check(part(testInput) ==41)
    check(part2(testInput)==6)

    val input = readInput("Day06").map { it.toList() }
    check(part(input) == 5131)
    check(part2(input) == 1784)
}