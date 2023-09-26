import structures.ArrayList;

public class ActionManager {
    public enum ACTION { DISPLAY_STORE, ADD_TO_CART, CHANGE_ORDER, REMOVE_FROM_CART, CLEAR_CART, CHECKOUT, EXIT }
    public static final String[] AVAILABLE_ACTIONS = {
        "Display Store",
        "Add to cart",
        "Change cart order",
        "Remove from cart",
        "Clear cart",
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
        switch (action)
        {
            case DISPLAY_STORE:
                storeRenderer.renderTable();
                break;
            case ADD_TO_CART:
                int productId = Utils.readIntInRange("Enter product id: ", 1, products.size());
                int quantity  = Utils.readIntInRange("Enter quantity: ", 0, Cart.MAX_CART_ITEMS);
                if (cart.addByProductId(productId, quantity))
                {
                    storeRenderer.update(
                        app.generateStoreHeaders(cart), 
                        app.generateStoreData(products, cart), 
                        app.generateStoreFooters(cart)
                    );
                    storeRenderer.renderTable();
                }
                
                break;
            case CHANGE_ORDER:
                if (cart.size() <= 1)
                {
                    System.out.println("Cart size must be greater than 1 to swap order.");
                    return;
                }

                int firstNum = Utils.readIntInRange("Enter first index to swap: ", 0, cart.size() - 1);
                int secondNum = Utils.readIntInRange("Enter second index to swap: ", 0, cart.size() - 1);
                cart.changeOrder(firstNum, secondNum);
                storeRenderer.update(
                    app.generateStoreHeaders(cart), 
                    app.generateStoreData(products, cart), 
                    app.generateStoreFooters(cart)
                );
                storeRenderer.renderTable();
                break;
            case REMOVE_FROM_CART:
                if (cart.isEmpty())
                {
                    System.out.println("Cart is empty.");
                    return;
                }

                int removeIndex = Utils.readIntInRange("Enter index to remove: ", 0, cart.size() - 1);
                cart.removeByIndex(removeIndex);
                storeRenderer.update(
                    app.generateStoreHeaders(cart), 
                    app.generateStoreData(products, cart), 
                    app.generateStoreFooters(cart)
                );
                storeRenderer.renderTable();
                break;
            case CLEAR_CART:
                cart.clear();
                storeRenderer.update(
                    app.generateStoreHeaders(cart), 
                    app.generateStoreData(products, cart), 
                    app.generateStoreFooters(cart)
                );
                storeRenderer.renderTable();
                break;
            case CHECKOUT:
                if (cart.isEmpty())
                {
                    System.out.println("Cart is empty. Add some items to cart.");
                    return;
                }

                System.out.println("Type \"D\" to apply a discount code; Type \"C\" to continue.");
                char choice = Utils.readChar("Enter your choice: ");
                if (choice == 'D')
                {
                    String discountCode = Utils.readLine("Apply discount code: ");
                    cart.applyDiscountCode(discountCode);
                }

                cart.checkout();             
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
        System.out.println("\nAvailable options:");
        for (int i = 0; i < AVAILABLE_ACTIONS.length; i++)
            System.out.println(String.format("  %d. %s", i, AVAILABLE_ACTIONS[i]));
    }
}
