import Intcode.IntcodeComputer
import Intcode.ProgramInput
import org.spekframework.spek2.Spek
import java.io.File
import kotlin.test.assertEquals

class IntcodeComputerTest : Spek({
    test("store with opcode 3") {
        val intcodeComputer = IntcodeComputer("3,0,99")
        intcodeComputer.executeProgram(5)
        assertEquals("5,0,99", intcodeComputer.memoryAsString())
    }
    test("store with opcode 4") {
        val intcodeComputer = IntcodeComputer("4,3,99,20")

        assertEquals(20, intcodeComputer.executeProgram(5))
    }


    test("store and input ") {
        assertEquals(30, IntcodeComputer("3,0,4,0,99").executeProgram(30))
    }

    test("immediate mode for output") {
        assertEquals(10, IntcodeComputer("4,0,104,10,99").executeProgram(30))
    }

    test("immediate mode for plus") {
        val intcodeComputer = IntcodeComputer("1101,5,10,5,99,0")
        intcodeComputer.executeProgram(10)
        assertEquals("1101,5,10,5,99,15", intcodeComputer.memoryAsString())
    }

    test("immediate mode for multiply") {
        val intcodeComputer = IntcodeComputer("1102,5,10,5,99,0")
        intcodeComputer.executeProgram(10)
        assertEquals("1102,5,10,5,99,50", intcodeComputer.memoryAsString())
    }

    test("file input with immediate modes and store and output instructions") {
        assertEquals(7566643, IntcodeComputer(File("src/test/resources/day05.txt")).executeProgram(1))
    }

    test("jump if true opcode 5") {
        assertEquals(1, IntcodeComputer("3,12,5,12,15,1,13,14,13,4,13,99,-1,0,1,9").executeProgram(0))
        assertEquals(0, IntcodeComputer("3,12,5,12,15,1,13,14,13,4,13,99,-1,0,1,9").executeProgram(8))
    }

    test("immediate mode jump if true") {
        assertEquals(0, IntcodeComputer("3,3,1105,-1,9,1101,0,0,12,4,12,99,1").executeProgram(0))
        assertEquals(1, IntcodeComputer("3,3,1105,-1,9,1101,0,0,12,4,12,99,1").executeProgram(8))
    }

    test("jump if false opcode 6") {
        assertEquals(0, IntcodeComputer("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9").executeProgram(0))
        assertEquals(1, IntcodeComputer("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9").executeProgram(8))
    }


    test("immediate mode jump if false") {
        assertEquals(1, IntcodeComputer("3,3,1106,-1,9,1101,0,0,12,4,12,99,1").executeProgram(0))
        assertEquals(0, IntcodeComputer("3,3,1106,-1,9,1101,0,0,12,4,12,99,1").executeProgram(8))
    }

    test("less than opcode 7") {
        assertEquals(1, IntcodeComputer("3,9,7,9,10,9,4,9,99,-1,8").executeProgram(7))
        assertEquals(0, IntcodeComputer("3,9,7,9,10,9,4,9,99,-1,8").executeProgram(8))
    }

    test("equal to opcode 8") {
        assertEquals(1, IntcodeComputer("3,9,8,9,10,9,4,9,99,-1,8").executeProgram(8))
        assertEquals(0, IntcodeComputer("3,9,8,9,10,9,4,9,99,-1,8").executeProgram(9))
    }


    test("immediate mode less than") {
        assertEquals(1, IntcodeComputer("3,3,1107,-1,8,3,4,3,99").executeProgram(7))
        assertEquals(0, IntcodeComputer("3,3,1107,-1,8,3,4,3,99").executeProgram(8))
    }

    test("immediate mode equal to") {
        assertEquals(1, IntcodeComputer("3,3,1108,-1,8,3,4,3,99").executeProgram(8))
        assertEquals(0, IntcodeComputer("3,3,1108,-1,8,3,4,3,99").executeProgram(9))
    }


    test("large example") {
        val program =
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"

        assertEquals(999, IntcodeComputer(program).executeProgram(7))
        assertEquals(1000, IntcodeComputer(program).executeProgram(8))
        assertEquals(1001, IntcodeComputer(program).executeProgram(9))
    }

    test("diagnostic test suite for system id 5") {
        assertEquals(9265694, IntcodeComputer(File("src/test/resources/day05.txt")).executeProgram(5))
    }
})
