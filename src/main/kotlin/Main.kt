import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
    val dictionary = mutableSetOf<String>()
    val file = File("src/main/resources/simple_dict.csv")

    BufferedReader(FileReader(file)).use { reader ->
        reader.forEachLine { word ->
            dictionary.add(word)
        }
    }

    val game =
        Game(
            charSets = listOf(
                setOf('B', 'L', 'V'),
                setOf('T', 'P', 'M'),
                setOf('J', 'S', 'C'),
                setOf('E', 'O', 'I')
            ),
            maxWords = 6
        )

    val solver = Solver(dictionary = dictionary, game = game)

    println(solver.solve())
}