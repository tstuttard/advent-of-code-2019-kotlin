package Intcode

const val PLUS_OPERATION_CODE = "01"
const val ONE_DIGIT_PLUS_OPERATION_CODE = "1"
const val MULTIPLY_OPERATION_CODE = "02"
const val ONE_DIGIT_MULTIPLY_OPERATION_CODE = "2"
const val STORE_OPERATION_CODE = "03"
const val ONE_DIGIT_STORE_OPERATION_CODE = "3"
const val OUTPUT_OPERATION_CODE = "04"
const val ONE_DIGIT_OUTPUT_OPERATION_CODE = "4"
const val LESS_THAN_OPERATION_CODE = "07"
const val ONE_DIGIT_LESS_THAN_OPERATION_CODE = "7"
const val EQUALS_OPERATION_CODE = "08"
const val ONE_DIGIT_EQUALS_OPERATION_CODE = "8"
const val HALT_OPERATION_CODE = "99"

data class ProgramInput(val noun: Int, val verb: Int)
data class ProgramOutput(var value: Int)
class HaltInstruction: Instruction {

}

class Plus(operationCode: String) : BaseInstruction(operationCode), AddressInstruction
{
    override val numberOfParameters: Int = 4

    override fun perform(memory: MutableList<Int>, instructionPointer: Int) {


        val firstParameterValue =  if (getParameterMode(0) == '1') memory[instructionPointer + 1] else memory[memory[instructionPointer + 1]]
        val secondParameterValue = if (getParameterMode(1) == '1') memory[instructionPointer + 2] else memory[memory[instructionPointer + 2]]
        val storageAddress = memory[instructionPointer + 3]
        memory[storageAddress] = firstParameterValue + secondParameterValue
    }

}

class Multiply(operationCode: String) : BaseInstruction(operationCode), AddressInstruction
{
    override val numberOfParameters: Int = 4

    override fun perform(memory: MutableList<Int>, instructionPointer: Int) {
        val firstParameterValue =  if (getParameterMode(0) == '1') memory[instructionPointer + 1] else memory[memory[instructionPointer + 1]]
        val secondParameterValue = if (getParameterMode(1) == '1') memory[instructionPointer + 2] else memory[memory[instructionPointer + 2]]
        val storageAddress = memory[instructionPointer + 3]
        memory[storageAddress] = firstParameterValue * secondParameterValue
    }
}

class LessThan(operationCode: String): BaseInstruction(operationCode), AddressInstruction
{
    override val numberOfParameters: Int = 4
    override fun perform(memory: MutableList<Int>, instructionPointer: Int) {
        val firstParameterValue =  if (getParameterMode(0) == '1') memory[instructionPointer + 1] else memory[memory[instructionPointer + 1]]
        val secondParameterValue = if (getParameterMode(1) == '1') memory[instructionPointer + 2] else memory[memory[instructionPointer + 2]]
        val storageAddress = memory[instructionPointer + 3]
        memory[storageAddress] = if (firstParameterValue < secondParameterValue) 1 else 0
    }


}

class Equals(operationCode: String): BaseInstruction(operationCode), AddressInstruction
{
    override val numberOfParameters: Int = 4
    override fun perform(memory: MutableList<Int>, instructionPointer: Int) {
        val firstParameterValue =  if (getParameterMode(0) == '1') memory[instructionPointer + 1] else memory[memory[instructionPointer + 1]]
        val secondParameterValue = if (getParameterMode(1) == '1') memory[instructionPointer + 2] else memory[memory[instructionPointer + 2]]
        val storageAddress = memory[instructionPointer + 3]
        memory[storageAddress] = if (firstParameterValue == secondParameterValue) 1 else 0
    }


}

class Store(operationCode: String, private val input: Int) : BaseInstruction(operationCode),AddressInstruction {

    override val numberOfParameters: Int = 2


    override fun perform(memory: MutableList<Int>, instructionPointer: Int) {
        val storageAddress = memory[instructionPointer + 1]
        memory[storageAddress] = input
    }
}


class Output(operationCode: String, private val output: ProgramOutput) : BaseInstruction(operationCode), AddressInstruction {

    override val numberOfParameters: Int = 2


    override fun perform(memory: MutableList<Int>, instructionPointer: Int) {
        val firstParameterPosition = instructionPointer + 1
        val outputAddress = memory[firstParameterPosition]
        if (getParameterMode(0) == '1') {
            output.value = memory[firstParameterPosition]
        } else {
            output.value = memory[outputAddress]
        }

    }
}

abstract class BaseInstruction(operationCode: String) {

    private var parameterModes: String = ""

    init {
        this.parameterModes = operationCode.substringBefore(operationCode.takeLast(2)).reversed()
        while (this.parameterModes.length < 3) {
            this.parameterModes += "0"
        }
    }

    fun getParameterMode(addressPosition: Int): Char {

        return parameterModes[addressPosition]
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