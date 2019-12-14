import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import java.io.File

class Day02Spec : FreeSpec() {
    init {
        "single chunk add operation" {
            Day02("1,0,0,0,99").executeProgram() shouldBe "2,0,0,0,99"
        }

        "single chunk multiply operation" {
            Day02("2,3,0,3,99").executeProgram() shouldBe "2,3,0,6,99"
        }

        "multiple chunk multiply operation with storage outside first chunk" {
            Day02("2,4,4,5,99,0").executeProgram() shouldBe "2,4,4,5,99,9801"
        }

        "multiple chunk add operation where initial halt opcode changes" {
            Day02("1,1,1,4,99,5,6,0,99").executeProgram() shouldBe "30,1,1,4,2,5,6,0,99"
        }

        "file chunk" {
            val output = Day02(File("src/test/resources/day02.txt")).executeProgram()
            output.split(",")[0] shouldBe "3101878"
        }
    }
}
