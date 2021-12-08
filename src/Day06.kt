import kotlin.system.measureTimeMillis

fun main() {

    fun generate(input: List<Int>): List<Int> {

        val new = input.filter { it == 0 }.map { 8 }

        val decrement = input.map {
            if (it == 0) {
                6
            } else {
                it - 1
            }
        }


        return decrement+new
    }

    fun generate(input: List<Int>, generations: Int): List<Int> {
        var population = input
        for (i in 1..generations) {
            population = generate(population)
        }
        return population
    }

    fun count(input: List<Int>, generations: Int): Int {
        return generate(input, generations).size
    }

    fun part1(input: List<String>): Int {
        val initial = input.first().split(",").map { it.toInt() }
        return count(initial, 80)
    }

    fun generate(input: Map<Int, Long>): Map<Int, Long> {
        var output = mutableMapOf<Int, Long>()
        for (i in 8 downTo 1) {
            output[i-1] = input[i] ?: 0L
        }
        output[8] = input[0] ?: 0L
        output[6] = (output[6] ?: 0L) + (input[0] ?: 0L)

        return output
    }

    fun part2(input: List<String>, generations: Int): Long {
        val initial = input.first().split(",").map { it.toInt() }
        var mMap = mutableMapOf<Int, Long>()
        for (i in initial) {
            mMap[i] = mMap[i]?.plus(1) ?: 1L
        }

        var map: Map<Int, Long> = mMap

        for (i in 1..generations) {
            map = generate(map)
        }

        println(map)

        // count of each number

        return map.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val initial = testInput.first().split(",").map { it.toInt() }

//    check(count(initial, 18) == 26)
//    check(count(initial, 80) == 5934)
    check(part2(testInput, 18) == 26L)
    check(part2(testInput, 80) == 5934L)
    check(part2(testInput, 256) == 26984457539)
//    check(part2(testInput) == 12)

    val input = readInput("Day06")
    val part1Measure = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 executed in $part1Measure ms")

    val part2Measure = measureTimeMillis {
        println(part2(input, 256))
    }
    println("Part 2 executed in $part2Measure ms")
}
