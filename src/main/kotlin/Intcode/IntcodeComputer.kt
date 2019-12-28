package Intcode

import java.io.File
import java.lang.RuntimeException
import java.util.regex.Pattern
import kotlin.streams.toList

class IntcodeComputer {
    private var input: Int = 0
    private val programOutput: ProgramOutput = ProgramOutput(0)
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

    fun executeProgram(programInput: Int): Int {
        input = programInput
        execute()



        return programOutput.value
    }


    fun executeProgram(programInput: ProgramInput? = null): String {

        programInput?.let { memory[1] = programInput.noun }
        programInput?.let { memory[2] = programInput.verb }

        execute()



        return memoryAsString()
    }

    fun memoryAsString() = memory.joinToString(separator = ",") { it.toString() }

    private fun execute() {
        var instructionPointer = 0
        while (true) {
            val instruction: Instruction = getOperation(memory[instructionPointer].toString())

            if (instruction is HaltInstruction) {
                break
            }

            instruction as AddressInstruction

            instruction.perform(memory, instructionPointer)
            instructionPointer = instruction.moveOperationsPointer(instructionPointer)
        }
    }

    private fun getOperation(operationCode: String): Instruction {
        return when (operationCode.takeLast(2)) {
            ONE_DIGIT_PLUS_OPERATION_CODE, PLUS_OPERATION_CODE -> {
                Plus(operationCode)
            }
            ONE_DIGIT_MULTIPLY_OPERATION_CODE, MULTIPLY_OPERATION_CODE -> {
                Multiply(operationCode)
            }
            ONE_DIGIT_STORE_OPERATION_CODE, STORE_OPERATION_CODE -> {
                Store(operationCode, input)
            }
            ONE_DIGIT_OUTPUT_OPERATION_CODE, OUTPUT_OPERATION_CODE -> {
                Output(operationCode, programOutput)
            }
            ONE_DIGIT_EQUALS_OPERATION_CODE, EQUALS_OPERATION_CODE -> {
                Equals(operationCode)
            }
            HALT_OPERATION_CODE -> {
                HaltInstruction()
            }
            else -> {
                throw RuntimeException("operationCode: ${operationCode.takeLast(2)} is not a valid operationCode.")
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
