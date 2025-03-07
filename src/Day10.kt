import java.util.*

fun main() {
    val readInput = readInput("input_day10")
//    val readInput = readInput("input_day10.test")
    val (anim, mp) = parse(readInput)
    val distances = mutableMapOf<Pair<Int, Int>, Int>()
    println("readings $mp")
    distances[anim] = 0
    var toTreat = listOf(anim)
    do {
        toTreat = toTreat.flatMap { propagate(it, mp, distances) }
        println("next will be $toTreat")
    } while (toTreat.isNotEmpty())
    distances.values.max().println()
//    println("final distances $distances")
}

enum class Pipe {
    NW,
    NE,
    SE,
    SW,
    Hor,
    Ver,
    None,
    Animal
}

fun parse(lines: List<String>): Pair<Pair<Int, Int>, MutableMap<Pair<Int, Int>, Pipe>> {

    var animal: Pair<Int, Int> = Pair(-2, -2)
    val mp: MutableMap<Pair<Int, Int>, Pipe> = mutableMapOf()

    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == 'S') {
                animal = Pair(x, y)
                mp[Pair(x, y)] = Pipe.Animal
            } else {
                mkPipe(c).ifPresent {
                    mp[Pair(x, y)] = it
                }

            }
        }
    }
    return Pair(animal, mp)
}

fun mkPipe(inp: Char): Optional<Pipe> {
    return when (inp) {
        'F' -> Optional.of(Pipe.NW)
        '|' -> Optional.of(Pipe.Ver)
        '-' -> Optional.of(Pipe.Hor)
        'J' -> Optional.of(Pipe.SE)
        '7' -> Optional.of(Pipe.NE)
        'L' -> Optional.of(Pipe.SW)
        'S' -> Optional.of(Pipe.Animal)
        else -> Optional.empty()
    }
}

fun propagate(
    point: Pair<Int, Int>,
    readings: Map<Pair<Int, Int>, Pipe>,
    distances: MutableMap<Pair<Int, Int>, Int>
): List<Pair<Int, Int>> {
    println("propagate for $point")
    val get = readings.getOrDefault(point, Pipe.None)
    println("found pipe form $get")
    val curDist: Int = distances[point] ?: return listOf()
    println("with distance $curDist")
    val nxt = mutableListOf<Pair<Int, Int>>()
    val toSouth = Pair(point.first, point.second + 1)
    val toNorth = Pair(point.first, point.second - 1)

    val toEast = Pair(point.first + 1, point.second)
    val toWest = Pair(point.first - 1, point.second)

    val nextDist = curDist + 1
    when (get) {
        Pipe.None -> return listOf()
        Pipe.NW -> {
            val e = distances[toEast]
            if (e == null || e > nextDist) {
                distances[toEast] = nextDist
                nxt.add(toEast)
            }

            val s = distances[toSouth]
            if (s == null || s > nextDist) {
                distances[toSouth] = nextDist
                nxt.add(toSouth)
            }
            return nxt
        }

        Pipe.NE -> {
            val s = distances[toSouth]
            if (s == null || s > nextDist) {
                distances[toSouth] = nextDist
                nxt.add(toSouth)
            }
            val w = distances[toWest]
            if (w == null || w > nextDist) {
                distances[toWest] = nextDist
                nxt.add(toWest)
            }
            return nxt
        }

        Pipe.SE -> {
            val n = distances[toNorth]
            if (n == null || n > nextDist) {
                distances[toNorth] = nextDist
                nxt.add(toNorth)
            }
            val w = distances[toWest]
            if (w == null || w > nextDist) {
                distances[toWest] = nextDist
                nxt.add(toWest)
            }
            return nxt
        }

        Pipe.SW -> {
            val e = distances[toEast]
            if (e == null || e > nextDist) {
                distances[toEast] = nextDist
                nxt.add(toEast)
            }
            val n = distances[toNorth]
            if (n == null || n > nextDist) {
                distances[toNorth] = nextDist
                nxt.add(toNorth)
            }
            return nxt
        }

        Pipe.Hor -> {
            val e = distances[toEast]
            if (e == null || e > nextDist) {
                distances[toEast] = nextDist
                nxt.add(toEast)
            }
            val w = distances[toWest]
            if (w == null || w > nextDist) {
                distances[toWest] = nextDist
                nxt.add(toWest)
            }
            return nxt
        }

        Pipe.Ver -> {
            val n = distances[toNorth]
            if (n == null || n > nextDist) {
                distances[toNorth] = nextDist
                nxt.add(toNorth)
            }
            val s = distances[toSouth]
            if (s == null || s > nextDist) {
                distances[toSouth] = nextDist
                nxt.add(toSouth)
            }
            return nxt
        }

        Pipe.Animal -> {
            // find who connects
            val neighs: MutableList<Pair<Int, Int>> = mutableListOf()
            when (readings.getOrDefault(toNorth, Pipe.None)) {
                Pipe.NW -> neighs.add(toNorth)
                Pipe.NE -> neighs.add(toNorth)
                Pipe.Ver -> neighs.add(toNorth)
                else -> {}
            }
            when (readings.getOrDefault(toSouth, Pipe.None)) {
                Pipe.SE -> neighs.add(toSouth)
                Pipe.SW -> neighs.add(toSouth)
                Pipe.Ver -> neighs.add(toSouth)
                else -> {}
            }
            when (readings.getOrDefault(toWest, Pipe.None)) {
                Pipe.NW -> neighs.add(toWest)
                Pipe.SW -> neighs.add(toWest)
                Pipe.Hor -> neighs.add(toWest)
                else -> {}
            }
            when (readings.getOrDefault(toEast, Pipe.None)) {
                Pipe.NE -> neighs.add(toEast)
                Pipe.SE -> neighs.add(toEast)
                Pipe.Hor -> neighs.add(toEast)
                else -> {}
            }

            neighs.forEach { distances[it] = 1 }
            return neighs
        }
    }

}