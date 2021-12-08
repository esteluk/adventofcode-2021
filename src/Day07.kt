import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {

    fun part1(input: List<Int>): Int {

        val max = input.maxOrNull() ?: 0
        val min = input.minOrNull() ?: 0

        var cheapestPosition = max
        var cheapestPositionCost = Int.MAX_VALUE
        for (i in min..max) {
            val fuel = input.map { abs(it - i) }.sum()
            if (fuel < cheapestPositionCost) {
                cheapestPosition = i
                cheapestPositionCost = fuel
            }
        }
        println("Cheapest position is $cheapestPosition")
        println("Cost is: $cheapestPositionCost")
        return cheapestPositionCost
    }

    fun part2(input: List<Int>): Int {
        val max = input.maxOrNull() ?: 0
        val min = input.minOrNull() ?: 0

        var cheapestPosition = max
        var cheapestPositionCost = Int.MAX_VALUE
        for (i in min..max) {
            val fuel = input.map { crab ->
                val n = abs(crab - i)
                val sum = (n * (n+1))/2
                sum
            }.sum()
            if (fuel < cheapestPositionCost) {
                cheapestPosition = i
                cheapestPositionCost = fuel
            }
        }
        println("Cheapest position is $cheapestPosition")
        println("Cost is: $cheapestPositionCost")
        return cheapestPositionCost
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputInts("Day07_test")

    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInputInts("Day07")
    val part1Measure = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 executed in $part1Measure ms")

    val part2Measure = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 executed in $part2Measure ms")
}
