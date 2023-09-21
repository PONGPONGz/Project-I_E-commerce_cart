public class TableRenderer {
    private enum HorizontalAlign { LEFT, CENTER };

    private static final int PADDING = 1;
    private static final int MAX_COLUMN_WIDTH = 50;

    private String   title;
    private String   lineSeparator;
    private String[] columns;
    private int      numColumn;
    private int[]    columnWidths;
    private LinkedList<String[]> data;
    private String[] footers;

    private int tableHeight = 0;
    private int tableWidth = 0;

    static LinkedList<String> splitTextIntoLinesOfMaxLength(String str, int maxCharInLine) {
        LinkedList<String> lines = new LinkedList<>();
        StringBuilder line = new StringBuilder(maxCharInLine);
        int offset = 0;

        while (offset < str.length() && maxCharInLine < str.length() - offset) {
            int spaceToWrapAt = offset + MAX_COLUMN_WIDTH;
            if (offset < spaceToWrapAt) {
                line.append(str, offset, spaceToWrapAt);
                offset = spaceToWrapAt + 1;
            } else {
                line.append(str, offset, offset + maxCharInLine);
                offset += maxCharInLine;
            }

            lines.add(line.toString());
            line.setLength(0);
        }

        line.append(str.substring(offset));
        lines.add(line.toString());

        return lines;
    }

    public void updateData(LinkedList<String[]> data)
    {
        this.data = data;

        for (int i = 0; i < data.getHead().getData().length; i++)
        {
            int maxLength = 0;
            for (int j = 0; j < data.size(); j++)
            {
                Node<String[]> currentColumn = data.getNode(j);
                int currentColumnWidth = currentColumn.getData()[i].length();
                if (currentColumnWidth > maxLength)
                    maxLength = Math.min(currentColumnWidth, MAX_COLUMN_WIDTH);
            }
            columnWidths[i] = Math.max(maxLength, (this.columnWidths[i] - (PADDING * 2))) + (PADDING * 2);
        }

        String[] temp = new String[numColumn];
        for (int i = 0; i < numColumn; i++)
            temp[i] = "-".repeat(columnWidths[i]);

        this.lineSeparator = '+' + String.join("+", temp) + '+';
        this.tableWidth = this.lineSeparator.length();

        for(int i = 0; i < numColumn; i++)
            this.footers[i] = justify(this.footers[i], HorizontalAlign.LEFT, this.columnWidths[i]);
    }

    public TableRenderer(String title, String[] headers, LinkedList<String[]> data, String[] footers)
    {
        this.title = title;
        this.data = data;
        this.footers = footers;
        this.numColumn = headers.length;

        this.columnWidths = new int[this.numColumn];

        for (int i = 0; i < data.getHead().getData().length; i++)
        {
            int maxLength = 0;
            for (int j = 0; j < data.size(); j++)
            {
                Node<String[]> currentColumn = data.getNode(j);
                int currentColumnWidth = currentColumn.getData()[i].length();
                if (currentColumnWidth > maxLength)
                    maxLength = Math.min(currentColumnWidth, MAX_COLUMN_WIDTH);
            }

            columnWidths[i] = Math.max(maxLength, headers[i].length()) + (PADDING * 2);
        }

        this.columns = headers;
        for (int i = 0; i < numColumn; i++)
        {
            this.columns[i] = justify(this.columns[i], HorizontalAlign.CENTER, columnWidths[i]);
        }

        String[] temp = new String[numColumn];
        for (int i = 0; i < numColumn; i++)
            temp[i] = "-".repeat(columnWidths[i]);

        this.lineSeparator = '+' + String.join("+", temp) + '+';
        this.tableWidth = this.lineSeparator.length();

        for(int i = 0; i < numColumn; i++)
            this.footers[i] = justify(this.footers[i], HorizontalAlign.LEFT, this.columnWidths[i]);
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
            LinkedList<String> cellData = splitTextIntoLinesOfMaxLength(paragraph, MAX_COLUMN_WIDTH);
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
            '|' + String.join("|", this.columns) + '|',
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


    // private int getColumnWidth(java.util.function.Function<Product, Integer> getAttribute, int minLength)
    // {
    //     for (String head: this.columns)

    //     // PADDING1 + ALIGNED_ATTRIBUTE + PADDING2
    //     return getMaxAttributeLength(getAttribute, minLength) + (PADDING * 2);
    // }

    // private int getMaxAttributeLength(java.util.function.Function<Product, Integer> getAttribute, int minLength)
    // {
    //     int maxLength = minLength;
    //     for (int i = 0; i < availableProducts.size(); i++)
    //     {
    //         int attributeLength = getAttribute.apply(availableProducts.get(i));
    //         if (attributeLength > maxLength)
    //             maxLength = attributeLength;
    //     }
    //     return maxLength;
    // }

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

    public void rerender()
    {
        int temp = this.tableHeight;
        System.out.print(String.format("\033[%dA\r\033[J", this.tableHeight));
        this.tableHeight = 0;
        this.render();
        System.out.println("Rerendered " + temp);
    }
}
