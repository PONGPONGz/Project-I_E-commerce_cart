public class LinkedList<T> implements List<T>, Iterable<T> {
    private Node<T> head;
    private int size = 0;

    public LinkedList(T headData)
    {
        this.add(headData);
    }

    public LinkedList()
    {
    }

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
        this.size++;
    }

    public T get(int index)
    {
        return this.getNode(index).getData();
    }

    public Node<T> getNode(int index)
    {
        if (index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException(index);

        Node<T> currentNode = this.head;
        while (index-- > 0)
        {
            currentNode = currentNode.getNextNode();
        }
        return currentNode;
    }

    public void remove(int index)
    {
        if (index >= this.size || index < 0)
            throw new IndexOutOfBoundsException(index);

        if (index == 0)
        {
            this.head = this.head.getNextNode();
            size--;
            return;
        }

        Node<T> previousNode = this.getNode(index - 1);
        Node<T> forwardNode  = previousNode.getNextNode().getNextNode();
        previousNode.setNextNode(forwardNode);
        size--;
    }

    public void swapByIndex(int index1, int index2) {
        if (index1 < 0 || index1 >= size || index2 < 0 || index2 >= size) 
            throw new IndexOutOfBoundsException("Invalid indices: " + index1 + ", " + index2);
    
        Node<T> previousNode1 = (index1 != 0) ? this.getNode(index1 - 1) : null;
        Node<T> previousNode2 = (index2 != 0) ? this.getNode(index2 - 1) : null;
        Node<T> node1 = previousNode1 != null ? previousNode1.getNextNode() : this.getNode(index1);
        Node<T> node2 = previousNode2 != null ? previousNode2.getNextNode() : this.getNode(index2);

        if (previousNode1 == null) 
        {
            this.head = node2;
        } 
        else 
        {
            previousNode1.setNextNode(node2);
        }
    
        if (previousNode2 == null) 
        {
            this.head = node1;
        } 
        else 
        {
            previousNode2.setNextNode(node1);
        }
    
        Node<T> temp = node1.getNextNode();
        node1.setNextNode(node2.getNextNode());
        node2.setNextNode(temp);
    }

    public int size()
    {
        return this.size;
    }

    public boolean isEmpty()
    {
        return this.size == 0;
    }

    public void clear()
    {
        Node<T> node = this.head;
        while (node != null)
        {
            Node<T> temp = node.getNextNode();
            node.setNextNode(null);
            node.setData(null);
            node = temp;
        }
        
        this.head = null;
        this.size = 0;
    }

    public Node<T> getHead()
    {
        return this.head;
    }

    public int indexOf(Object o) {
        int index = 0;
        if (o==null) 
        {
            for (Node<T> e = this.head.getNextNode(); e != null; e = e.getNextNode()) 
            {
                if (e.getData()==null)
                    return index;
                index++;
            }
        } 
        else 
        {
            for (Node<T> e = this.head.getNextNode(); e != null; e = e.getNextNode())
            {
                if (o.equals(e.getData()))
                    return index;
                index++;
            }
        }
        return -1;
    }

    public boolean contains(T object)
    {
        return indexOf(object) != -1;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray()
    {
        T[] retval = (T[])new Object[size];
        int i = 0;
        for (Node<T> currentNode = this.head; currentNode.getNextNode() != null; currentNode = currentNode.getNextNode())
            retval[i++] = currentNode.getData();

        return retval;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>()
        {
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
