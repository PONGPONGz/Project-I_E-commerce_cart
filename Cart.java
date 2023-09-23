public class Cart {
    public static final int MAX_CART_ITEMS = 15;

    private LinkedList<Product> items;

    public Cart()
    {
        this.items = new LinkedList<>();
    }

    public LinkedList<Product> getItems()
    {
        return this.items;
    }

    public int size()
    {
        return this.items.size();
    }

    public float getTotalPrice()
    {
        float total = 0f;
        for (Node<Product> product = this.items.getHead(); product != null; product = product.getNextNode())
            total += product.getData().getPrice();

        return total;
    }

    public String[] getCartSummary()
    {
        int sizeOfDistinctProduct = 0;
        ArrayList<Integer> seen = new ArrayList<>();
        for (Product product: this.items)
        {
            System.out.println(product.getId());
            if (!seen.contains(product.getId()))
            {
                seen.add(product.getId());
            }
            
        }
        return new String[5];
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
        String[] allItemsName = new String[this.items.size() + 1];      // items size + total
        Node<Product> currentNode = this.items.getHead();
        for (int i = 0; i < this.items.size(); i++)
        {
            allItemsName[i] = currentNode.getData().getName();
            currentNode = currentNode.getNextNode();
            if (currentNode == null)
                break;
        }
        allItemsName[this.items.size()] = String.valueOf(this.getTotalPrice());
        return String.join(" -> ", allItemsName);
    }
}
