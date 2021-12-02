fun main() {
    fun part1(input: List<String>): Int {
        var (x, z) = Pair(0, 0)

        input.map { it.split(" ") }.forEach {
            val distance = it[1].toInt()
            if (it[0] == "forward") {
                x += distance
            } else if (it[0] == "down") {
                z += distance
            } else if (it[0] == "up") {
                z -= distance
            }
        }
        println("Position ${x}, ${z}")
        return x*z
    }

    fun part2(input: List<String>): Int {
        var (x, z) = Pair(0, 0)
        var vector = 0

        input.map { it.split(" ") }.forEach {
            val distance = it[1].toInt()
            if (it[0] == "forward") {
                x += distance
                z += (vector * distance)
            } else if (it[0] == "down") {
                vector += distance
            } else if (it[0] == "up") {
                vector -= distance
            }
        }

        println("Position ${x}, ${z}")
        return x*z
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
