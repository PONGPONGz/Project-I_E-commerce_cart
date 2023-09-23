public class ActionManager {
    public enum ACTION { ADD_TO_CART, CHECKOUT }
    public static final String[] AVAILABLE_ACTIONS = {
        "Add to cart",
        "Checkout"
    };

    private TableRenderer      tableRenderer;
    private ArrayList<Product> products;
    private Cart               cart;

    public ActionManager(TableRenderer tableRenderer, ArrayList<Product> products, Cart cart)
    {
        this.tableRenderer = tableRenderer;
        this.products = products;
        this.cart = cart;
    }

    public boolean assertAction(int actionNumber)
    {
        return actionNumber < AVAILABLE_ACTIONS.length && actionNumber >= 0;
    }

    public void perform(ACTION action)
    {
        System.out.println("Action: " + action.toString() + " performed.");
        switch (action)
        {
            case ADD_TO_CART:
                int productId = Utils.readIntInRange("Enter product id: ", 0, products.size());
                cart.addByProductId(productId);
                tableRenderer.update(
                    TableRenderer.generateTableHeaders(cart), 
                    TableRenderer.generateTableData(products, cart), 
                    TableRenderer.generateTableFooters(cart)
                );
                tableRenderer.renderTable();
                break;
            case CHECKOUT:
                break;
            default:
                break;
        }
    }

    public void render()
    {
        System.out.println("Available options:");
        for (int i = 0; i < AVAILABLE_ACTIONS.length; i++)
        {
            System.out.println(String.format("  %d. %s", i, AVAILABLE_ACTIONS[i]));
        }
    }
}
