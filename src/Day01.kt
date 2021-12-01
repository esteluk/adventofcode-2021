fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.map { it.toInt() }

        var count = 0
        for (i in numbers.indices) {
            if (i > 0 && numbers[i] > numbers[i-1]) {
                count += 1
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { it.toInt() }
        val windowSize = 3

        var count = 0

        for (i in windowSize..numbers.lastIndex) {
            val previousWindow = numbers[i-1] + numbers[i-2] + numbers[i-3]
            val currentWindow = numbers[i] + numbers[i-1] + numbers[i-2]

            if (currentWindow > previousWindow) {
                count +=1
            }
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
