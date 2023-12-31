import java.util.Arrays;
import java.util.Scanner;

import structures.LinkedList;

public class Utils
{   
    public enum HorizontalAlign { LEFT, CENTER };

    private static Scanner scanner = new Scanner(System.in);

    public static String justify(String string, HorizontalAlign align, int length, int padding)
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
                    leftPadding = padding;
            }

            retval = " ".repeat(leftPadding) + string + " ".repeat(length - string.length() - leftPadding);
        }
        else
        {
            retval = string;
        }
        return retval;
    }

    public static LinkedList<String> splitTextIntoLinesOfMaxLength(String str, int maxCharInLine) {
        LinkedList<String> lines = new LinkedList<>();
        StringBuilder line = new StringBuilder(maxCharInLine);
        int offset = 0;

        while (offset < str.length() && maxCharInLine < str.length() - offset) {
            int spaceToWrapAt = offset + maxCharInLine;
            if (offset < spaceToWrapAt) {
                line.append(str, offset, spaceToWrapAt);
                offset = spaceToWrapAt;
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

    public static int readIntInRange(String prompt, int from, int to)
    {
        String line;
        while (true)
        {
            try
            {
                System.out.print(prompt);
                line = scanner.nextLine();
                int currentInt = Integer.parseInt(line);
                if (currentInt >= from && currentInt <= to)
                    return currentInt;
            }
            catch (Exception e)
            {

            }
        }
    }

    public static String readLine(String prompt)
    {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static char readChar(String prompt)
    {
        System.out.print(prompt);
        String line = scanner.nextLine();
        while (line.length() > 1 || line.length() <= 0)
        {
            line = scanner.nextLine();
        }
        return line.charAt(0);
    }

    public static <T> T[] subArray(T[] array, int from, int to) {
        int length = to - from;
        if (length <= 0) {
            throw new IllegalArgumentException("Invalid subarray bounds");
        }

        T[] newArray = Arrays.copyOfRange(array, from, to);
        return newArray;
    }

    public static void closeScanner()
    {
        scanner.close();
    }
}