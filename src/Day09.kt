import kotlin.system.measureTimeMillis

fun main() {

    fun lowPoints(heightMap: List<List<Int>>): List<Pair<Int, Int>> {
        var result = mutableListOf<Pair<Int, Int>>()
        for (row in heightMap.indices) {
            for (column in heightMap[row].indices) {
                val isLeadingEdge = column == 0
                val isTrailingEdge = column == heightMap[row].lastIndex
                val isTopEdge = row == 0
                val isBottomEdge = row == heightMap.lastIndex

                val value = heightMap[row][column]

                val isLow = (isTrailingEdge || value < heightMap[row][column+1])
                        && (isLeadingEdge || value < heightMap[row][column-1])
                        && (isTopEdge || value < heightMap[row-1][column])
                        && (isBottomEdge || value < heightMap[row+1][column])
                if (isLow) {
                    result += Pair(column, row)
                }
            }
        }
        return result
    }

    fun part1(input: List<String>): Int {
        val heightMap = input.map { it.toCharArray().map { char -> char.digitToInt() } }
        return lowPoints(heightMap).sumOf { heightMap[it.second][it.first] + 1 }
    }

    fun flood(heightMap: List<List<Int>>, point: Pair<Int, Int>, set: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        var newPoints = set.toMutableSet()
        val column = point.first
        val row = point.second
        val isLeadingEdge = column == 0
        val isTrailingEdge = column == heightMap[row].lastIndex
        val isTopEdge = row == 0
        val isBottomEdge = row == heightMap.lastIndex

        if (!isLeadingEdge) {
            val prevPoint = Pair(column-1, row)
            if (heightMap[row][column-1] < 9 && !newPoints.contains(prevPoint)) {
                newPoints.add(prevPoint)
                newPoints = flood(heightMap, prevPoint, newPoints).toMutableSet()
            }
        }
        if (!isTrailingEdge) {
            var nextPoint = Pair(column+1, row)
            if (heightMap[row][column+1] < 9 && !newPoints.contains(nextPoint)) {
                newPoints.add(nextPoint)
                newPoints = flood(heightMap, nextPoint, newPoints).toMutableSet()
            }
        }

        if (!isTopEdge) {
            val abovePoint = Pair(column, row-1)
            if (heightMap[row-1][column] < 9 && !newPoints.contains(abovePoint)) {
                newPoints.add(abovePoint)
                newPoints = flood(heightMap, abovePoint, newPoints).toMutableSet()
            }
        }

        if (!isBottomEdge) {
            val belowPoint = Pair(column, row+1)
            if (heightMap[row+1][column] < 9 && !newPoints.contains(belowPoint)) {
                newPoints.add(belowPoint)
                newPoints = flood(heightMap, belowPoint, newPoints).toMutableSet()
            }
        }

        return newPoints
    }

    fun part2(input: List<String>): Int {
        val heightMap = input.map { it.toCharArray().map { char -> char.digitToInt() } }
        val lowPoints = lowPoints(heightMap)
        val basins = lowPoints.map {
            var points = setOf(it)
            flood(heightMap, it, points)
        }.sortedBy { -it.size }
            .take(3)
            .map { it.size }

        println(basins)

        return basins.reduce { acc, set -> acc * set }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    val part1Measure = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 executed in $part1Measure ms")

    val part2Measure = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 executed in $part2Measure ms")
}
