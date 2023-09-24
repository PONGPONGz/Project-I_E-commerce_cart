import structures.LinkedList;

public class Invoice {
    private static final String   TITLE   = "Invoice"; 
    private static final String[] HEADERS = {"BILL TO", "BILL FROM", "", ""};
    private static final String[] FOOTERS = {"", "", "", ""};

    private static int count = 0;

    private LinkedList<String[]> cartSummary;
    private TableRenderer renderer;

    

    public Invoice(Cart cart)
    {
        count++;
        this.cartSummary = cart.getCartSummary();
        String[][] data = {
            {"ICT Mahidol", "", "", "INVOICE#"},
            {"105/3 M2", "", "", String.format("%06d", count)},
            {"Nakhon Pathom", "", "", ""},
            {"Thailand", "", "", "DATE"},
            {"73110", "", "", "24/09/23"},
            {"", "", "", ""}
        };
        LinkedList<String[]> invoiceData = new LinkedList<>();
        for (String[] e: data)
            invoiceData.add(e);

        invoiceData.add(new String[] {"Name", "Description", "Quantity", "Price"});
        invoiceData.add(new String[] {"", "", "", ""});
        for (String[] e: cartSummary)
            invoiceData.add(Utils.subArray(e, 1, e.length));

        String[] footers = {"", "", "", String.format("Total: %.2f", cart.getTotalPrice())};

        this.renderer = TableRenderer.builder()
            .title(TITLE)
            .headers(HEADERS)
            .data(invoiceData)
            .footers(footers)
            .renderLineSeparator(false)
            .build();
    }

    public void render()
    {
        this.renderer.renderTable();
    }
}
