package Intcode

const val PLUS_OPERATION_CODE = "01"
const val ONE_DIGIT_PLUS_OPERATION_CODE = "1"
const val MULTIPLY_OPERATION_CODE = "02"
const val ONE_DIGIT_MULTIPLY_OPERATION_CODE = "2"
const val STORE_OPERATION_CODE = "03"
const val ONE_DIGIT_STORE_OPERATION_CODE = "3"
const val OUTPUT_OPERATION_CODE = "04"
const val ONE_DIGIT_OUTPUT_OPERATION_CODE = "4"
const val JUMP_IF_TRUE = "05"
const val ONE_DIGIT_JUMP_IF_TRUE = "5"
const val JUMP_IF_FALSE = "06"
const val ONE_DIGIT_JUMP_IF_FALSE = "6"
const val LESS_THAN_OPERATION_CODE = "07"
const val ONE_DIGIT_LESS_THAN_OPERATION_CODE = "7"
const val EQUALS_OPERATION_CODE = "08"
const val ONE_DIGIT_EQUALS_OPERATION_CODE = "8"
const val HALT_OPERATION_CODE = "99"

data class ProgramInput(val noun: Int, val verb: Int)
data class ProgramOutput(var value: Int)
class HaltInstruction: Instruction {

}

class JumpIfFalse(operationCode: String) : BaseInstruction(operationCode), AddressInstruction
{
    override var numberOfParameters: Int = 3

    override fun perform(memory: MutableList<Int>, instructionPointer: InstructionPointer) {
        val firstParameterValue =  if (getParameterMode(0) == '1') memory[instructionPointer.position + 1] else memory[memory[instructionPointer.position + 1]]
        val secondParameterValue = if (getParameterMode(1) == '1') memory[instructionPointer.position + 2] else memory[memory[instructionPointer.position + 2]]

        if (firstParameterValue == 0) {
            instructionPointer.position = secondParameterValue
        } else {
            instructionPointer.position += numberOfParameters
        }
    }

    fun moveOperationsPointer(instructionPointer: Int): Int {
        return instructionPointer + numberOfParameters
    }

}

class JumpIfTrue(operationCode: String) : BaseInstruction(operationCode), AddressInstruction
{
    override var numberOfParameters: Int = 3

    override fun perform(memory: MutableList<Int>, instructionPointer: InstructionPointer) {
        val firstParameterValue =  if (getParameterMode(0) == '1') memory[instructionPointer.position + 1] else memory[memory[instructionPointer.position + 1]]
        val secondParameterValue = if (getParameterMode(1) == '1') memory[instructionPointer.position + 2] else memory[memory[instructionPointer.position + 2]]

        if (firstParameterValue != 0) {
            instructionPointer.position = secondParameterValue
        } else {
            instructionPointer.position += numberOfParameters
        }
    }

    fun moveOperationsPointer(instructionPointer: Int): Int {
        return instructionPointer + numberOfParameters
    }

}

class Plus(operationCode: String) : BaseInstruction(operationCode), AddressInstruction
{
    override val numberOfParameters: Int = 4

    override fun perform(memory: MutableList<Int>, instructionPointer: InstructionPointer) {


        val firstParameterValue =  if (getParameterMode(0) == '1') memory[instructionPointer.position + 1] else memory[memory[instructionPointer.position + 1]]
        val secondParameterValue = if (getParameterMode(1) == '1') memory[instructionPointer.position + 2] else memory[memory[instructionPointer.position + 2]]
        val storageAddress = memory[instructionPointer.position + 3]
        memory[storageAddress] = firstParameterValue + secondParameterValue

        instructionPointer.position += numberOfParameters
    }

}

class Multiply(operationCode: String) : BaseInstruction(operationCode), AddressInstruction
{
    override val numberOfParameters: Int = 4

    override fun perform(memory: MutableList<Int>, instructionPointer: InstructionPointer) {
        val firstParameterValue =  if (getParameterMode(0) == '1') memory[instructionPointer.position + 1] else memory[memory[instructionPointer.position + 1]]
        val secondParameterValue = if (getParameterMode(1) == '1') memory[instructionPointer.position + 2] else memory[memory[instructionPointer.position + 2]]
        val storageAddress = memory[instructionPointer.position + 3]
        memory[storageAddress] = firstParameterValue * secondParameterValue

        instructionPointer.position += numberOfParameters
    }
}

class LessThan(operationCode: String): BaseInstruction(operationCode), AddressInstruction
{
    override val numberOfParameters: Int = 4
    override fun perform(memory: MutableList<Int>, instructionPointer: InstructionPointer) {
        val firstParameterValue =  if (getParameterMode(0) == '1') memory[instructionPointer.position + 1] else memory[memory[instructionPointer.position + 1]]
        val secondParameterValue = if (getParameterMode(1) == '1') memory[instructionPointer.position + 2] else memory[memory[instructionPointer.position + 2]]
        val storageAddress = memory[instructionPointer.position + 3]
        memory[storageAddress] = if (firstParameterValue < secondParameterValue) 1 else 0

        instructionPointer.position += numberOfParameters
    }


}

class Equals(operationCode: String): BaseInstruction(operationCode), AddressInstruction
{
    override val numberOfParameters: Int = 4
    override fun perform(memory: MutableList<Int>, instructionPointer: InstructionPointer) {
        val firstParameterValue =  if (getParameterMode(0) == '1') memory[instructionPointer.position + 1] else memory[memory[instructionPointer.position + 1]]
        val secondParameterValue = if (getParameterMode(1) == '1') memory[instructionPointer.position + 2] else memory[memory[instructionPointer.position + 2]]
        val storageAddress = memory[instructionPointer.position + 3]
        memory[storageAddress] = if (firstParameterValue == secondParameterValue) 1 else 0

        instructionPointer.position += numberOfParameters
    }


}

class Store(operationCode: String, private val input: Int) : BaseInstruction(operationCode),AddressInstruction {

    override val numberOfParameters: Int = 2


    override fun perform(memory: MutableList<Int>, instructionPointer: InstructionPointer) {
        val storageAddress = memory[instructionPointer.position + 1]
        memory[storageAddress] = input

        instructionPointer.position += numberOfParameters
    }
}


class Output(operationCode: String, private val output: ProgramOutput) : BaseInstruction(operationCode), AddressInstruction {

    override val numberOfParameters: Int = 2


    override fun perform(memory: MutableList<Int>, instructionPointer: InstructionPointer) {
        val firstParameterPosition = instructionPointer.position + 1
        val outputAddress = memory[firstParameterPosition]
        if (getParameterMode(0) == '1') {
            output.value = memory[firstParameterPosition]
        } else {
            output.value = memory[outputAddress]
        }

        instructionPointer.position += numberOfParameters

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

    fun perform(memory: MutableList<Int>, instructionPointer: InstructionPointer)
}

interface Instruction {
}