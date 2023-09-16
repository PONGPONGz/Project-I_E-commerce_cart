public class Node<T> {
    private T data;
    private Node<T> nextNode = null;

    public Node(T _data)
    {
        this.data = _data;
    }

    public Node<T> getNextNode()
    {
        return this.nextNode;
    }

    public void setNextNode(Node<T> _nextNode)
    {
        this.nextNode = _nextNode;
    }

    public T getData()
    {
        return data;
    }
}
