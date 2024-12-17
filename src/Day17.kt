import kotlin.math.pow

data class Reg(val name: Char, var value: Long = 0) {
    override fun toString() = "$name:$value"
}

private enum class Opcode { ADV, BXL, BST, JNZ, BXC, OUT, BDV, CDV }

private fun part1(input: List<String>): String {
    val program = input.last().split(":").last().trim().split(",").chunked(2).map { ins ->
            Pair(Opcode.entries.first { it.ordinal == ins[0].toInt() }, ins[1].toInt())
        }

    val (a, b, c) = input.take(3).map { it.split(":").last().trim().toLong() }.mapIndexed { i, v ->
        Reg(
            when (i) {
                0 -> 'A'
                1 -> 'B'
                else -> 'C'
            }, v
        )
    }

    val output = mutableListOf<Long>()
    var instructionPointer = 0

    while (instructionPointer < program.size) {
        val (instruction, operand) = program[instructionPointer]

        when (instruction) {
            Opcode.ADV -> {
                a.value /= getDenominator(operand, a, b, c)
            }

            Opcode.BXL -> {
                b.value = b.value.xor(operand.toLong())
            }

            Opcode.BST -> {
                b.value = getComboValue(operand.toLong(), listOf(a, b, c)) % 8
            }

            Opcode.JNZ -> {
                if (a.value == 0L) break
                instructionPointer = operand
                continue
            }

            Opcode.BXC -> {
                b.value = b.value.xor(c.value)
            }

            Opcode.OUT -> {
                output.add(getComboValue(operand.toLong(), listOf(a, b, c)) % 8)
            }

            Opcode.BDV -> {
                b.value = a.value / getDenominator(operand, a, b, c)
            }

            Opcode.CDV -> {
                c.value = a.value / getDenominator(operand, a, b, c)
            }
        }
        instructionPointer++
    }
    return output.joinToString(separator = ",")
}

private fun getDenominator(operand: Int, a: Reg, b: Reg, c: Reg) =
    (2.0.pow(getComboValue(operand.toLong(), listOf(a, b, c)).toDouble())).toLong()

private fun getComboValue(operand: Long, regs: List<Reg>): Long {
    return if (operand <= 3) {
        operand
    } else {
        when (operand) {
            4L -> regs[0].value
            5L -> regs[1].value
            else -> regs[2].value
        }
    }
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
 