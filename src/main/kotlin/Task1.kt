import java.io.File
import kotlin.math.abs

class Task1(private val dataInputPath: String) : Task<Pair<List<Int>, List<Int>>> {

    override fun parseInput(): Pair<List<Int>, List<Int>> {
        val numbers =
            File(dataInputPath).readLines()
                .flatMap { line ->
                    line.split("   ")
                        .map { number -> number.toInt() }
                }
        val leftNumbers = numbers.slice(numbers.indices step 2)
        val rightNumbers = numbers.slice(1..<numbers.size step 2)

        return Pair(leftNumbers, rightNumbers)
    }

    override fun doPart1(input: Pair<List<Int>, List<Int>>): Int {
        val (leftNumbers, rightNumbers) = input
        val pairedNumbers = leftNumbers.sorted().zip(rightNumbers.sorted())
        return pairedNumbers.sumOf { abs(it.first - it.second) }
    }

    override fun doPart2(input: Pair<List<Int>, List<Int>>): Int {
        val (leftNumbers, rightNumbers) = input
        return leftNumbers.sumOf { leftNumber ->
            leftNumber * rightNumbers.count { rightNumber -> leftNumber == rightNumber }
        }
    }

    override fun doTask() {
        val parsedInput = parseInput()

        val solutionPart1 = doPart1(parsedInput)
        val solutionPart2 = doPart2(parsedInput)

        println("Solution for part 1")
        println(solutionPart1)
        println("=====")
        println("Solution for part 2")
        println(solutionPart2)
        println("=====")
    }
}