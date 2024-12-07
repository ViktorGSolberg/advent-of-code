package year2024

import Task
import toPair
import java.io.File

class Task3(private val dataInputPath: String) : Task<List<Pair<Int, Int>>> {
    override fun parseInput(isPart2: Boolean): List<Pair<Int, Int>> {
        val input = File(dataInputPath).readLines().joinToString()
        val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)|don't\\(\\)|do\\(\\)")
        val matches = regex.findAll(input)

        var isEnabled = true

        return matches.mapNotNull { match ->
            val matchingResults = match.groupValues

            if (matchingResults.first() == "do()") {
                isEnabled = true
                return@mapNotNull null
            }
            if (matchingResults.first() == "don't()") {
                isEnabled = false
                return@mapNotNull null
            }
            if (!isEnabled && isPart2) {
                return@mapNotNull null
            }

            match.groupValues.takeLast(2).map { value ->
                value.toInt()
            }.toPair()
        }.toList()
    }

    override fun doPart1(input: List<Pair<Int, Int>>): Int =
        input.sumOf { pair -> pair.first * pair.second }

    override fun doPart2(input: List<Pair<Int, Int>>): Int = doPart1(input)
}