public class ActionManager {
    public enum ACTION { ADD_TO_CART, CHANGE_ORDER, REMOVE_FROM_CART, CHECKOUT, EXIT }
    public static final String[] AVAILABLE_ACTIONS = {
        "Add to cart",
        "Change cart order",
        "Remove from cart",
        "Checkout",
        "Exit"
    };

    public boolean isRunning;

    private TableRenderer      storeRenderer;
    private ArrayList<Product> products;
    private Cart               cart;

    public ActionManager(TableRenderer storeRenderer, ArrayList<Product> products, Cart cart)
    {
        this.storeRenderer = storeRenderer;
        this.products = products;
        this.cart = cart;
        this.isRunning = true;
    }

    public boolean assertAction(int actionNumber)
    {
        return actionNumber < AVAILABLE_ACTIONS.length && actionNumber >= 0;
    }

    public void perform(ACTION action)
    {
        //System.out.println("Action: " + action.toString() + " performed.");
        switch (action)
        {
            case ADD_TO_CART:
                int productId = Utils.readIntInRange("Enter product id: ", 0, products.size());
                cart.addByProductId(productId);
                storeRenderer.update(
                    app.generateStoreHeaders(cart), 
                    app.generateStoreData(products, cart), 
                    app.generateStoreFooters(cart)
                );
                storeRenderer.renderTable();
                break;
            case CHECKOUT:
                // Render table of cart and prompt user confirmation
                TableRenderer cartRenderer = TableRenderer.builder()
                    .headers(app.generateCartHeaders(cart))
                    .data(app.generateCartData(cart))
                    .footers(app.generateCartFooters(cart))
                    .build();
                cartRenderer.renderTable();
                checkout();
                break;
            case EXIT:
                this.isRunning = false;
                break;
            default:
                break;
        }
    }

    public void render()
    {
        System.out.println("Available options:");
        for (int i = 0; i < AVAILABLE_ACTIONS.length; i++)
            System.out.println(String.format("  %d. %s", i, AVAILABLE_ACTIONS[i]));
    }

    private void checkout()
    {
        
    }
}
