import structures.LinkedList;

public class Invoice {
    private static final String   TITLE   = "Invoice"; 
    private static final String[] HEADERS = {"", "", "", ""};
    private static final String[] FOOTERS = {"", "", "", ""};

    private static int count = 0;

    private TableRenderer renderer;
    private Cart          cart;

    public Invoice(Cart cart)
    {
        count++;        // increment invoice count
        this.cart = cart;
        this.renderer = TableRenderer.builder()
            .title(TITLE)
            .headers(HEADERS)
            .data(this.generateData())
            .footers(FOOTERS)
            .renderLineSeparator(false)
            .build();
    }

    private LinkedList<String[]> generateData()
    {
        LinkedList<String[]> invoiceData = new LinkedList<>();
        String[][] data = {
            {"BILL TO",                  "BILL FROM",           "", ""},
            {"ICT Mahidol",              "NASA",                "", "INVOICE#"},
            {"999 Phutthamonthon Sai 4", "1022/48",             "", String.format("%06d", count)},
            {"Nakhon Pathom",            "Elon Musk Territory", "", ""},
            {"Thailand",                 "Mars",                "", "DATE"},
            {"73170",                    "99999",               "", "24/09/23"},
            {"",                         "",                    "", ""}
        };

        // Add all data to the LinkedList
        for (String[] e: data)
            invoiceData.add(e);

        invoiceData.add(new String[] {"Name", "Description", "Quantity", "Price"});
        invoiceData.add(new String[] {"", "", "", ""});
        for (String[] e: this.cart.getCartSummary())
            invoiceData.add(Utils.subArray(e, 1, e.length));

        float discount = this.cart.getDiscount();
        float total = Math.max(0, this.cart.getTotalPrice() - discount);        // prevent total from being negative.
        invoiceData.add(new String[] {"", "", "", ""});
        invoiceData.add(new String[] {"", "", "", String.format("Discount: $%.2f", discount)});
        invoiceData.add(new String[] {"", "", "", String.format("Total:    $%.2f", total)});

        return invoiceData;
    }

    public void render()
    {
        this.renderer.renderTable();
    }
}
