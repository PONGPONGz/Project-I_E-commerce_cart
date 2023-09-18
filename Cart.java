public class Cart {
    public LinkedList<Product> items;

    public Cart()
    {
        this.items = new LinkedList<>();
    }

    public int size()
    {
        return this.items.size();
    }

    public void addByProductId(int productId)
    {
        ArrayList<Product> availableProducts = Product.getAvailableProducts();
        for (int i = 0; i < availableProducts.size(); i++)
        {
            Product product = availableProducts.get(i);
            if (product.getId() == productId)
            {
                this.items.add(product);
                break;
            }
        }
    }

    public void changeOrder(int index1, int index2)
    {
        this.items.swapByIndex(index1, index2);
    }

    public void removeByIndex(int index)
    {
        this.items.remove(index);
    }

    @Override
    public String toString()
    {
        String[] allItemsName = new String[this.items.size()];
        Node<Product> currentNode = this.items.getHead();
        for (int i = 0; i < this.items.size(); i++)
        {
            allItemsName[i] = currentNode.getData().getName();
            currentNode = currentNode.getNextNode();
            if (currentNode == null)
                break;
        }

        return String.join(" -> ", allItemsName);
    }
}
