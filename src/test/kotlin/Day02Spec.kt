import org.spekframework.spek2.Spek
import java.io.File
import kotlin.test.assertEquals

class Day02Spec : Spek({
    test("single chunk add operation") {
        assertEquals(Day02("1,0,0,0,99").executeProgram(), "2,0,0,0,99")
    }

    test("single chunk multiply operation") {
        assertEquals(Day02("2,3,0,3,99").executeProgram(), "2,3,0,6,99")
    }

    test("multiple chunk multiply operation with storage outside first chunk") {
        assertEquals(Day02("2,4,4,5,99,0").executeProgram(), "2,4,4,5,99,9801")
    }

    test("multiple chunk add operation where initial halt opcode changes") {
        assertEquals(Day02("1,1,1,4,99,5,6,0,99").executeProgram(), "30,1,1,4,2,5,6,0,99")
    }

    test("file chunk") {
        val output = Day02(File("src/test/resources/day02.txt")).executeProgram(ProgramInput(12, 2))
        assertEquals(output.split(",")[0], "3101878")
    }

    test("find inputs that produce output") {
        val expectedProgramInput = ProgramInput(84, 44)
        assertEquals(Day02(File("src/test/resources/day02.txt")).findInputsFromOutput(19690720), expectedProgramInput)
        assertEquals(expectedProgramInput.noun * 100 + expectedProgramInput.verb, 8444)

    }
})
