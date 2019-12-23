import Intcode.IntcodeComputer
import Intcode.ProgramInput
import org.spekframework.spek2.Spek
import java.io.File
import kotlin.test.assertEquals

class IntcodeComputerTest : Spek({
    test("single chunk add operation") {
        assertEquals("2,0,0,0,99", IntcodeComputer("1,0,0,0,99").executeProgram())
    }

    test("single chunk multiply operation") {
        assertEquals("2,3,0,6,99", IntcodeComputer("2,3,0,3,99").executeProgram())
    }

    test("multiple chunk multiply operation with storage outside first chunk") {
        assertEquals("2,4,4,5,99,9801", IntcodeComputer("2,4,4,5,99,0").executeProgram())
    }

    test("multiple chunk add operation where initial halt opcode changes") {
        assertEquals("30,1,1,4,2,5,6,0,99", IntcodeComputer("1,1,1,4,99,5,6,0,99").executeProgram())
    }

    test("file chunk") {
        val output = IntcodeComputer(File("src/test/resources/day02.txt")).executeProgram(
            ProgramInput(
                12,
                2
            )
        )
        assertEquals(output.split(",")[0], "3101878")
    }

    test("find inputs that produce output") {
        val expectedProgramInput = ProgramInput(84, 44)
        assertEquals(expectedProgramInput, IntcodeComputer(File("src/test/resources/day02.txt")).findInputsFromOutput(19690720))
        assertEquals(8444, expectedProgramInput.noun * 100 + expectedProgramInput.verb)

    }

//    test("store with opcode 3") {
//        assertEquals("5,0,99", IntcodeComputer("3,0,99").executeProgram(5))
//    }
//
//
//    test("output with opcode 4") {
//        assertEquals("5", IntcodeComputer("4,3,99,6").executeProgram(5))
//    }
})
