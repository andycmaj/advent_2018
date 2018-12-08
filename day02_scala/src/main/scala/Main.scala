object Control {
    // https://alvinalexander.com/scala/how-to-open-read-text-files-in-scala-cookbook-examples
    def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
        try {
            f(resource)
        } finally {
            resource.close()
        }

    def readTextFile(filename: String): List[String] = {
        val lines = using(io.Source.fromFile(filename)) { source =>
            (for (line <- source.getLines) yield line).toList
        }
        lines
    }
}

object Scanner {
    def scanId(id: String): (Int, Int) = {
        val groups = id.toList.groupBy(identity);
        val groupSizes = groups.values.map(_.size);
        val twoAndThreeCounts = groupSizes.foldLeft((0, 0)){
            (counts, group) => group match {
                case 2 => (1, counts._2)
                case 3 => (counts._1, 1)
                case _ => counts
            }
        }

        println(id)
        println(twoAndThreeCounts)
        twoAndThreeCounts
    }
}

object Main extends App {
    val result = Control.readTextFile("input")
    val counts = result.foldLeft((0, 0)) {
        (counts, line) => {
            val lineCounts = Scanner.scanId(line);
            (counts._1 + lineCounts._1, counts._2 + lineCounts._2)
        }
    }
    println(counts);
}