class app
{
    private static final String APP_TITLE = "E-Commerce Cart System";

    private static String[] generateTableHeaders(Cart cart)
    {
        System.out.println(cart.size());
        return new String[] {"ID", "Name", "Description", "Price", "Stock Count", String.format("Cart (%d / %d)", cart.size(), Cart.MAX_CART_ITEMS)};
    }

    private static LinkedList<String[]> generateTableData(ArrayList<Product> products, Cart cart)
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

    public static void main(String[] args) throws InterruptedException
    {
        ArrayList<Product> availableProducts = Product.loadProductsFromCSV("Products.csv");

        Cart cart = new Cart();
        String[] headers = generateTableHeaders(cart);
        LinkedList<String[]> data = generateTableData(availableProducts, cart);

        String[] footers = {
            "",
            "",
            "",
            "",
            "",
            String.format("Total: $%.2f", cart.getTotalPrice())
        };

        TableRenderer renderer = new TableRenderer(APP_TITLE, headers, data, footers);
        renderer.render();
        Thread.sleep(2000);
        cart.addByProductId(1);
        System.out.println(cart.size());
        data = generateTableData(availableProducts, cart);
        renderer.update(generateTableHeaders(cart), data);
        renderer.render();
    }
}