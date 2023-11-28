package stack

interface BaseStack<E> {

    val count: Int
    val isEmpty: Boolean
        get() = count == 0
    fun push(element: E)
    fun pop(): E?
    fun peek(): E?
}

class Stack<E>: BaseStack<E> {
    private val storage = arrayListOf<E>()

    override val count: Int
        get() = storage.size
    override fun push(element: E) {
        storage.add(element)
    }

    override fun pop(): E? {
        if (storage.size == 0) {
            return null
        }
        return storage.removeAt(storage.size - 1)
    }

    override fun peek(): E? {
        return storage.lastOrNull()
    }

    companion object {
        fun <E> create(elements: Iterable<E>): Stack<E> {
            val stack = Stack<E>()
            for (element in elements) {
                stack.push(element)
            }
            return stack
        }
    }

    override fun toString(): String {
        return buildString {
            appendLine("----top----")
            storage.asReversed().forEach {
                appendLine(it)
            }
            appendLine("-----------")
        }
    }
}

fun <E> stackOf(vararg elements: E): Stack<E> {
    return Stack.create(elements.asList())
}
