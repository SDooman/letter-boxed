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

    val trie = Trie(dictionary = dictionary)

    val game =
        GameBoard(
            listOf(
                setOf('B', 'L', 'V'),
                setOf('T', 'P', 'M'),
                setOf('J', 'S', 'C'),
                setOf('E', 'O', 'I')
            )
        )

    println(trie.contains("job"))

    val possibleWords = trie.getPossibleWords(gameBoard = game)

    println(possibleWords.take(10))
}