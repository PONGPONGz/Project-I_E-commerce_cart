import java.util.Arrays;
import java.util.Scanner;

import structures.LinkedList;

public class Utils
{   
    private static Scanner scanner = new Scanner(System.in);

    public static LinkedList<String> splitTextIntoLinesOfMaxLength(String str, int maxCharInLine) {
        LinkedList<String> lines = new LinkedList<>();
        StringBuilder line = new StringBuilder(maxCharInLine);
        int offset = 0;

        while (offset < str.length() && maxCharInLine < str.length() - offset) {
            int spaceToWrapAt = offset + maxCharInLine;
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

    @SuppressWarnings("unchecked")
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