import kotlin.system.measureTimeMillis

fun String.rearrange(): String {
    return this.toCharArray().sorted().joinToString("")
}

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.split(" | ")[1] }
            .map { it.split(" ") }
            .fold(0) { acc, row ->
                acc + row.count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
            }
    }

    fun solve(input: List<String>): Map<String, Int> {
        val one = input.first { it.length == 2 }.rearrange()
        val four = input.first { it.length == 4 }.rearrange()
        val seven = input.first { it.length == 3 }.rearrange()
        val eight = input.first { it.length == 7 }.rearrange()

        // digits: possible values
        // 2: one
        // 3: seven
        // 4: four
        // 5: two, three, five
        // 6: zero, six, nine
        // 7: eight

        val positions = "ijklmno".toCharArray()

        /*
            iiii
           j    k
           j    k
            llll
           m    n
           m    n
            oooo
         */

        /*
         acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab |
            cdfeb fcadb cdfeb cdbaf

            1478 known sets
            Take the sets that are six characters long - 0, 6, 9
            overlaps with 1 - not 6
            overlaps with 4 - must be 9
            overlaps with 7 - not 6
            overlaps with 8 -

            Five characters: 2,3,5
            overlaps with 1 - must be 3
            overlaps with 4 - not possible
            overlaps with 7 - not possible
            overlaps with 8 - not possible

            nine contains all of five

         */


        val zero = input.filter { it.length == 6 }
            .map { it.toCharArray().toSet() }
            .filter { !it.containsAll(four.toCharArray().toSet()) }
            .first { it.containsAll(one.toCharArray().toSet()) }
            .joinToString("").rearrange()

        val three = input.filter { it.length == 5 }
            .map { it.toCharArray().toSet() }
            .first { it.containsAll(one.toCharArray().toSet()) }
            .joinToString("").rearrange()

        val six = input.filter { it.length == 6 }
            .map { it.toCharArray().toSet() }
            .filter { !it.containsAll(four.toCharArray().toSet()) }
            .first { !it.containsAll(one.toCharArray().toSet()) }
            .joinToString("").rearrange()

        val nine = input.filter { it.length == 6}
            .map { it.toCharArray().toSet() }
            .first { it.containsAll(four.toCharArray().toSet()) }
            .joinToString("").rearrange()

        val five = input.filter { it.length == 5 }
            .map { it.toCharArray().toSet() }
            .filter { !it.containsAll(one.toCharArray().toSet()) }
            .first { nine.toCharArray().toSet().containsAll(it) }
            .joinToString("").rearrange()

        val two = input.filter { it.length == 5 }
            .map { it.toCharArray().toSet() }
            .first { !nine.toCharArray().toSet().containsAll(it) }
            .joinToString("").rearrange()

        return mapOf(
            zero to 0,
            one to 1,
            two to 2,
            three to 3,
            four to 4,
            five to 5,
            six to 6,
            seven to 7,
            eight to 8,
            nine to 9
        )
    }

    fun part2(input: List<String>): Int {
        val lines = input
            .map { it.split(" | ") }

        val output = lines.map { line ->
            val solution = solve(line[0].split(" "))
            println(solution)
            val output = line[1].split(" ")
                .joinToString("") { (solution[it.rearrange()] ?: 0).toString() }
                .toInt()
            println(output)
            output
        }.sum()

        println(output)
        return output
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")

    val test = listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
    val test2 = listOf("aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea")
    check(part1(testInput) == 26)
    check(part2(test) == 5353)
    check(part2(test2) == 4873)
    check(part2(testInput) == 61229)

    /*
      bbbb
          gf
          gf
      aeae
          gf
          gf
      aeae
     */

    val input = readInput("Day08")
    val part1Measure = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 executed in $part1Measure ms")

    val part2Measure = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 executed in $part2Measure ms")
}
