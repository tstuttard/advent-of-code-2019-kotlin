import org.spekframework.spek2.Spek
import java.io.File
import kotlin.test.assertEquals

class Day02Spec : Spek({
    test("single chunk add operation") {
        assertEquals("2,0,0,0,99", Day02("1,0,0,0,99").executeProgram())
    }

    test("single chunk multiply operation") {
        assertEquals("2,3,0,6,99", Day02("2,3,0,3,99").executeProgram())
    }

    test("multiple chunk multiply operation with storage outside first chunk") {
        assertEquals("2,4,4,5,99,9801", Day02("2,4,4,5,99,0").executeProgram())
    }

    test("multiple chunk add operation where initial halt opcode changes") {
        assertEquals("30,1,1,4,2,5,6,0,99", Day02("1,1,1,4,99,5,6,0,99").executeProgram())
    }

    test("file chunk") {
        val output = Day02(File("src/test/resources/day02.txt")).executeProgram(ProgramInput(12, 2))
        assertEquals("3101878", output.split(",")[0])
    }

    test("find inputs that produce output") {
        val expectedProgramInput = ProgramInput(84, 44)
        assertEquals(expectedProgramInput, Day02(File("src/test/resources/day02.txt")).findInputsFromOutput(19690720))
        assertEquals(8444, expectedProgramInput.noun * 100 + expectedProgramInput.verb)

    }
})
