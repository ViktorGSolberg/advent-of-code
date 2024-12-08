package year2024

import Task
import java.io.File

class Task8(private val dataInputPath: String) : Task<List<List<Char>>> {
    override fun parseInput(isPart2: Boolean): List<List<Char>> =
        File(dataInputPath).readLines().map { it.toList() }

    // 323
    override fun doPart1(input: List<List<Char>>): Int {
        val lineSize = input.size
        val charSize = input.first().size
        val antennaIdToLocations = mapOfAntennaIdToLocations(input)
        val antennaPairs = antennaIdToLocations.values.flatMap { generatePairs(it) }
        val inboundNodes =
            antennaPairs.flatMap { (first, second) -> calculateAntiNodes(first, second, lineSize, charSize) }
        val uniqueInboundNodes = inboundNodes.toSet().sortedBy { it.first }

        return uniqueInboundNodes.size
    }


    override fun doPart2(input: List<List<Char>>): Int {
        val lineSize = input.size
        val charSize = input.first().size
        val antennaIdToLocations = mapOfAntennaIdToLocations(input)
        val antennaPairs = antennaIdToLocations.values.flatMap { generatePairs(it) }
        val inboundNodes =
            antennaPairs.flatMap { (first, second) -> calculateAntiNodesPart2(first, second, lineSize, charSize) }
        val antennasWithMoreThanOneOccurence = antennaIdToLocations.filter { it.value.size > 1  }.flatMap { it.value }
        val uniqueInboundNodes = (inboundNodes + antennasWithMoreThanOneOccurence).toSet().sortedBy { it.first }

        return uniqueInboundNodes.size
    }

    private fun mapOfAntennaIdToLocations(input: List<List<Char>>): MutableMap<Char, MutableList<Pair<Int, Int>>> {
        val map = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, char ->
                if (char.isDigit() || char.isLetter()) {
                    map.computeIfAbsent(char) { mutableListOf() }.add(Pair(lineIndex, charIndex))
                }
            }
        }

        return map
    }

    private fun <T> generatePairs(list: List<T>): List<List<T>> {
        val result = mutableListOf<List<T>>()

        for (i in list.indices) {
            for (j in i + 1 until list.size) {
                result.add(listOf(list[i], list[j]))
            }
        }

        return result
    }

    private fun calculateAntiNodes(
        first: Pair<Int, Int>,
        second: Pair<Int, Int>,
        lineSize: Int,
        charSize: Int
    ): List<Pair<Int, Int>> {
        val firstToSecondLineDistance = second.first - first.first
        val firstToSecondCharDistance = second.second - first.second

        val secondToFirstLineDistance = first.first - second.first
        val secondToFirstCharDistance = first.second - second.second

        val firstAntiNodeLine = second.first + firstToSecondLineDistance
        val firstAntiNodeChar = second.second + firstToSecondCharDistance

        val secondAntiNodeLine = first.first + secondToFirstLineDistance
        val secondAntiNodeChar = first.second + secondToFirstCharDistance

        val firstNodeLegal =
            firstAntiNodeLine > -1 && firstAntiNodeLine < lineSize && firstAntiNodeChar > -1 && firstAntiNodeChar < charSize
        val secondNodeLegal =
            secondAntiNodeLine > -1 && secondAntiNodeLine < lineSize && secondAntiNodeChar > -1 && secondAntiNodeChar < charSize

        val legalAntiNodes = mutableListOf<Pair<Int, Int>>()

        if (firstNodeLegal) {
            legalAntiNodes.add(Pair(firstAntiNodeLine, firstAntiNodeChar))
        }

        if (secondNodeLegal) {
            legalAntiNodes.add(Pair(secondAntiNodeLine, secondAntiNodeChar))
        }

        return legalAntiNodes
    }

    private fun calculateAntiNodesPart2(
        first: Pair<Int, Int>,
        second: Pair<Int, Int>,
        lineSize: Int,
        charSize: Int
    ): List<Pair<Int, Int>> {
        val legalAntiNodes = mutableListOf<Pair<Int, Int>>()
        var currentFirst = first
        var currentSecond = second

        var firstNodeLegal = true
        var secondNodeLegal = true

        val firstToSecondLineDistance = currentSecond.first - currentFirst.first
        val firstToSecondCharDistance = currentSecond.second - currentFirst.second

        val secondToFirstLineDistance = currentFirst.first - currentSecond.first
        val secondToFirstCharDistance = currentFirst.second - currentSecond.second

        while (firstNodeLegal) {
            val firstAntiNodeLine = currentSecond.first + firstToSecondLineDistance
            val firstAntiNodeChar = currentSecond.second + firstToSecondCharDistance

            firstNodeLegal =
                firstAntiNodeLine > -1 && firstAntiNodeLine < lineSize && firstAntiNodeChar > -1 && firstAntiNodeChar < charSize

            if (firstNodeLegal) {
                legalAntiNodes.add(Pair(firstAntiNodeLine, firstAntiNodeChar))
            }

            currentSecond = Pair(firstAntiNodeLine, firstAntiNodeChar)
        }

        while (secondNodeLegal) {
            val secondAntiNodeLine = currentFirst.first + secondToFirstLineDistance
            val secondAntiNodeChar = currentFirst.second + secondToFirstCharDistance

            secondNodeLegal =
                secondAntiNodeLine > -1 && secondAntiNodeLine < lineSize && secondAntiNodeChar > -1 && secondAntiNodeChar < charSize

            if (secondNodeLegal) {
                legalAntiNodes.add(Pair(secondAntiNodeLine, secondAntiNodeChar))
            }
            currentFirst = Pair(secondAntiNodeLine, secondAntiNodeChar)
        }

        return legalAntiNodes
    }
}