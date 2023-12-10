fun main() {
    val input: List<List<Int>> = readInput("input_day09")
        .map { it.split(' ') }
        .map { it.map(String::toInt) }

    input.map { it.reversed() }.sumOf { inter1(it) }.println()

    input.sumOf { inter2(it) }.println()

}

fun inter1(arg: List<Int>): Int {
    if (arg.all { it == 0 }) {
        return 0
    }
    return arg[0] + inter1(arg.zipWithNext { a, b -> a - b })
}

fun inter2(arg: List<Int>): Int {
    if (arg.all { it == 0 }) {
        return 0
    }
    return arg[0] - inter2(arg.zipWithNext { a, b -> b - a })
}