import java.io.File
import java.lang.RuntimeException
import java.util.regex.Pattern
import kotlin.streams.toList

const val PLUS_OPERATION_CODE = 1
const val MULTIPLY_OPERATION_CODE = 2
const val HALT_OPERATION_CODE = 99

class Day02 {
    private lateinit var memory: MutableList<Int>
    private val program: String
    constructor(program: String) {
        this.program = program
        storeProgramInMemory(this.program)
    }

    constructor(program: File) {
        this.program = program.readText()
        storeProgramInMemory(this.program)
    }

    private fun storeProgramInMemory(memory: String) {
        this.memory = Pattern.compile(",").splitAsStream(memory).mapToInt { it.trim().toInt() }.toList().toMutableList()
    }


    fun executeProgram(programInput: ProgramInput? = null): String {

        programInput?.let { memory[1] = programInput.noun }
        programInput?.let { memory[2] = programInput.verb }

        var instructionPointer = 0
        while (true) {
            val instruction: Instruction = getOperation(memory[instructionPointer])

            if (instruction is HaltInstruction) {
                break
            }

            instruction as AddressInstruction

            instruction.perform(memory, instructionPointer)
            instructionPointer = instruction.moveOperationsPointer(instructionPointer)
        }



        return memory.joinToString(separator = ",") { it.toString() }
    }

    private fun getOperation(operationCode: Int): Instruction {
        return when (operationCode) {
            PLUS_OPERATION_CODE -> {
                Plus()
            }
            MULTIPLY_OPERATION_CODE -> {
                Multiply()
            }
            HALT_OPERATION_CODE -> {
                HaltInstruction()
            }
            else -> {
                throw RuntimeException("operationCode: $operationCode is not a valid operationCode.")
            }
        }
    }

    fun findInputsFromOutput(programOutput: Int): ProgramInput {

        for (noun in 0..99) {
            for (verb in 0..99) {
                val programInput = ProgramInput(noun, verb)
                storeProgramInMemory(this.program)
                executeProgram(programInput)
                if (memory[0] == programOutput) {
                    return programInput
                }
            }
        }

        throw Exception("Unable to find noun and verb which causes a program output of $programOutput")
    }


}

data class ProgramInput(val noun: Int, val verb: Int)

class HaltInstruction: Instruction {

}

class Plus: AddressInstruction
{
    override fun perform(memory: MutableList<Int>, instructionPointer: Int) {
        val address1Value = memory[memory[instructionPointer + 1]]
        val address2Value = memory[memory[instructionPointer + 2]]
        val storageAddress = memory[instructionPointer + 3]
        memory[storageAddress] = address1Value + address2Value
        moveOperationsPointer(instructionPointer)
    }

}

class Multiply: AddressInstruction
{
    override fun perform(memory: MutableList<Int>, instructionPointer: Int) {
        val address1Value = memory[memory[instructionPointer + 1]]
        val address2Value = memory[memory[instructionPointer + 2]]
        val storageAddress = memory[instructionPointer + 3]
        memory[storageAddress] = address1Value * address2Value
    }
}

interface AddressInstruction: Instruction {
    fun perform(memory: MutableList<Int>, instructionPointer:Int)
    fun moveOperationsPointer(instructionPointer: Int): Int {
        return instructionPointer + 4
    }
}

interface Instruction {
}
