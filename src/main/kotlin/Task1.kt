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
        val left = numbers.slice(numbers.indices step 2)
        val right = numbers.slice(1..<numbers.size step 2)

        return Pair(left, right)
    }

    override fun doPart1(input: Pair<List<Int>, List<Int>>): Int {
        val (left, right) = input
        val pair = left.sorted().zip(right.sorted())
        return pair.sumOf { abs(it.first - it.second) }
    }

    override fun doPart2(input: Pair<List<Int>, List<Int>>): Int {
        val (left, right) = input
        return left.sumOf { leftNumber ->
            leftNumber * right.count { rightNumber -> leftNumber == rightNumber }
        }
    }
}