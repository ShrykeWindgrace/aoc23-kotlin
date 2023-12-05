import java.math.BigInteger
import java.security.MessageDigest
import java.util.function.Predicate
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun <T> List<T>.splitPredicate(p: Predicate<T>): List<List<T>> {
    val res: MutableList<List<T>> = mutableListOf()
    var acc: MutableList<T> = mutableListOf()

    this.forEach {
        if (p.test(it)) {
            res.add(acc)
            acc = mutableListOf()
        } else {
            acc.add(it)
        }
    }
    if (acc.isEmpty().not()) {
        res.add(acc)
    }
    return res
}

