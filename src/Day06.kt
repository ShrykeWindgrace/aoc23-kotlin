import java.util.regex.Pattern
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    val readInput = readInput("input_day06")
//    val readInput = readInput("input_day06.test")
    val splitter = Pattern.compile("\\s+")
    val times = readInput[0].split(splitter).drop(1).map(String::toLong)
    val distances = readInput[1].split(splitter).drop(1).map(String::toLong)

    times.zip(distances).map { interval(it.first, it.second) }.reduce(Long::times).println()

    val time2 = readInput[0].split(splitter).drop(1).reduce { acc, s -> acc + s }.toLong()
    val distance2 = readInput[1].split(splitter).drop(1).reduce { acc, s -> acc + s }.toLong()
    interval(time2, distance2).println()
}

fun interval(t: Long, d: Long): Long {
    val discr: Double = sqrt((t * t - 4 * (d + 1)).toDouble())
    val rootM: Long = ceil((t - discr) / 2).toLong()
    val rootP: Long = floor((t + discr) / 2).toLong()

    return if (rootP >= rootM) {
        1 + rootP - rootM
    } else {
        0
    }
}

