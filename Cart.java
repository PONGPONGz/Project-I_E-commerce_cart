import java.util.HashMap;
import java.util.Map;

import structures.ArrayList;
import structures.LinkedList;
import structures.Node;

public class Cart {
    public static final int MAX_CART_ITEMS = 15;
    public static final HashMap<String, Float> DISCOUNT_CODES = new HashMap<>(
        Map.of("3WXD2A3", 100f)
    );

    private LinkedList<Product> items;
    private float discount = 0f;

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

    public float getDiscount()
    {
        return this.discount;
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

        for (Product product: Product.getAvailableProducts())
        {
            // If product is out of stock.
            if (product.getStockCount() < quantity)
            {
                System.out.println("The product " + product.getName() + " is out of stock.");
                return false;
            }
            else if (product.getId() == productId)
            {
                // Repeat add() for <quantity> of times.
                for (int i = 0; i < quantity; i++)
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

    public void applyDiscountCode(String discountCode)
    {
        // If the code is valid
        if (DISCOUNT_CODES.containsKey(discountCode))
            discount = DISCOUNT_CODES.get(discountCode);
        else
            System.out.println("Invalid discount code: " + discountCode);
    }

    public void checkout()
    {
        ArrayList<Integer> seen = new ArrayList<>();
        for (Product product: this.items)
        {
            if (!seen.contains(product.getId()))
            {
                seen.add(product.getId());
                product.setStockCount(product.getStockCount() - this.countDuplicates(product.getId()));
            }
        }

        Invoice invoice = new Invoice(this);
        invoice.render();
        this.clear();
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
