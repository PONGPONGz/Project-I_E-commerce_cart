class app
{
    private static final String APP_TITLE = "E-Commerce Cart System";
    private static final String[] APP_HEADERS = {"ID", "Name", "Description", "Price", "Stock Count", "Cart"};

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
        cart.addByProductId(3);
        cart.addByProductId(3);
        cart.addByProductId(3);
        cart.addByProductId(3);
        cart.addByProductId(3);
        cart.addByProductId(1);
        cart.addByProductId(1);

        //String[][] data = new String[availableProducts.size() + 1][];
        LinkedList<String[]> data = generateTableData(availableProducts, cart);

        String[] footers = {
            "",
            "",
            "",
            "",
            "",
            String.format("Total: $%.2f", cart.getTotalPrice())
        };

        TableRenderer renderer = new TableRenderer(APP_TITLE, APP_HEADERS, data, footers);
        renderer.render();
        Thread.sleep(2000);
        cart.addByProductId(2);
        cart.addByProductId(2);

        data = generateTableData(availableProducts, cart);
        renderer.updateData(data);
        renderer.render();
        // while (true)
        // {
        //     // http://www.braun-home.net/michael/info/misc/VT100_commands.htm
        //     // ESC[5A - cursor up 5 times
        //     // \r - cursor return to begin of line
        //     // ESC[J - erase to end of screen

        //     Thread.sleep(1000);
        // }
    }
}