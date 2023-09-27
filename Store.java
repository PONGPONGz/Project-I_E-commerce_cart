import structures.ArrayList;
import structures.LinkedList;
import structures.Node;

public class Store {
    // Find first available discount code. (Set doesn't have method get(), so stream is useful in this case.)
    private static final String TITLE = String.format("E-Commerce Cart System (Apply code: %s)", Cart.DISCOUNT_CODES.keySet().stream().findFirst().get().toString());
    
    private static ArrayList<Product> products = Product.getAvailableProducts(); 

    private TableRenderer renderer;
    private Cart cart;

    public Store(Cart cart)
    {
        this.cart = cart;
        this.refresh();
    }

    public String[] generateHeaders()
    {
        return new String[] {"ID", "Name", "Description", "Price", "Stock Count", String.format("Cart (%d / %d)", cart.size(), Cart.MAX_CART_ITEMS)};
    }

    public LinkedList<String[]> generateData()
    {
        LinkedList<String[]> data = new LinkedList<>();
        Node<Product> currentNode = this.cart.getItems().getHead();

        int dataLength = Math.max(products.size(), this.cart.getItems().size());
        for (int i = 0; i < dataLength; i++)
        {
            Product product = null;
            if (i < products.size())
                product = products.get(i);
            
            data.add(new String[] { 
                product != null ? String.format("%02d", product.getId()) : "",
                product != null ? product.getName() : "",
                product != null ? product.getDescription() : "",
                product != null ? String.format("$%.2f", product.getPrice()) : "",
                product != null ? String.valueOf(product.getStockCount()) : "",
                currentNode != null ? (i + ": " + currentNode.getData().toString()) : ""
            });

            if (currentNode != null)
                currentNode = currentNode.getNextNode();
        }

        return data;
    }

    public String[] generateFooters()
    {
        return new String[] {
            "",
            "",
            "",
            "",
            "",
            String.format("Total: $%.2f", this.cart.getTotalPrice())
        };
    }

    public void render()
    {
        this.renderer.renderTable();
    }

    public void refresh()
    {
        this.renderer = TableRenderer.builder()
            .title(TITLE)
            .headers(this.generateHeaders())
            .data(this.generateData())
            .footers(this.generateFooters())
            .build();
            
        this.render();
    }
}
