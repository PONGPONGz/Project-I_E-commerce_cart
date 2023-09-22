import java.io.IOException;
import java.util.Scanner;

class app
{
    private static final String APP_TITLE = "E-Commerce Cart System";
    private static final int    EXIT_KEY  = 9;

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

    private static String[] generateTableFooters(Cart cart)
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

    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> availableProducts = Product.loadProductsFromCSV("Products.csv");

        Cart cart = new Cart();

        String[] headers          = generateTableHeaders(cart);
        LinkedList<String[]> data = generateTableData(availableProducts, cart);
        String[] footers          = generateTableFooters(cart);

        TableRenderer renderer    = new TableRenderer(APP_TITLE, headers, data, footers);
        while (true)
        {
            renderer.render();
            cart.addByProductId(1);
            renderer.update(generateTableHeaders(cart), generateTableData(availableProducts, cart), generateTableFooters(cart));
            renderer.render();

            int inp = scanner.nextInt();
            if (inp == EXIT_KEY)
                break;
        }

        scanner.close();
    }
}