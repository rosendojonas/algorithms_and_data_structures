package linked_list

/**
 * Linked list from scratch
 * */
class LinkedList<T> : MutableIterable<T>, MutableCollection<T> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    override var size: Int = 0
        private set

    override fun clear() {
        head = null
        tail = null
        size = 0
    }

    override fun addAll(elements: Collection<T>): Boolean {
        for (element in elements) {
            append(element)
        }
        return true
    }

    override fun add(element: T): Boolean {
        append(element)
        return true
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        for (element in elements) {
            if (!contains(element)) return false
        }

        return true
    }

    override fun contains(element: T): Boolean {
        for (item in this) {
            if (item == element) return true
        }

        return false
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun iterator(): MutableIterator<T> {
        return LinkedListIterator(this)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        var result = false
        val iterator = this.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (!elements.contains(item)) {
                iterator.remove()
                result = true
            }
        }

        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        var result = false
        for (item in elements) {
            result = remove(item) || result
        }

        return result
    }

    override fun remove(element: T): Boolean {
        val iterator = iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item == element) {
                iterator.remove()
                return true
            }
        }
        return false
    }

    override fun toString(): String {
        return if (isEmpty()) "Empty List" else head.toString()
    }

    fun push(value: T): LinkedList<T> {
        head = Node(value = value, next = head)
        if (tail == null) {
            tail = head
        }
        size++
        return this
    }

    fun append(value: T): LinkedList<T> {

        if (isEmpty()) {
            push(value)
            return this
        }

        tail?.next = Node(value)
        tail = tail?.next
        size++
        return this
    }

    private fun nodeAt(index: Int): Node<T>? {
        var currentNode: Node<T>? = head
        var currentIndex: Int = 0

        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }

        return currentNode
    }

    fun insertAfter(value: T, afterIndex: Int): LinkedList<T> {

        val afterNode = nodeAt(afterIndex)

        if (afterNode == tail) {
            append(value)
            return this
        }

        val newNode = Node(value, next = afterNode?.next)
        afterNode?.next = newNode
        size++

        return this
    }

    fun pop(): T? {
        if (isNotEmpty()) size--
        val value = head?.value
        head = head?.next

        if (isEmpty()) {
            tail = null
        }

        return value
    }

    fun removeLast(): T? {
        if (head == null) return null
        if (head?.next == null) return pop()

        size--

        var previousNode = head
        var currentNode = head
        var nextNode = currentNode?.next

        while (nextNode != null) {
            previousNode = currentNode
            currentNode = nextNode
            nextNode = currentNode.next
        }

        previousNode?.next = null
        tail = previousNode
        return currentNode?.value
    }

    fun removeAfter(index: Int): T? {
        val node = nodeAt(index) ?: return null

        val value = node.next?.value

        if (node.next == tail) tail = node

        if (node.next != null) size--

        node.next = node.next?.next

        return value
    }

    private fun removeAfter(node: Node<T>): T? {
        val value = node.next?.value

        if (node.next == tail) tail = node

        if (node.next != null) size--

        node.next = node.next?.next

        return value
    }

    class LinkedListIterator<T>(private val linkedList: LinkedList<T>) : MutableIterator<T> {

        private var index = 0
        private var lastNode: Node<T>? = null

        override fun hasNext(): Boolean {
            return index < linkedList.size
        }

        override fun next(): T {
            if (index >= linkedList.size) throw IndexOutOfBoundsException()

            lastNode = if (index == 0) {
                linkedList.nodeAt(0)
            } else {
                lastNode?.next
            }

            index++
            return lastNode!!.value
        }

        override fun remove() {
            if (index == 1) {
                linkedList.pop()
            } else {
                val prevNode = linkedList.nodeAt(index - 2) ?: return
                linkedList.removeAfter(prevNode)
                lastNode = prevNode
            }
            index--
        }

    }
}
