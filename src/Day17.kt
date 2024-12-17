import kotlin.math.pow

//data class Reg(val name: Char, var value: Long = 0) {
//    override fun toString() = "$name:$value"
//}

private enum class Opcode { ADV, BXL, BST, JNZ, BXC, OUT, BDV, CDV }

private fun getDenominator(operand: Int, a: Long, b: Long, c: Long) =
    (2.0.pow(getComboValue(operand.toLong(), a,b,c).toDouble())).toLong()

private fun getComboValue(operand: Long, a: Long, b: Long, c: Long): Long {
    return when (operand) {
            1L,2L,3L -> operand
            4L -> a
            5L -> b
            else -> c
        }
}


private fun part1(input: List<String>): String {
    val program = input.last().split(":").last().trim().split(",").chunked(2).map { ins -> Pair(Opcode.entries.first { it.ordinal == ins[0].toInt() }, ins[1].toInt()) }
    var (a, b, c) = input.take(3).map { it.split(":").last().trim().toLong() }

    val output = mutableListOf<Long>()
    var instructionPointer = 0
    while (instructionPointer < program.size) {
        val (instruction, operand) = program[instructionPointer]
        when (instruction) {
            Opcode.ADV -> a /= getDenominator(operand, a, b, c)
            Opcode.BXL -> b = b.xor(operand.toLong())
            Opcode.BST -> b = getComboValue(operand.toLong(), a, b, c) % 8
            Opcode.BXC -> b = b.xor(c)
            Opcode.OUT -> output.add(getComboValue(operand.toLong(), a, b, c) % 8)
            Opcode.BDV -> b = a / getDenominator(operand, a, b, c)
            Opcode.CDV -> c = a / getDenominator(operand, a, b, c)
            Opcode.JNZ -> {
                if (a == 0L) break
                instructionPointer = operand
                continue
            }
        }
        instructionPointer++
    }
    return output.joinToString(separator = ",")
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day17_test")

    check(part1(testInput) == "4,2,5,6,7,7,7,7,3,1,0")
//    check(part2(testInput) == 0)

    val input = readInput("Day17")
    check(part1(input) == "1,5,7,4,1,6,0,3,0")
//    check(part2(input) == 0)
}
 