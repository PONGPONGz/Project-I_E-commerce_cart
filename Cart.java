import structures.ArrayList;
import structures.LinkedList;
import structures.Node;

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

    public void clear()
    {
        this.items.clear();
    }

    public boolean isEmpty()
    {
        return this.size() == 0;
    }

    public float getTotalPrice()
    {
        float total = 0f;
        for (Node<Product> product = this.items.getHead(); product != null; product = product.getNextNode())
            total += product.getData().getPrice();

        return total;
    }

    public LinkedList<String[]> getCartSummary()
    {
        LinkedList<String[]> retval = new LinkedList<>();
        ArrayList<Integer> seen = new ArrayList<>();
        for (Product product: this.items)
        {
            if (!seen.contains(product.getId()))
            {
                seen.add(product.getId());
                int quantity = countDuplicates(product.getId());
                String[] row = {
                    String.format("%2d", product.getId()),
                    product.getName(),
                    product.getDescription(),
                    String.valueOf(quantity),
                    String.format("$%.2f", product.getPrice() * quantity),
                    String.valueOf(product.getStockCount())
                };
                retval.add(row);
            }
            
        }
        return retval;
    }

    public boolean addByProductId(int productId)
    {
        return addByProductId(productId, 1);
    }

    public boolean addByProductId(int productId, int quantity)
    {
        if ((this.size() + quantity) > Cart.MAX_CART_ITEMS)
        { 
            System.out.println(String.format("Cart is full. Quantity of %d exceeds max capacity of %d", quantity, Cart.MAX_CART_ITEMS));   
            return false;
        }

        ArrayList<Product> availableProducts = Product.getAvailableProducts();
        for (int i = 0; i < availableProducts.size(); i++)
        {
            Product product = availableProducts.get(i);
            if (product.getId() == productId)
            {
                for (int j = 0; j < quantity; j++)
                    this.items.add(product);
                return true;
            }
        }

        return false;
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

    private int countDuplicates(int productId)
    {
        int count = 0;
        for (Product product: this.items)
        {
            if (product.getId() == productId)
                count++;
        }
        return count;
    }
}
