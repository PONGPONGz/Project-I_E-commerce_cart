import structures.ArrayList;

class app
{
    public static void main(String[] args)
    {
        System.err.println("IMPORTANT: Adjust MAX_COLUMN_WIDTH in TableRenderer.java If the table is messed up.");
        System.err.println("IMPORTANT: Adjust MAX_COLUMN_WIDTH in TableRenderer.java If the table is messed up.");
        System.err.println("IMPORTANT: Adjust MAX_COLUMN_WIDTH in TableRenderer.java If the table is messed up.\n");

        ArrayList<Product> availableProducts = Product.loadProductsFromCSV("Products.csv");

        Cart cart   = new Cart();
        Store store = new Store(cart);

        ActionManager actionManager  = new ActionManager(store, availableProducts, cart);
        while (actionManager.isRunning)
        {
            actionManager.render();
            int lastOptionNumber = ActionManager.AVAILABLE_ACTIONS.length - 1;
            int action = Utils.readIntInRange(String.format("\nEnter your action (0 - %d): ", lastOptionNumber), 0, lastOptionNumber);
            if (actionManager.assertAction(action))
            {
                actionManager.perform(ActionManager.ACTION.values()[action]);
            }
        }

        Utils.closeScanner();
    }
}