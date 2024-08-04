fun main(args: Array<String>) {
    val trie = Trie(dictionary = listOf("Hello", "World"))

    println(trie.contains("Hello"))
    println(trie.contains("false"))

    val game =
        GameBoard(
            listOf(
                setOf('B', 'L', 'V'),
                setOf('T', 'P', 'M'),
                setOf('J', 'S', 'C'),
                setOf('E', 'O', 'I')
            )
        )
}