public class TableRendererBuilder {
    private String title;
    private String[] headers;
    private LinkedList<String[]> data;
    private String[] footers;

    public TableRendererBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TableRendererBuilder headers(String[] headers) {
        this.headers = headers;
        return this;
    }

    public TableRendererBuilder data(LinkedList<String[]> data) {
        this.data = data;
        return this;
    }

    public TableRendererBuilder footers(String[] footers) {
        this.footers = footers;
        return this;
    }

    public TableRenderer build() {
        return new TableRenderer(title, headers, data, footers);
    }
}
