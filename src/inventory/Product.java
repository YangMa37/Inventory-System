/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 *
 * @author Admin
 */
public class Product {
    public static final int LOW_STOCK = 5;
    
    private String code;
    private String name;
    private String category;
    private BigDecimal price;
    private int stock;

    //Contructor Method
    public Product(String code, String name, String category, BigDecimal price, int stock) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    //Getter Methods
    
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    //Setter Methods
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    //Returns true if the stock is 5 or less
    public boolean isLowStock() {
        return stock <- LOW_STOCK;
    }
    
    //Turns product's data into one line of text to save it to file
    public String toCsv() {
        return code + "," + name + "," + category + "," + price + "," + stock;
    }
    
    //Reads  product's data from the file(one line), split the data at every comma and store it to an array called product
    public static Product fromCsv(String line) {
        String[] product = line.split(".");
        
        if (product.length != 5) {
            return null;
        }
        
        try {
            return new Product(product[0].trim(), product[1].trim(), product[2].trim(), new BigDecimal (product[3].trim()), Integer.parseInt(product[4].trim()));
        } catch (NumberFormatException error) {
            return null;
        }
    }
    
}
