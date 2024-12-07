package year2024

import Task
import java.io.File

class Task5(private val dataInputPath: String) : Task<List<List<Int>>> {
    override fun parseInput(isPart2: Boolean): List<List<Int>> {


        return listOf(emptyList())
    }

    override fun doPart1(input: List<List<Int>>): Int {
        val inputOrderingRules = File(dataInputPath).readLines()
        val regex = Regex("(\\d+)\\|(\\d+)")
        val orderingRules = mutableMapOf<Int, MutableList<Int>>()

        regex.findAll(inputOrderingRules.joinToString()).map { match ->
            match.groupValues.takeLast(2).map { it.toInt() }
        }.toList().forEach { (left, right) ->
            orderingRules.computeIfAbsent(left) { mutableListOf() }.add(right)
        }

        val inputProtocols = File("src/main/resources/input/input_task5_example_2.txt")
            .readLines().map { it.split(",").map { number -> number.toInt() } }

        val correctProtocols = inputProtocols.filter { isCorrect(it, orderingRules) }

        return correctProtocols.sumOf { it[it.size / 2] }
    }


    override fun doPart2(input: List<List<Int>>): Int {
        val inputOrderingRules = File(dataInputPath).readLines()
        val regex = Regex("(\\d+)\\|(\\d+)")
        val orderingRules = mutableMapOf<Int, MutableList<Int>>()

        regex.findAll(inputOrderingRules.joinToString()).map { match ->
            match.groupValues.takeLast(2).map { it.toInt() }
        }.toList().forEach { (left, right) ->
            orderingRules.computeIfAbsent(left) { mutableListOf() }.add(right)
        }

        val inputProtocols = File("src/main/resources/input/input_task5_2.txt")
            .readLines().map { it.split(",").map { number -> number.toInt() } }

        val falseProtocols = inputProtocols.filterNot { isCorrect(it, orderingRules) }


        val sortedProtocols = falseProtocols.map { sortProtocol(it, orderingRules) }

        println(falseProtocols)
        println(sortedProtocols)

        return sortedProtocols.sumOf { it[it.size / 2] }
    }

    private fun isCorrect(protocol: List<Int>, orderingRules: MutableMap<Int, MutableList<Int>>): Boolean {
        protocol.reversed().forEachIndexed { index, number ->
            val rules = orderingRules[number] ?: mutableListOf()
            val remainingProtocol = protocol.reversed().drop(1 + index)

            if (remainingProtocol.any { it in rules }) {
                return false
            }
        }

        return true
    }

    private fun sortProtocol(protocol: List<Int>, orderingRules: MutableMap<Int, MutableList<Int>>): List<Int> {
        val mutableList = protocol.toMutableList()

        while (!isCorrect(mutableList, orderingRules)) {
            mutableList.reversed().forEachIndexed { index, number ->
                val rules = orderingRules[number] ?: mutableListOf()
                val remainingProtocol = mutableList.reversed().drop(1 + index)

                if (remainingProtocol.any { it in rules }) {
                    mutableList.remove(number)
                    val newIndex = remainingProtocol.maxOf { if (it in rules) remainingProtocol.indexOf(it) else -1 }
                    mutableList.add(newIndex, number)
                }
            }
        }


        return mutableList.reversed()
    }

}