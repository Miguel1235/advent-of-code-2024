import kotlin.math.abs

data class Block(val id: String)

private fun obtainBlockFile(diskMap: List<String>): List<Block> {
    val r = mutableListOf<Block>()
    var currentId = 0
    for (i in diskMap.indices) {
        val block = diskMap[i]
        for (z in 0 until block.toInt()) {
            if (i % 2 == 1) {
                r.add(Block("."))
            } else {
                r.add(Block(currentId.toString()))
            }
        }
        if (i % 2 == 0) currentId++
    }
    return r
}

private fun moveBlocks(blockFile: List<Block>): List<Block> {
    val ls = blockFile.toMutableList()

    var findFreeSpace = ls.indexOfFirst { it.id == "." }
    var findListRight = ls.indexOfLast { it.id != "." }

    while (abs(findFreeSpace - findListRight) > 1) {
        ls[findFreeSpace] = ls[findListRight].also { ls[findListRight] = ls[findFreeSpace] }

        findFreeSpace = ls.indexOfFirst { it.id == "." }
        findListRight = ls.indexOfLast { it.id != "." }
    }
    return ls
}

private fun part1(input: List<String>): Long {
    val diskMap = input[0].map { it.toString() }
    val blockFile = obtainBlockFile(diskMap)
    return moveBlocks(blockFile).foldIndexed(0L) { index, acc, value -> acc + if (value.id != ".") (value.id.toLong() * index) else 0 }
}

private fun part2(input: List<String>): Long {
    val diskMap = input[0].map { it.toString() }
    val blockFile = obtainBlockFile(diskMap)

    return moveContBlocks(blockFile).foldIndexed(0L) { index, acc, value -> acc + if (value.id != ".") (value.id.toLong() * index) else 0 }
}

private fun moveContBlocks(blockFile: List<Block>): List<Block> {
    val ls = blockFile.toMutableList()
    var lastId = ls.last { it.id != "." }.id.toInt()
    while (lastId >= 0) {
        val freeSpaces = obtainRanges(ls, Block("."))
        for (freeSpace in freeSpaces) {
            val idRange = obtainRanges(ls, Block(lastId.toString())).first()
            val tr = freeSpace.first.coerceAtLeast(0)..freeSpace.last.coerceAtMost(ls.size - 1)
            val ts = idRange.first.coerceAtLeast(0)..idRange.last.coerceAtMost(ls.size - 1)
            if (tr.count() < ts.count()) continue
            val sourceElements = ls.slice(ts)

            if (!(tr.first <= ts.first && tr.last < ts.first)) continue

            var sourceIndex = 0
            for (i in tr) {
                if (sourceIndex < sourceElements.size) {
                    ls[i] = sourceElements[sourceIndex]
                    sourceIndex++
                } else {
                    ls[i] = Block(".") // Or use any placeholder for empty space
                }
            }

            for (i in ts) ls[i] = Block(".")
            break
        }
        lastId--
    }
    return ls
}

private fun obtainRanges(input: List<Block>, block: Block): List<IntRange> {
    val ranges = mutableListOf<IntRange>()
    var startIndex: Int? = null

    for (i in input.indices) {
        if (input[i].id == block.id) {
            if (startIndex == null) startIndex = i
        } else {
            if (startIndex != null) {
                ranges.add(startIndex until i)
                startIndex = null
            }
        }
    }
    if (startIndex != null) {
        ranges.add(startIndex until input.size)
    }
    return ranges
}

fun main() {
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput("Day09")
    check(part1(input) == 6334655979668L)
    check(part2(input) == 6349492251099)
}
 