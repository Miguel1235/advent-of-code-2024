import kotlin.math.pow

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

private fun obtainOutput(
    program: List<Pair<Opcode, Int>>,
    registers: List<Long>,
): String {
    val (a,b,c) = registers
    var a1 = a
    var b1 = b
    var c1 = c
    val output = mutableListOf<Long>()
    var instructionPointer = 0
    while (instructionPointer < program.size) {
        val (instruction, operand) = program[instructionPointer]
        when (instruction) {
            Opcode.ADV -> a1 /= getDenominator(operand, a1, b1, c1)
            Opcode.BXL -> b1 = b1.xor(operand.toLong())
            Opcode.BST -> b1 = getComboValue(operand.toLong(), a1, b1, c1) % 8
            Opcode.BXC -> b1 = b1.xor(c1)
            Opcode.OUT -> output.add(getComboValue(operand.toLong(), a1, b1, c1) % 8)
            Opcode.BDV -> b1 = a1 / getDenominator(operand, a1, b1, c1)
            Opcode.CDV -> c1 = a1 / getDenominator(operand, a1, b1, c1)
            Opcode.JNZ -> {
                if (a1 == 0L) break
                instructionPointer = operand
                continue
            }
        }
        instructionPointer++
    }
    return output.joinToString(separator = ",")
}

private val obtainProgram = { input: List<String> -> input.last().split(":").last().trim().split(",").chunked(2).map { ins -> Pair(Opcode.entries.first { it.ordinal == ins[0].toInt() }, ins[1].toInt()) } }
private val obtainRegisters = { input: List<String >-> input.take(3).map { it.split(":").last().trim().toLong() } }


private fun part1(program: List<Pair<Opcode, Int>>, registers: List<Long>): String {
    return obtainOutput(program, registers)
}

private fun part2(program: List<Pair<Opcode, Int>>, registers: List<Long>): Long {
    val programString = program.map { listOf(it.first.ordinal, it.second)}.flatten().joinToString(",")


    val min = 8.0.pow(program.size*2-1).toLong()
    val max = 8.0.pow(program.size*2).toLong()

    val (_, b,c) = registers

//    println("min: $min max: $max")

    var current = min
    while(current < max) {
        val result = obtainOutput(program, listOf(current,b,c))
//        println("(a:$current)=$result")
        if(programString == result) break
        current++
    }
//    println("The a to match the same program is $current")

    return current
}

fun main() {
    val testInput = readInput("Day17_test")
    val testProgram = obtainProgram(testInput)
    val testRegisters = obtainRegisters(testInput)

    check(part1(testProgram, testRegisters) == "4,6,3,5,6,3,5,2,1,0")
    part2(testProgram, testRegisters).println()

    val input = readInput("Day17")
    val program = obtainProgram(input)
    val registers = obtainRegisters(input)
    check(part1(program, registers) == "1,5,7,4,1,6,0,3,0")
//    part2(program, registers)
}
 