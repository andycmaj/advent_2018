object Control {
    // https://alvinalexander.com/scala/how-to-open-read-text-files-in-scala-cookbook-examples
    def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
        try {
            f(resource)
        } finally {
            resource.close()
        }

    def readTextFile(filename: String): List[String] = {
        using(io.Source.fromFile(filename)) { source =>
            (for (line <- source.getLines) yield line).toList
        }
    }
}

object Scanner {
    def scanId(id: String): (Int, Int) = {
        val groups = id.toList.groupBy(identity);
        val groupSizes = groups.values.map(_.size);
        groupSizes.foldLeft((0, 0)){
            (counts, group) => group match {
                case 2 => (1, counts._2)
                case 3 => (counts._1, 1)
                case _ => counts
            }
        }
    }
}

// returns count of differing positions
/*
for ((x,y) <- xs zip ys) yield x*y
same as
(xs zip ys) map { case (x,y) => x*y }
*/
object Part2 {
    def compareIds(first: String, second: String): Boolean = {
        (first zip second).foldLeft(0) {
            (count, pair) => pair match {
                case p if (p._1 == p._2) => count
                case _ => count + 1
            }
        } == 1
    }
}

object Main extends App {
    // part 1
    val part1Lines = Control.readTextFile("input_part1")
    val counts = part1Lines.foldLeft((0, 0)) {
        (counts, line) => {
            val lineCounts = Scanner.scanId(line);
            (counts._1 + lineCounts._1, counts._2 + lineCounts._2)
        }
    }
    println(counts);

    // part 2
    val part2Lines = Control.readTextFile("input_part2")
    val offByOnePair = part2Lines
        .combinations(2)
        .find(combination =>
            Part2.compareIds(combination.head, combination.last)
        )
    println(offByOnePair)
}
