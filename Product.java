import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Product {
    private static ArrayList<Product> availableProducts = new ArrayList<>();

    private int id;
    private String name;
    private String description;
    private float price;
    private int stockCount;

    public static ArrayList<Product> loadProductsFromCSV(String filepath)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath)))
        {
            reader.readLine();          // Ignore first line of csv
            String currentLine;
            while ((currentLine = reader.readLine()) != null)
            {
                String[] product          = currentLine.split(",");
                int productId             = Integer.parseInt(product[0]);
                int stockCount            = Integer.parseInt(product[4]);
                String productName        = product[1];
                String productDescription = product[2];
                float price               = Float.parseFloat(product[3]);

                availableProducts.add(new Product(productId, productName, price, productDescription, stockCount));
            }
        }
        catch (IOException exception)
        {
            System.err.println(exception.getMessage());
        }

        return availableProducts;
    }

    public static ArrayList<Product> getAvailableProducts()
    {
        return availableProducts;
    }

    public Product(int _id, String _name, float _price, String _description, int _stockCount)
    {
        this.id = _id;
        this.name = _name;
        this.price = _price;
        this.description = _description;
        this.stockCount = _stockCount;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public float getPrice()
    {
        return this.price;
    }

    public int getStockCount()
    {
        return this.stockCount;
    }

    @Override
    public String toString()
    {
        return String.format("%s - $%.2f", this.name, this.price);
    }
}
