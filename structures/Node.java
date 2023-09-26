package structures;

public class Node<T> {
    private T data;                 // Data stored in the node
    private Node<T> nextNode;       // Reference to the next node in the list

    // Constructs a new node with the given data
    public Node(T _data)
    {
        this.data = _data;
    }

    // Return the next node in the linked list
    public Node<T> getNextNode()
    {
        return this.nextNode;
    }

    // Set the next node in the linked list
    public void setNextNode(Node<T> _nextNode)
    {
        this.nextNode = _nextNode;
    }

    // Return the data stored in the node
    public T getData()
    {
        return data;
    }

    // Set new data in the node
    public void setData(T newData)
    {
        this.data = newData;
    }
}
