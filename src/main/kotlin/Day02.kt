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
    private var operations: MutableList<Int>
    constructor(program: String) {
        operations = convertProgramToOperationsList(program)
    }

    constructor(program: File) {
        operations = convertProgramToOperationsList(program.readText())
    }

    private fun convertProgramToOperationsList(operationsString: String): MutableList<Int> {
        return Pattern.compile(",").splitAsStream(operationsString).mapToInt { it.trim().toInt() }.toList().toMutableList()
    }


    fun executeProgram(): String {

        var operationsPointer = 0
        while (true) {
            val operation: Operation = getOperation(operations[operationsPointer])

            if (operation is HaltOperation) {
                break
            }

            operation as AddressOperation

            operations = operation.perform(operations, operationsPointer)
            operationsPointer = operation.moveOperationsPointer(operationsPointer)
        }



        return operations.joinToString(separator = ",") { it.toString() }
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


}

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
