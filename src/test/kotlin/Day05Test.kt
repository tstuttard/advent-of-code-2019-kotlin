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


    test("output with opcode 4") {
        assertEquals(30, IntcodeComputer("3,0,4,0,99").executeProgram(30))
    }
})
