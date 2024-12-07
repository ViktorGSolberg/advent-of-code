import kotlin.system.measureTimeMillis

fun <T> List<T>.toPair(): Pair<T, T> {
    if (this.size != 2) {
        throw IllegalArgumentException("List is not of length 2!")
    }
    return Pair(this[0], this[1])
}

fun <T> List<List<T>>.getColumns(): List<List<T>> = List(this.size) {
    this.getColumn(it)
}

fun <T> List<List<T>>.getColumn(column: Int): List<T> = List(this.size) {
    this[it][column]
}

//fun <T> List<List<T>>.getDiagonals(column: Int): List<T> {
//    val matrixString = this.joinToString("") { list -> list.joinToString("") }
//    val listLength = this.first().size
//    val numberOfLists = this.size
//
//    var diagonals = emptyList<String>()
//
//
//
//    println(matrixString)
//    return emptyList()
//}

fun Array<String>.getDiagonals(): List<String> = getMatrixDiagonal(this)

fun getMatrixDiagonal(grid: Array<String>): List<String> {
    val builder = StringBuilder()
    for (s in grid) {
        builder.append(s)
    }
    val matrixString = builder.toString()

    val wordLength = grid[0].length
    val numberOfWords = grid.size
    val list = mutableListOf<String>()

    if (wordLength > 0) {
        val indexes = IntArray(numberOfWords)


        indexes[0] = matrixString.length - wordLength
        for (i in 1 until numberOfWords) {
            indexes[i] = indexes[i - 1] - wordLength
        }

        val wordCount = numberOfWords + wordLength - 1

        for (i in 0 until wordCount) {
            builder.clear()
            for (j in 0..i) {
                if (j < numberOfWords && indexes[j] < wordLength * (wordCount - i)) {
                    val c = matrixString[indexes[j]]
                    builder.append(c)
                    indexes[j]++
                }
            }
            val s = builder.reverse().toString()
            list.add(s)
        }
    }

    return list.toList()
}

val solutions2024 = mapOf(
    1 to Pair(1579939, 20351745),
    2 to Pair(686, 717),
    3 to Pair(192767529, 104083373),
    4 to Pair(2483, 1925),
    5 to Pair(5991, 5479),
)

val solutionsByYear = mapOf(
    2024 to solutions2024
)

fun timedExecution(taskName: String, task: () -> Any): Any {
    var res: Any
    val time = measureTimeMillis {
        res = task()
    }.toDouble()
    println("$taskName - Result: $res, took ${time / 1000} s ($time ms)")
    return res
}

