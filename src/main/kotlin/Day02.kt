import java.io.File
import java.lang.RuntimeException
import java.util.regex.Pattern
import kotlin.streams.toList

const val PLUS_OPERATION_CODE = 1
const val MULTIPLY_OPERATION_CODE = 2
const val HALT_OPERATION_CODE = 99

const val OPERATION_POSITION = 0
const val ADDRESS1_POSITION = 1
const val ADDRESS2_POSITION = 2
const val STORAGE_POSITION = 3

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
            val operation: Operation = getOperation(memory[instructionPointer])

            if (operation is HaltOperation) {
                break
            }

            operation as AddressOperation

            memory = operation.perform(memory, instructionPointer)
            instructionPointer = operation.moveOperationsPointer(instructionPointer)
        }



        return memory.joinToString(separator = ",") { it.toString() }
    }

    private fun getOperation(operationCode: Int): Operation {
        return when (operationCode) {
            PLUS_OPERATION_CODE -> {
                Plus()
            }
            MULTIPLY_OPERATION_CODE -> {
                Multiply()
            }
            HALT_OPERATION_CODE -> {
                HaltOperation()
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

class HaltOperation: Operation {

}

class Plus: AddressOperation
{
    override fun perform(operations: MutableList<Int>, operationsPointer: Int): MutableList<Int> {
        val address1Value = operations[operations[operationsPointer + 1]]
        val address2Value = operations[operations[operationsPointer + 2]]
        val storageAddress = operations[operationsPointer + 3]
        operations[storageAddress] = address1Value + address2Value
        return operations
    }

}

class Multiply: AddressOperation
{
    override fun perform(operations: MutableList<Int>, operationsPointer: Int): MutableList<Int> {
        val address1Value = operations[operations[operationsPointer + 1]]
        val address2Value = operations[operations[operationsPointer + 2]]
        val storageAddress = operations[operationsPointer + 3]
        operations[storageAddress] = address1Value * address2Value
        return operations
    }
}

interface AddressOperation: Operation {
    fun perform(operations: MutableList<Int>, operationsPointer:Int): MutableList<Int>
    fun moveOperationsPointer(operationsPointer: Int): Int {
        return operationsPointer + 4
    }
}

interface Operation {
}
