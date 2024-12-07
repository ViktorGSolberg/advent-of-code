package year2024

import Task
import java.io.File

class Task6(private val dataInputPath: String) : Task<List<List<Char>>> {
    override fun parseInput(isPart2: Boolean): List<List<Char>> {
        val input = File(dataInputPath).readLines().map { it.toList() }

        return input
    }

    override fun doPart1(input: List<List<Char>>): Int {

        val startingPosition = getStartingPosition(input) ?: throw Exception("Fant ikke startposisjon")
        val uniqueVisitedPositions = getVisitedPositions(input, startingPosition, false)

        return uniqueVisitedPositions.size
    }


    override fun doPart2(input: List<List<Char>>): Int {
        val startingPosition = getStartingPosition(input) ?: throw Exception("Fant ikke startposisjon")
        // 1705 too low
        // 1973 too high
        // 1810 too low


        return bruteForcePart2(input, startingPosition)
    }

    private fun getStartingPosition(input: List<List<Char>>): Pair<Int, Int>? {
        input.forEachIndexed { indexLine, line ->
            line.forEachIndexed { indexString, string ->
                if (string == '^') {
                    return Pair(indexLine, indexString)
                }
            }
        }
        return null
    }

    private fun getBlockadePositions(matrix: List<List<Char>>, startingPosition: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val uniqueBlockadePositions = mutableSetOf<Pair<Int, Int>>()
        var currentLine = startingPosition.first
        var currentChar = startingPosition.second
        var direction = "up"
        var count = 0

        while (currentLine != 0 && currentLine != matrix.size - 1 && currentChar != 0 && currentChar != matrix.first().size - 1) {
            count++
            println(count)

            when (direction) {
                "up" -> {
                    if (matrix[currentLine - 1][currentChar] == '#') {
                        direction = "right"
                    } else {
                        if (canPlaceBlockade(matrix, BlockadePosition(currentLine - 1, currentChar, "up"), Pair(currentLine, currentChar), "right")) {
                            uniqueBlockadePositions.add(Pair(currentLine - 1, currentChar))
                        }
                        currentLine -= 1
                    }
                }
                "right" -> {
                    if (matrix[currentLine][currentChar + 1] == '#') {
                        direction = "down"
                    } else {
                        if (canPlaceBlockade(matrix,BlockadePosition(currentLine, currentChar+1, "right"), Pair(currentLine, currentChar), "down")) {
                            uniqueBlockadePositions.add(Pair(currentLine, currentChar+1))
                        }
                        currentChar += 1
                    }
                }
                "down" -> {
                    if (matrix[currentLine + 1][currentChar] == '#') {
                        direction = "left"
                    } else {
                        if (canPlaceBlockade(matrix,BlockadePosition(currentLine+1, currentChar, "down"), Pair(currentLine, currentChar), "left")) {
                            uniqueBlockadePositions.add(Pair(currentLine+1, currentChar))
                        }
                        currentLine += 1
                    }
                }
                "left" -> {
                    if (matrix[currentLine][currentChar - 1] == '#') {
                        direction = "up"
                    } else {
                        if (canPlaceBlockade(matrix, BlockadePosition(currentLine, currentChar-1, "left"), Pair(currentLine, currentChar), "up")) {
                            uniqueBlockadePositions.add(Pair(currentLine, currentChar-1))
                        }
                        currentChar -= 1
                    }
                }
            }
        }

        uniqueBlockadePositions.remove(Pair(startingPosition.first, startingPosition.second))

        return uniqueBlockadePositions
    }

    private fun canPlaceBlockade(matrix: List<List<Char>>, potentialBlockadePosition: BlockadePosition, startingPosition: Pair<Int, Int>, startingDirection: String): Boolean {
        val hitBlockadePositions = mutableSetOf(potentialBlockadePosition)
        var currentLine = startingPosition.first
        var currentChar = startingPosition.second
        var direction = startingDirection

        while (currentLine != 0 && currentLine != matrix.size - 1 && currentChar != 0 && currentChar != matrix.first().size - 1) {


            when (direction) {
                "up" -> {
                    if (matrix[currentLine - 1][currentChar] == '#' || (potentialBlockadePosition.first == currentLine-1 && potentialBlockadePosition.second == currentChar)) {
                        if (hitBlockadePositions.any { it.first == currentLine-1 && it.second == currentChar && it.direction == "up" }) {
                            return true
                        }

                        hitBlockadePositions.add(BlockadePosition(currentLine - 1, currentChar, "up"))
                        direction = "right"
//                        currentChar += 1
                    } else {
//                        if (potentialBlockadePosition.first == currentLine-1 && potentialBlockadePosition.second == currentChar && potentialBlockadePosition.direction == "up") {
//                            return true
//                        }
                        currentLine -= 1
                    }
                }
                "right" -> {
                    if (matrix[currentLine][currentChar + 1] == '#' || (potentialBlockadePosition.first == currentLine && potentialBlockadePosition.second == currentChar+1)) {
                        if (hitBlockadePositions.any { it.first == currentLine && it.second == currentChar+1 && it.direction == "right" }) {
                            return true
                        }

                        hitBlockadePositions.add(BlockadePosition(currentLine, currentChar+1, "right"))
                        direction = "down"
//                        currentLine += 1
                    } else {
//                        if (potentialBlockadePosition.first == currentLine && potentialBlockadePosition.second == currentChar+1 && potentialBlockadePosition.direction == "right") {
//                            return true
//                        }
                        currentChar += 1
                    }
                }
                "down" -> {
                    if (matrix[currentLine + 1][currentChar] == '#' || (potentialBlockadePosition.first == currentLine+1 && potentialBlockadePosition.second == currentChar)) {
                        if (hitBlockadePositions.any { it.first == currentLine+1 && it.second == currentChar && it.direction == "down" }) {
                            return true
                        }

                        hitBlockadePositions.add(BlockadePosition(currentLine + 1, currentChar, "down"))
                        direction = "left"
//                        currentChar -= 1
                    } else {
//                        if (potentialBlockadePosition.first == currentLine+1 && potentialBlockadePosition.second == currentChar && potentialBlockadePosition.direction == "down") {
//                            return true
//                        }
                        currentLine += 1
                    }
                }
                "left" -> {
                    if (matrix[currentLine][currentChar - 1] == '#' || (potentialBlockadePosition.first == currentLine && potentialBlockadePosition.second == currentChar-1)) {
                        if (hitBlockadePositions.any { it.first == currentLine && it.second == currentChar-1 && it.direction == "left" }) {
                            return true
                        }

                        hitBlockadePositions.add(BlockadePosition(currentLine, currentChar - 1, "left"))
                        direction = "up"
//                        currentLine -= 1
                    } else {
//                        if (potentialBlockadePosition.first == currentLine && potentialBlockadePosition.second == currentChar-1 && potentialBlockadePosition.direction == "left") {
//                            return true
//                        }
                        currentChar -= 1
                    }
                }
            }
        }

        return false
    }

    private fun getVisitedPositions(matrix: List<List<Char>>, startingPosition: Pair<Int, Int>, isPart2: Boolean): Set<Pair<Int, Int>> {
        val uniqueVisitedPositions = mutableSetOf<Pair<Int, Int>>()
        var currentLine = startingPosition.first
        var currentChar = startingPosition.second
        var direction = "up"
        var count = 0

        while (currentLine != 0 && currentLine != matrix.size - 1 && currentChar != 0 && currentChar != matrix.first().size - 1) {
            if (isPart2) {
                count++
                if (count > 10000) {
                    return emptySet()
                }
            }

            uniqueVisitedPositions.add(Pair(currentLine, currentChar))

            when (direction) {
                "up" -> {
                    if (matrix[currentLine - 1][currentChar] == '#') {
                        direction = "right"
                    } else {
                        currentLine -= 1
                    }
                }
                "right" -> {
                    if (matrix[currentLine][currentChar + 1] == '#') {
                        direction = "down"
                    } else {
                        currentChar += 1
                    }
                }
                "down" -> {
                    if (matrix[currentLine + 1][currentChar] == '#') {
                        direction = "left"
                    } else {
                        currentLine += 1
                    }
                }
                "left" -> {
                    if (matrix[currentLine][currentChar - 1] == '#') {
                        direction = "up"
                    } else {
                        currentChar -= 1
                    }
                }
            }
        }

        uniqueVisitedPositions.add(Pair(currentLine, currentChar))

        return uniqueVisitedPositions
    }

    data class BlockadePosition(val first: Int, val second: Int, val direction: String)

    fun bruteForcePart2(matrix: List<List<Char>>, startingPosition: Pair<Int, Int>): Int {
        var count = 0

        matrix.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, char ->
                val tempMatrix = matrix.map { it.toMutableList() }.toMutableList()
                if (lineIndex != startingPosition.first || charIndex != startingPosition.second ) {
                    if (tempMatrix[lineIndex][charIndex] != '#') {
                        tempMatrix[lineIndex][charIndex] = '#'
                        val visits = getVisitedPositions(tempMatrix, startingPosition, true)
                        if (visits.isEmpty()) {
                            count++
                        }
                    }
                }
            }
        }
        return count

    }

}