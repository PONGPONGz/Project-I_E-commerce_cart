package structures;

import java.util.Iterator;

public class LinkedList<T> implements List<T>, Iterable<T> {
    private Node<T> head; // Reference to the head (first element) of the linked list
    private int size = 0; // Number of elements in the linked list

    // Constructor that creates a linked list with a specified head element
    public LinkedList(T headData) {
        this.add(headData); // Add the head element to the linked list
    }

    // Default constructor for an empty linked list
    public LinkedList() {
    }

    // Add an element to the end of the linked list
    public void add(T data) {
        Node<T> newNode = new Node<T>(data); // Create a new node with the given data
        if (this.head == null) {
            this.head = newNode; // If the list is empty, set the new node as the head
        } else {
            Node<T> last = this.head;
            while (last.getNextNode() != null) {
                last = last.getNextNode(); // Traverse the list to find the last node
            }
            last.setNextNode(newNode); // Set the new node as the next node of the last node
        }
        this.size++; // Increase the size of the list
    }

    // Get the element at a specific index in the linked list
    public T get(int index) {
        return this.getNode(index).getData();
    }

    // Get the node at a specific index in the linked list
    public Node<T> getNode(int index) {
        if (index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException(index);

        Node<T> currentNode = this.head;
        while (index-- > 0) {
            currentNode = currentNode.getNextNode(); // Traverse the list to find the node at the given index
        }
        return currentNode;
    }

    // Remove an element at a specific index in the linked list
    public void remove(int index) {
        if (index >= this.size || index < 0)
            throw new IndexOutOfBoundsException(index);

        if (index == 0) {
            this.head = this.head.getNextNode(); // Remove the head element
            size--;
            return;
        }

        Node<T> previousNode = this.getNode(index - 1); // Find the node before the one to be removed
        Node<T> forwardNode = previousNode.getNextNode().getNextNode(); // Find the node after the one to be removed
        previousNode.setNextNode(forwardNode); // Update the references to remove the node
        size--;
    }

    // Swap elements in the linked list by their indices
    public void swapByIndex(int index1, int index2) {
        // Check if the indices are valid
        if (index1 < 0 || index1 >= size || index2 < 0 || index2 >= size)
            throw new IndexOutOfBoundsException("Invalid indices: " + index1 + ", " + index2);

        // Find the nodes and their previous nodes for the indices
        Node<T> previousNode1 = (index1 != 0) ? this.getNode(index1 - 1) : null;
        Node<T> previousNode2 = (index2 != 0) ? this.getNode(index2 - 1) : null;
        Node<T> node1 = previousNode1 != null ? previousNode1.getNextNode() : this.getNode(index1);
        Node<T> node2 = previousNode2 != null ? previousNode2.getNextNode() : this.getNode(index2);

        // Update the references to swap the nodes
        if (previousNode1 == null) {
            this.head = node2;
        } else {
            previousNode1.setNextNode(node2);
        }

        if (previousNode2 == null) {
            this.head = node1;
        } else {
            previousNode2.setNextNode(node1);
        }

        Node<T> temp = node1.getNextNode();
        node1.setNextNode(node2.getNextNode());
        node2.setNextNode(temp);
    }

    // Get the number of elements in the linked list
    public int size() {
        return this.size;
    }

    // Check if the linked list is empty
    public boolean isEmpty() {
        return this.size == 0;
    }

    // Clear the linked list (remove all elements)
    public void clear() {
        Node<T> node = this.head;
        while (node != null) {
            Node<T> temp = node.getNextNode();
            node.setNextNode(null);
            node.setData(null);
            node = temp;
        }

        this.head = null;
        this.size = 0;
    }

    // Get the head node of the linked list
    public Node<T> getHead() {
        return this.head;
    }

    // Get the index of a specific element in the linked list
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<T> e = this.head.getNextNode(); e != null; e = e.getNextNode()) {
                if (e.getData() == null)
                    return index;
                index++;
            }
        } else {
            for (Node<T> e = this.head.getNextNode(); e != null; e = e.getNextNode()) {
                if (o.equals(e.getData()))
                    return index;
                index++;
            }
        }
        return -1;
    }

    // Check if the linked list contains a specific element
    public boolean contains(T object) {
        return indexOf(object) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                T data = current.getData();
                current = current.getNextNode();
                return data;
            }
        };
    }
}
