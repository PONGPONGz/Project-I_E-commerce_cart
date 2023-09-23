import java.io.IOException;
import java.util.Scanner;

class app
{
    private static final String APP_TITLE = "E-Commerce Cart System";
    private static final int    EXIT_KEY  = 9;

    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> availableProducts = Product.loadProductsFromCSV("Products.csv");

        Cart cart = new Cart();

        String[] headers          = TableRenderer.generateTableHeaders(cart);
        LinkedList<String[]> data = TableRenderer.generateTableData(availableProducts, cart);
        String[] footers          = TableRenderer.generateTableFooters(cart);

        TableRenderer tableRenderer  = new TableRenderer(APP_TITLE, headers, data, footers);
        ActionManager actionManager  = new ActionManager(tableRenderer, availableProducts, cart);
        while (true)
        {
            tableRenderer.renderTable();
            actionManager.render();
            System.out.print(String.format("\nEnter your action (0 - %d): ", ActionManager.AVAILABLE_ACTIONS.length - 1));
            int action = scanner.nextInt();
            if (actionManager.assertAction(action))
            {
                actionManager.perform(ActionManager.ACTION.values()[action]);
            }
            
            if (action == EXIT_KEY)
                break;
        }

        scanner.close();
    }
}