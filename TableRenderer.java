public class TableRenderer {
    private enum HorizontalAlign { LEFT, CENTER };

    private static final int PADDING = 1;
    private static final int MAX_COLUMN_WIDTH = 50;

    private String   title;
    private String   lineSeparator;
    private String[] columns;       // columns used to create object (headers without padding)
    private String[] headers;       // headers with padding included
    private int      numColumn;
    private int[]    columnWidths;
    private LinkedList<String[]> data;
    private String[] footers;

    private int tableHeight = 0;
    private int tableWidth = 0;

    

    private void calculateColumnWidths()
    {
        this.columnWidths = new int[this.numColumn];
        for (int i = 0; i < this.numColumn; i++) {
            int maxLength = this.columns[i].length();
            for (String[] row : this.data) {
                int cellLength = row[i].length();
                if (cellLength > maxLength) {
                    maxLength = cellLength;
                }
            }
            this.columnWidths[i] = Math.min(maxLength, MAX_COLUMN_WIDTH) + (PADDING * 2);
        }
    }

    private void generateLineSeparator()
    {
        String[] temp = new String[numColumn];
        for (int i = 0; i < numColumn; i++)
            temp[i] = "-".repeat(columnWidths[i]);

        this.lineSeparator = '+' + String.join("+", temp) + '+';
        this.tableWidth = this.lineSeparator.length();
    }

    public TableRenderer(String title, String[] headers, LinkedList<String[]> data, String[] footers)
    {
        this.title = title;
        this.headers = headers;
        this.columns = headers;
        this.numColumn = headers.length;
        this.data = data;
        this.footers = footers;
        
        calculateColumnWidths();
        justifyHeaders();
        generateLineSeparator();
        justifyFooters();
    }

    public void update(String[] headers, LinkedList<String[]> data, String[] footers)
    {
        this.headers = headers;
        this.columns = headers;
        this.numColumn = headers.length;
        this.data = data;
        this.footers = footers;

        calculateColumnWidths();
        justifyHeaders();
        generateLineSeparator();
        justifyFooters();
    }

    // A method to output table so we can track number of lines.
    private void writeLines(String[] lines)
    {
        for (String line: lines)
            System.out.println(line);
        
        this.tableHeight += lines.length;
    }

    private void writeData(String[] contents)
    {
        int maxParagraphSize = 0;
        ArrayList<LinkedList<String>> allContents = new ArrayList<>();
        for (String paragraph: contents)
        {
            LinkedList<String> cellData = Utils.splitTextIntoLinesOfMaxLength(paragraph, MAX_COLUMN_WIDTH);
            allContents.add(cellData);
            if (cellData.size() > maxParagraphSize)
                maxParagraphSize = cellData.size();
        }

        String[] lines = new String[maxParagraphSize + 1];
        for (int i = 0; i < maxParagraphSize; i++)
        {
            String[] temp = new String[allContents.size()];
            for (int j = 0; j < allContents.size(); j++)
            {
                if (i >= allContents.get(j).size())
                    temp[j] = justify(" ", HorizontalAlign.LEFT, this.columnWidths[j]);
                else
                    temp[j] = justify(allContents.get(j).getNode(i).getData(), HorizontalAlign.LEFT, this.columnWidths[j]);
            }
            lines[i] = '+' + String.join("|", temp) + '+';
        }
        lines[maxParagraphSize] = lineSeparator;        // append lineseparator

        writeLines(lines);
    }

    private void renderHeader()
    {
        String[] lines = {
            "",
            justify(title, HorizontalAlign.CENTER, this.tableWidth),
            this.lineSeparator,
            '|' + String.join("|", this.headers) + '|',
            this.lineSeparator
        };

        writeLines(lines);
    }

    private void renderData()
    {
        for (int i = 0; i < this.data.size(); i++)
        {
            String[] temp = new String[this.numColumn];
            for (int j = 0; j < this.numColumn; j++)
                temp[j] = this.data.getNode(i).getData()[j];

            this.writeData(temp);
        }
    }

    private void renderFooter()
    {
        String[] lines = {
            ' ' + String.join(" ", this.footers) + ' ',
            "",
            "#".repeat(this.tableWidth),
            ""
        };
        writeLines(lines);
    }

    public void render()
    {
        this.renderHeader();
        this.renderData();
        this.renderFooter();
    }

    private static String justify(String string, HorizontalAlign align, int length)
    {
        String retval;
        if (string.length() < length)
        {
            int leftPadding;
            switch (align)
            {
                case CENTER:
                    leftPadding = (length - string.length()) / 2;
                    break;
                case LEFT:
                default:
                    leftPadding = PADDING;
            }

            retval = " ".repeat(leftPadding) + string + " ".repeat(length - string.length() - leftPadding);
        }
        else
        {
            retval = string;
        }
        return retval;
    }

    private void justifyHeaders()
    {
        for (int i = 0; i < this.numColumn; i++)
            this.headers[i] = justify(this.headers[i], HorizontalAlign.CENTER, this.columnWidths[i]);
    }

    private void justifyFooters()
    {
        for(int i = 0; i < numColumn; i++)
            this.footers[i] = justify(this.footers[i], HorizontalAlign.LEFT, this.columnWidths[i]);
    }
}
