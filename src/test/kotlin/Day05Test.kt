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

    test("equal to opcode 8") {
        assertEquals(1, IntcodeComputer("3,9,8,9,10,9,4,9,99,-1,8").executeProgram(8))
        assertEquals(0, IntcodeComputer("3,9,8,9,10,9,4,9,99,-1,8").executeProgram(9))
    }

})
