class Table {
    private String title;
    private String[] headers;
    private LinkedList<TableRow> data;
    private String[] footers;

    public Table(String title, String[] headers, LinkedList<String[]> data, String[] footers) {
        this.title = title;
        this.headers = headers;
        this.footers = footers;
        this.data = new LinkedList<>();
        
        for (String[] rowData : data) {
            TableRow row = new TableRow(rowData);
            this.data.add(row);
        }
    }

    public void render() {
        renderTitle();
        renderHeaders();
        renderData();
        renderFooters();
    }
}