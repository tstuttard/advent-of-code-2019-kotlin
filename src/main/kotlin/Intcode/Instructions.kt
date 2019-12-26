package Intcode

const val PLUS_OPERATION_CODE = 1
const val MULTIPLY_OPERATION_CODE = 2
const val STORE_OPERATION_CODE = 3
const val OUTPUT_OPERATION_CODE = 4
const val HALT_OPERATION_CODE = 99

data class ProgramInput(val noun: Int, val verb: Int)
data class ProgramOutput(var value: Int)
class HaltInstruction: Instruction {

}

class Plus: AddressInstruction
{
    override val numberOfParameters: Int = 4

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
    override val numberOfParameters: Int = 4

    override fun perform(memory: MutableList<Int>, instructionPointer: Int) {
        val address1Value = memory[memory[instructionPointer + 1]]
        val address2Value = memory[memory[instructionPointer + 2]]
        val storageAddress = memory[instructionPointer + 3]
        memory[storageAddress] = address1Value * address2Value
    }
}

class Store(private val input: Int) : AddressInstruction {
    override val numberOfParameters: Int = 2


    override fun perform(memory: MutableList<Int>, instructionPointer: Int) {
        val storageAddress = memory[instructionPointer + 1]
        memory[storageAddress] = input
    }
}


class Output(private val output: ProgramOutput) : AddressInstruction {
    override val numberOfParameters: Int = 2


    override fun perform(memory: MutableList<Int>, instructionPointer: Int) {
        val outputAddress = memory[instructionPointer + 1]
        output.value = memory[outputAddress]
    }
}

interface InputInstruction: Instruction {

}


interface AddressInstruction: Instruction {

    val numberOfParameters: Int

    fun perform(memory: MutableList<Int>, instructionPointer:Int)
    fun moveOperationsPointer(instructionPointer: Int): Int {
        return instructionPointer + numberOfParameters
    }
}

interface Instruction {
}