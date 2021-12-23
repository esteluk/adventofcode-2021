import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.map { it.toCharArray().map { char -> char.digitToInt() }.toMutableList() }
        val columns = grid[0].lastIndex
        var flashCount = 0

        for (i in 1..100) {
            // Increment all values by one
            for (row in grid.indices) {
                for (column in 0..columns) {
                    grid[row][column] += 1
                }
            }

            val flashes = mutableListOf<Pair<Int, Int>>()

            fun checkForFlash(row: Int, column: Int) {
                if (grid[row][column] > 9 && !flashes.contains(Pair(row, column))) {
                    flashes.add(Pair(row, column))

                    for (surroundingRow in max(0, row-1)..min(row+1, grid.lastIndex)) {
                        for (surroundingColumn in max(0, column-1)..min(column+1, grid[0].lastIndex)) {
                            grid[surroundingRow][surroundingColumn] += 1
                            checkForFlash(surroundingRow, surroundingColumn)
                        }
                    }
                }
            }

            // Flashes
            for (row in grid.indices) {
                for (column in 0..columns) {
                    checkForFlash(row, column)
                }
            }

            // Reset
            for (row in grid.indices) {
                for (column in 0..columns) {
                    if (grid[row][column] > 9) {
                        grid[row][column] = 0
                    }
                }
            }

            flashCount += flashes.size
//            println("After $i steps there have been $flashCount flashes")
//            if (i % 10 == 0) {
//                for (row in grid) {
//                    println(row.joinToString(""))
//                }
//            }
        }
        return flashCount
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { it.toCharArray().map { char -> char.digitToInt() }.toMutableList() }
        val columns = grid[0].lastIndex

        for (i in 1..100000) {
            // Increment all values by one
            for (row in grid.indices) {
                for (column in 0..columns) {
                    grid[row][column] += 1
                }
            }

            val flashes = mutableListOf<Pair<Int, Int>>()

            fun checkForFlash(row: Int, column: Int) {
                if (grid[row][column] > 9 && !flashes.contains(Pair(row, column))) {
                    flashes.add(Pair(row, column))

                    for (surroundingRow in max(0, row-1)..min(row+1, grid.lastIndex)) {
                        for (surroundingColumn in max(0, column-1)..min(column+1, grid[0].lastIndex)) {
                            grid[surroundingRow][surroundingColumn] += 1
                            checkForFlash(surroundingRow, surroundingColumn)
                        }
                    }
                }
            }

            // Flashes
            for (row in grid.indices) {
                for (column in 0..columns) {
                    checkForFlash(row, column)
                }
            }

            // Reset
            for (row in grid.indices) {
                for (column in 0..columns) {
                    if (grid[row][column] > 9) {
                        grid[row][column] = 0
                    }
                }
            }

            if (flashes.size == grid.size * grid[0].size) {
                return i
            }
//            println("After $i steps there have been $flashCount flashes")
//            if (i % 10 == 0) {
//                for (row in grid) {
//                    println(row.joinToString(""))
//                }
//            }
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    val part1Measure = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 executed in $part1Measure ms")

    val part2Measure = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 executed in $part2Measure ms")
}
