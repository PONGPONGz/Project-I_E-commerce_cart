public class LinkedList<T> {
    private Node<T> head;
    private int size = 0;

    public void add(T data)
    {
        Node<T> newNode = new Node<T>(data);
        if (this.head == null)
        {
            this.head = newNode;
        }
        else
        {
            Node<T> last = this.head;
            while (last.getNextNode() != null) {
                last = last.getNextNode();
            }
  
            last.setNextNode(newNode);
        }

        size++;
    }

    public int size()
    {
        return this.size;
    }
}
