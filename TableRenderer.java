import structures.ArrayList;
import structures.LinkedList;

public class TableRenderer 
{
    private static final int PADDING = 1;
    private static final int MAX_COLUMN_WIDTH = 20;

    private String   title;
    private String   lineSeparator;
    private String[] columns;       // columns used to create object (headers without padding)
    private String[] headers;       // headers with padding included
    private int      numColumn;
    private int[]    columnWidths;
    private LinkedList<String[]> data;
    private String[] footers;

    private int tableWidth = 0;

    private boolean renderLineSeparator;

    public TableRenderer(String title, String[] headers, LinkedList<String[]> data, String[] footers, boolean renderLineSeparator)
    {
        this.title = title;
        this.headers = headers;
        this.columns = headers;
        this.numColumn = headers.length;
        this.data = data;
        this.footers = footers;
        this.renderLineSeparator = renderLineSeparator;

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

    private void calculateColumnWidths()
    {
        this.columnWidths = new int[this.numColumn];
        for (int i = 0; i < this.numColumn; i++) {
            // Starts with max length of headers or footers.
            int maxLength = Math.max(this.columns[i].length(), this.footers[i].length());
            // Find max in headers, footers, and data
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
    
    private void writeLines(String[] lines)
    {
        for (String line: lines)
            System.out.println(line);
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

        String[] lines = new String[maxParagraphSize + (this.renderLineSeparator ? 1 : 0)];
        if (this.renderLineSeparator)
            lines[0] = lineSeparator;        // prepend lineseparator

        for (int i = 0; i < maxParagraphSize; i++)
        {
            String[] temp = new String[allContents.size()];
            for (int j = 0; j < allContents.size(); j++)
            {
                if (i >= allContents.get(j).size())
                    temp[j] = Utils.justify(" ", Utils.HorizontalAlign.LEFT, this.columnWidths[j], PADDING);
                else
                    temp[j] = Utils.justify(allContents.get(j).getNode(i).getData(), Utils.HorizontalAlign.LEFT, this.columnWidths[j], PADDING);
            }
            lines[i+(this.renderLineSeparator ? 1 : 0)] = '|' + String.join("|", temp) + '|';
        }

        writeLines(lines);
    }

    private void renderHeader()
    {
        boolean isHeadersEmpty = true;
        for (String header: this.headers)
        {
            if (!header.isBlank())
            {
                isHeadersEmpty = false;
                break;
            }
        }

        if (isHeadersEmpty)
        {
            if (this.title != null)
            {
                String[] lines = {
                    "",
                    Utils.justify(title, Utils.HorizontalAlign.CENTER, this.tableWidth, PADDING),
                };

                writeLines(lines);
            }
        }
        else
        {
            if (this.title != null)
            {
                String[] lines = {
                    "",
                    Utils.justify(title, Utils.HorizontalAlign.CENTER, this.tableWidth, PADDING),
                    this.lineSeparator,
                    '|' + String.join("|", this.headers) + '|',
                };

                writeLines(lines);
            }
            else
            {
                String[] lines = {
                    "",
                    this.lineSeparator,
                    '|' + String.join("|", this.headers) + '|',
                };

                writeLines(lines);
            }
        }
    }

    private void renderData()
    {
        if (!this.renderLineSeparator)
            this.writeLines(new String[] {this.lineSeparator});

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
            this.lineSeparator,
            ' ' + String.join(" ", this.footers) + ' ',
            "",
            "#".repeat(this.tableWidth),
        };
        writeLines(lines);
    }

    public void renderTable()
    {
        this.renderHeader();
        this.renderData();
        this.renderFooter();
    }

    public static TableRendererBuilder builder()
    {
        return new TableRendererBuilder();
    }

    private void justifyHeaders()
    {
        for (int i = 0; i < this.numColumn; i++)
            this.headers[i] = Utils.justify(this.headers[i], Utils.HorizontalAlign.CENTER, this.columnWidths[i], PADDING);
    }

    private void justifyFooters()
    {
        for(int i = 0; i < numColumn; i++)
            this.footers[i] = Utils.justify(this.footers[i], Utils.HorizontalAlign.LEFT, this.columnWidths[i], PADDING);
    }
}
