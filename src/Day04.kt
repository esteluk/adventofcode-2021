import kotlin.system.measureTimeMillis

fun main() {

    fun boards(input: List<String>): List<List<MutableList<CharSequence>>> {
        return input.drop(2).chunked(6)
            .map { it.filter { row -> !row.isEmpty() } }
            .map { board -> board.map {
                it.chunked(3) { item -> item.trim() }.toMutableList()
            }}
            .map { board ->
                val extraRows = board[0].indices.map { index ->
                    board.map { it[index] }.toMutableList()
                }
                board + extraRows
            }
    }

    fun mark(call: String, board: List<MutableList<CharSequence>>) {
        for (row in board) {
            row.removeAll { it == call }
        }
    }

    fun isBoardComplete(board: List<List<CharSequence>>): Boolean {
        return board.fold(false) { acc, row -> row.isEmpty() || acc }
    }

    fun part1(input: List<String>): Int {
        val calls = input.first().split(",")

        val boards = boards(input)

        println(boards.joinToString())

        var callIndex = -1
        while(boards.fold(true) { acc, board -> !isBoardComplete(board) && acc} ) {
            callIndex += 1
            val call = calls[callIndex]
//            println("Calling number $call")
            boards.forEach {
                mark(call, it)
            }
        }

        val board = boards.indexOfFirst { isBoardComplete(it) }
//        println(boards[board])
//        println(board)

        val score = boards[board].dropLast(5).fold(0) { acc, row -> acc + row.fold(0) { rowAcc, item -> rowAcc + item.toString().toInt() }}
//        println(score)
        return score*calls[callIndex].toInt()
    }

    fun part2(input: List<String>): Int {
        val calls = input.first().split(",")
        val boards = boards(input)

        var callIndex = -1
        var inPlayBoards = boards
        var lastBoard = boards.first()
        var lastCall = ""

        do {
            callIndex += 1
            lastCall = calls[callIndex]
            inPlayBoards.forEach {
                mark(lastCall, it)
                lastBoard = it
            }
            inPlayBoards = boards.filter { !isBoardComplete(it) }
        } while(inPlayBoards.isNotEmpty())

        println(lastBoard.joinToString())
        val score = lastBoard.dropLast(5).fold(0) { acc, row -> acc + row.fold(0) { rowAcc, item -> rowAcc + item.toString().toInt() }}
        return score*lastCall.toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    val part1Measure = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 executed in $part1Measure ms")

    val part2Measure = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 executed in $part2Measure ms")
}
