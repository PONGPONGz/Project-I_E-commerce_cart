class app
{
    private static final String APP_TITLE = "E-Commerce Cart System";

    public static String[] generateStoreHeaders(Cart cart)
    {
        return new String[] {"ID", "Name", "Description", "Price", "Stock Count", String.format("Cart (%d / %d)", cart.size(), Cart.MAX_CART_ITEMS)};
    }

    public static LinkedList<String[]> generateStoreData(ArrayList<Product> products, Cart cart)
    {
        LinkedList<String[]> data = new LinkedList<>();
        Node<Product> currentNode = cart.getItems().getHead();

        int dataLength = Math.max(products.size(), cart.getItems().size());
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
                currentNode != null ? ((i + 1) + ": " + currentNode.getData().toString()) : ""
            });

            if (currentNode != null)
                currentNode = currentNode.getNextNode();
        }

        return data;
    }

    public static String[] generateStoreFooters(Cart cart)
    {
        return new String[] {
            "",
            "",
            "",
            "",
            "",
            String.format("Total: $%.2f", cart.getTotalPrice())
        };
    }

    public static String[] generateCartHeaders(Cart cart)
    {
        return new String[] {"ID", "Name", "Description", "Quantity", "Price", "Stock Count"};
    }

    public static LinkedList<String[]> generateCartData(Cart cart)
    {
        LinkedList<String[]> data = new LinkedList<>();
        cart.getCartSummary();
        return data;
    }

    public static String[] generateCartFooters(Cart cart)
    {
        return new String[] {
            "",
            "",
            "",
            "",
            "",
            String.format("Total: $%.2f", cart.getTotalPrice())
        };
    }

    public static void main(String[] args)
    {
        ArrayList<Product> availableProducts = Product.loadProductsFromCSV("Products.csv");

        Cart cart = new Cart();

        String[] headers          = generateStoreHeaders(cart);
        LinkedList<String[]> data = generateStoreData(availableProducts, cart);
        String[] footers          = generateStoreFooters(cart);

        TableRenderer storeRenderer  = TableRenderer.builder()
            .title(APP_TITLE)
            .headers(headers)
            .data(data)
            .footers(footers)
            .build();
        ActionManager actionManager  = new ActionManager(storeRenderer, availableProducts, cart);
        storeRenderer.renderTable();
        actionManager.render();
        while (actionManager.isRunning)
        {
            // storeRenderer.renderTable();
            // actionManager.render();

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