import java.util.HashMap;
import java.util.Map;

import structures.ArrayList;
import structures.LinkedList;
import structures.Node;

public class Cart {
    public static final int MAX_CART_ITEMS = 15;
    // Stores discount codes and their corresponding discounts as key-value pairs
    public static final HashMap<String, Float> DISCOUNT_CODES = new HashMap<>(
        Map.of("3WXD2A3", 100f)
    );

    private LinkedList<Product> items;      // List of items in cart
    private float discount = 0f;            // Discount applied to the cart

    public Cart()
    {
        this.items = new LinkedList<>();        // Initialize empty cart
    }

    public String[] generateHeaders()
    {
        return new String[] {"ID", "Name", "Description", "Quantity", "Price", "Stock Count"};
    }

    public LinkedList<String[]> generateData()
    {
        return this.getCartSummary();
    }

    public String[] generateFooters()
    {
        return new String[] {
            "",
            "",
            "",
            "",
            "",
            String.format("Total: $%.2f", this.getTotalPrice())
        };
    }

    // Return items in the cart as LinkedList
    public LinkedList<Product> getItems()
    {
        return this.items;
    }

    public float getDiscount()
    {
        return this.discount;
    }

    // Return the number of items in the cart
    public int size()
    {
        return this.items.size();
    }

    // Remove all items from the cart
    public void clear()
    {
        this.items.clear();
    }

    // Check if the cart is empty
    public boolean isEmpty()
    {
        return this.size() == 0;
    }

    // Return the total price of items in the cart
    public float getTotalPrice()
    {
        float total = 0f;
        for (Product product: this.items)
            total += product.getPrice();

        return total;
    }

    // Return a summary of items in the cart
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

    // Add a product to the cart by its ID
    public boolean addByProductId(int productId)
    {
        return addByProductId(productId, 1);
    }

    // Add a specific number of a product to the cart by its ID
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

    // Change the order of items in the cart
    public void changeOrder(int index1, int index2)
    {
        this.items.swapByIndex(index1, index2);
    }

    // Remove an item from the cart by its index
    public void removeByIndex(int index)
    {
        this.items.remove(index);
    }

    // Apply a discount code to the cart
    public void applyDiscountCode(String discountCode)
    {
        // If the code is valid
        if (DISCOUNT_CODES.containsKey(discountCode))
            discount = DISCOUNT_CODES.get(discountCode);
        else
            System.out.println("Invalid discount code: " + discountCode);
    }

    // Checkout and generate an invoice
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

    // Count the number of duplicates of the same product in the cart
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
