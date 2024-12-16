private fun part1(input: String): Int {
    val (mapI, instructionsI) = input.split("\n\n")
    val mapSplit = mapI.split("\n")

    val instructionList = instructionsI.toList().filter { it != '\n' }.map {
        when (it) {
            '^' -> Cord.UP
            'v' -> Cord.DOWN
            '<' -> Cord.LEFT
            else -> Cord.RIGHT
        }
    }

    var map = mapSplit.map { it.map {
        when (it) {
                'O' -> Tile(it, canBePushed = true)
                '#' -> Tile(it)
                '@' -> Tile(it, canWalk = true)
                else -> Tile('.', canWalk = true)
        }
    }.toMutableList()}.toMutableList()


    for(cord in instructionList) {
        map = moveRobot(cord, map, findStartingPoint(map))
    }

    var total = 0
    for(r in map.indices) {
        for(c in map[r].indices) {
            val value = map[r][c]
            if(value.name == 'O') {
                total += 100*r+c
            }
        }
    }

    return total
}

private fun moveRobot(cord: Cord, map: MutableList<MutableList<Tile>>, startingPoint: Pair<Int, Int>): MutableList<MutableList<Tile>> {
    val (r,c) = startingPoint
    val emptyTile = Tile('.', canWalk = true)
    val currentRobot = Tile('@', canWalk = true)
    val box = Tile('O', canBePushed = true)

    when(cord) {
        Cord.UP -> {
            if(map[r-1][c].name == '#') {
                return map
            }

            if(map[r-1][c].name == '.') {
                map[r-1][c] = map[r][c]
                map[r][c] = emptyTile
                return map
            }

            var tiles = mutableListOf<Tile>()
            for(i in map[r].size-1 downTo 0) {
                val tile = map[i][c]
                tiles.add(tile)
            }

            if(Regex("""@O+#""").containsMatchIn(tiles.joinToString(separator = ""))) {
                return map
            }


            var passedAt = false
            for(i in tiles.indices) {
                if(tiles[i].name == '@') passedAt = true
                if(tiles[i].name == '.' && passedAt) {
                    tiles[i] = box
                    break
                }
            }

            var p = false
            for(i in tiles.indices) {
                if(tiles[i].name == '@') {
                    tiles[i] = emptyTile
                    p = true
                }
                if(tiles[i].name == 'O' && p == true) {
                    tiles[i] = currentRobot
                    break
                }
            }
            tiles = tiles.reversed().toMutableList()

            for(i in map[0].size-1 downTo 0) {
                map[i][c] = tiles[i]
            }
            return map
        }
        Cord.DOWN -> {
            if(map[r+1][c].name == '#') return map

            if(map[r+1][c].name == '.') {
                map[r+1][c] = map[r][c]
                map[r][c] = emptyTile
                return map
            }

            var tiles = mutableListOf<Tile>()
            for(i in map[r].size-1 downTo 0) {
                val tile = map[i][c]
                tiles.add(tile)
            }

            if(Regex("""#O+@""").containsMatchIn(tiles.joinToString(separator = ""))) return map

            tiles = tiles.reversed().toMutableList()

            var passedAt = false
            for(i in tiles.indices) {
                if(tiles[i].name == '@') passedAt = true
                if(tiles[i].name == '.' && passedAt) {
                    tiles[i] = box
                    break
                }
            }


            var alreadyChanged = false
            for(i in tiles.indices) {
                if(tiles[i].name == '@') {
                    tiles[i] = emptyTile
                    alreadyChanged = true
                }
                if(tiles[i].name == 'O' && alreadyChanged) {
                    tiles[i] = currentRobot
                    break
                }
            }



            for(i in map[0].size-1 downTo 0) {
                map[i][c] = tiles[i]
            }
            return map
        }
        Cord.RIGHT -> {
            if(map[r][c+1].name == '#') {
                return map
            }
            if(map[r][c+1].name == '.') {
                map[r][c+1] = map[r][c]
                map[r][c] = emptyTile
                return map
            }

            var tiles = mutableListOf<Tile>()
            for(i in 0..<map[r].size) {
                val tile = map[r][i]
                tiles.add(tile)
            }

            if(Regex("""@O+#""").containsMatchIn(tiles.joinToString(separator = ""))) {
                return map
            }

            var passedAt = false
            for(i in tiles.indices) {
                if(tiles[i].name == '@') passedAt = true
                if(tiles[i].name == '.' && passedAt) {
                    tiles[i] = box
                    break
                }
            }

            var pass = false
            for(i in tiles.indices) {
                if(tiles[i].name == '@') {
                    tiles[i] = emptyTile
                    pass = true
                }
                if(tiles[i].name == 'O' && pass) {
                    tiles[i] = currentRobot
                    break
                }
            }


            for(i in 0..<map[r].size) map[r][i] = tiles[i]
            return map
        }
        Cord.LEFT -> {
            if(map[r][c-1].name == '#') {
                return map
            }
            if(map[r][c-1].name == '.') {
                map[r][c-1] = map[r][c]
                map[r][c] = emptyTile
                return map
            }

            var tiles = mutableListOf<Tile>()
            for(i in 0..<map[r].size) {
                val tile = map[r][i]
                tiles.add(tile)
            }


            tiles = tiles.reversed().toMutableList()

            if(Regex("""@O+#""").containsMatchIn(tiles.joinToString(separator = ""))) {
                return map
            }

            var passedAt = false
            for(i in tiles.indices) {
                if(tiles[i].name == '@') passedAt = true
                if(tiles[i].name == '.' && passedAt) {
                    tiles[i] = box
                    break
                }
            }

            var pass = false
            for(i in tiles.indices) {
                if(tiles[i].name == '@') {
                    tiles[i] = emptyTile
                    pass = true
                }
                if(tiles[i].name == 'O' && pass) {
                    tiles[i] = currentRobot
                    break
                }
            }

            tiles = tiles.reversed().toMutableList()

            for(i in 0..<map[r].size) map[r][i] = tiles[i]
            return map
        }
    }
}

private fun prettyPrint(map: List<List<Tile>>) {
    for(r in map.indices) {
        for(c in map[r].indices) {
            print(map[r][c])
        }
        println()
    }
    println()
}

enum class Cord(name: Char) {
    UP('^'), DOWN('v'), LEFT('<'), RIGHT('>')
}

private fun findStartingPoint(map: List<List<Tile>>): Pair<Int, Int> {
    for (r in map.indices) {
        for (c in map[r].indices) {
            if (map[r][c].name == '@') {
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
    check(part1(testInput) == 10092)
//    check(part2(testInput) == 0)

    val input = readOneLine("Day15")
    check(part1(input) == 1414416)
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 