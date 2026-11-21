/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

/**
 *
 * @author Admin
 */
public class StockEntry {
    private final String dateTime;
    private final String code;
    private final String name;
    private final int added;
    private final int stockAfter;
    private final String by;

    //Constructor Method
    public StockEntry(String dateTime, String code, String name, int added, int stockAfter, String by) {
        this.dateTime = dateTime;
        this.code = code;
        this.name = name;
        this.added = added;
        this.stockAfter = stockAfter;
        this.by = by;
    }

    
    //Getter Methods
    public String getDateTime() {
        return dateTime;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAdded() {
        return added;
    }

    public int getStockAfter() {
        return stockAfter;
    }

    public String getBy() {
        return by;
    }

    
    //Same save/load to file as Product (see Product Class)
    public String toCsv() {
        return dateTime + "," + code + "," + name + "," + added + "," + stockAfter + "," + by;
    }
    
    
    public static StockEntry fromCsv(String line) {
        String[] product = line.split(",");
        
        if (product.length != 6) {
            return null;
        }
        
        try {
            return new StockEntry(product[0].trim(), product[1].trim(), product[2].trim(), Integer.parseInt(product[3].trim()), Integer.parseInt(product[4].trim()), product[5].trim());
        } catch (NumberFormatException error) {
            return null;
        }
    }
}
