fun main(args: Array<String>) {
    val trie = Trie(dictionary = listOf("Hello", "World"))

    println(trie.contains("Hello"))
    println(trie.contains("false"))
}