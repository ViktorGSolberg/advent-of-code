package year2024

import LongTask
import java.io.File

// 3749 example correct!
// 7579994664753 correct!
// 11387 example correct!
// 438027111276610 correct!
class Task7(private val dataInputPath: String) : LongTask<List<Pair<Long, List<Long>>>> {
    override fun parseInput(isPart2: Boolean): List<Pair<Long, List<Long>>> =
        File(dataInputPath).readLines().map { line ->
            val numbers = line.split(":").map { numbers ->
                numbers.trim().split(" ")
            }.flatten().map { it.toLong() }
            Pair(numbers.first(), numbers.drop(1))
        }

    override fun doPart1(input: List<Pair<Long, List<Long>>>): Long {
        val results = input.map { canComputeResultOrZero(it.first, it.second, false) }
        return results.sum()
    }


    override fun doPart2(input: List<Pair<Long, List<Long>>>): Long {
        val results = input.map { canComputeResultOrZero(it.first, it.second, true) }
        return results.sum()
    }

    private fun canComputeResultOrZero(answer: Long, numbers: List<Long>, isPart2: Boolean): Long {
        val operators = if(isPart2) Operator.values().toList() else listOf(Operator.ADD, Operator.MULTIPLY)
        val listOfOperators = generateCombinationsWithRepetition(numbers.size, operators)

        listOfOperators.forEach { operatorList ->
            if (compute(numbers, operatorList, answer) == answer) {
                return answer
            }
        }

        return 0L
    }

    private fun generateCombinationsWithRepetition(n: Int, list: List<Operator>): List<List<Operator>> {
        val result = mutableListOf<List<Operator>>()
        val combination = mutableListOf(*Array(n) { list[0] })

        // Generate combinations iteratively
        while (true) {
            result.add(ArrayList(combination)) // Add the current combination

            // Move to the next combination
            var index = n - 1
            while (index >= 0) {
                // If we haven't reached the last element in the list, move to the next one
                if (combination[index] != list.last()) {
                    combination[index] = list[list.indexOf(combination[index]) + 1]
                    break
                }
                // If we have reached the last element, reset this position and move to the previous one
                combination[index] = list[0]
                index--
            }

            // If all positions have been reset, we've exhausted all combinations
            if (index < 0) break
        }

        return result
    }

    private fun compute(numbers: List<Long>, operators: List<Operator>, answer: Long) =
        numbers.foldIndexed(0L) { index, acc, number ->
            if (index == 0){
                acc + number
            } else {
                val computed = compute(acc, number, operators[index-1])
                if (computed > answer) {
                    return 0L
                }
                computed
            }
        }

    private fun compute(first: Long, second: Long, operator: Operator): Long = when (operator) {
        Operator.ADD -> first + second
        Operator.MULTIPLY -> first * second
        Operator.CONCATENATE -> (first.toString() + second.toString()).toLong()
    }

    enum class Operator {
        ADD,
        MULTIPLY,
        CONCATENATE,
    }
}