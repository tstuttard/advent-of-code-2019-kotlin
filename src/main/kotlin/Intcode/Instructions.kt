package Intcode

const val PLUS_OPERATION_CODE = 1
const val MULTIPLY_OPERATION_CODE = 2
const val HALT_OPERATION_CODE = 99

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