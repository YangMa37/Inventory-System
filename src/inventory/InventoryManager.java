/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Admin
 */
public class InventoryManager {
    private final File productsFile;
    private final File stockFile;
    private final List<Product> products = new ArrayList<>();
    private final List<StockEntry> stockLog = new ArrayList<>();
    
    public InventoryManager() {
        this(new File("data"));
    }
    
    public InventoryManager(File dir) {
        dir.mkdirs();
        productsFile = new File(dir, "products.csv");
        stockFile = new File(dir, "stock_log.csv");
    }
    
    private List<String> readLines(File f) {
        try {
            return Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
        } catch (IOException error) {
            return new ArrayList<>();
        }
    }
    
    private void writeLines(File f, List<String> lines) {
        try {
            Files.write(f.toPath(), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Could not save " + f + ": " + e.getMessage());
        }
    }
    
    public void load () {
        products.clear();
        stockLog.clear();
        
        if (!productsFile.exists()) {
            addSampleData();
            saveProducts();
        } else {
            for (String line: readLines(productsFile)) {
                Product p = Product.fromCsv(line);
                
                if (p != null) {
                    products.add(p);
                }
            }
        }
        
        for (String line: readLines(stockFile)) {
            StockEntry se = StockEntry.fromCsv(line);
            
            if (se != null) {
                stockLog.add(se);
            }
        }
    }
    
    public void saveProducts() {
        List<String> lines = new ArrayList<>();
        for (Product p: products) {
            lines.add(p.toCsv());
        }
        writeLines(productsFile, lines);
        
    }
    
    public void saveStockLog() {
        List<String> lines = new ArrayList<>();
        for (StockEntry s : stockLog) {
            lines.add(s.toCsv());
        }
        writeLines(stockFile, lines);
    }
    private void addSampleData() {
        products.add(new Product("P001", "Rice 1kg", "Groceries", new BigDecimal("58.00"), 120));
        products.add(new Product("P002", "Notebook", "School", new BigDecimal("35.00"), 64));
        products.add(new Product("P003", "Bottled Water", "Drinks", new BigDecimal("20.00"), 200));
        products.add(new Product("P004", "Instant Noodles", "Groceries", new BigDecimal("15.00"), 4));
        products.add(new Product("P005", "Ballpen (blue)", "School", new BigDecimal("10.00"), 150));
        products.add(new Product("P006", "Soap", "Hygiene", new BigDecimal("32.00"), 45));
        products.add(new Product("P007", "Canned Sardines", "Groceries", new BigDecimal("28.00"), 2));
        products.add(new Product("P008", "Biscuits", "Snacks", new BigDecimal("12.00"), 80));
    }
    
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
    
    public List<StockEntry> getStockLog() {
        return Collections.unmodifiableList(stockLog);
    }
    
    public Product findByCode(String code) {
        for (Product p : products) {
            if (p.getCode().equalsIgnoreCase(code.trim())) {
                return p;
            }
        }
        return null;
    }
    
    public List<Product> search(String keyword) {
        String k = keyword.trim().toLowerCase();
        List<Product>result = new ArrayList<>();
        for (Product p: products) {
            if (p.getCode().toLowerCase().contains(k) || p.getName().toLowerCase().contains(k) || p.getCategory().toLowerCase().contains(k)) {
                result.add(p);
            }
        }
        
        return result;
    }
    
    private String check (String code, String name, String category, String priceText, String stockText) {
        if (code.trim().isEmpty()) {
            return "Code is required.";
        }
        if (name.trim().isEmpty()) {
            return "Name is required.";
        }
        if(category.trim().isEmpty()) {
            return "Category is required.";
        }
        if (code.contains(",") || name.contains(",") ||category.contains(",")) {
            return "Commas are not allowed.";
        }
        
        BigDecimal price;
        try {
            price = new BigDecimal (priceText.trim());
        } catch (NumberFormatException error) {
            return "Price must be a number.";
        }
        
        if (price.signum() < 0) {
            return "Price cannot be negative.";
        }
        
        int stock;
        
        try {
            stock = Integer.parseInt(stockText.trim());
        } catch(NumberFormatException error) {
            return "Stock must be a whole number.";
        }
        
        if (stock <0) {
            return "Stock cannot be negative.";
        }
        return null;
        
    }
    
    public String add (String code, String name, String category, String priceText, String stockText) {
        String error = check(code, name, category, priceText, stockText);
        
        if (error != null) {
            return error;
        }
        if (findByCode(code) != null) {
            return "Product code" + code.trim() + " already exist.";
        }
        products.add(new Product(code.trim(), name.trim(), category.trim(), new BigDecimal(priceText.trim()), Integer.parseInt(stockText.trim())));
        
        saveProducts();
        return null;
    }
    
    public String update(String code, String name, String category, String priceText, String stockText) {
        String error = check(code, name, category, priceText, stockText);
        
        if (error != null) {
            return error;
        }
        
        Product p = findByCode(code);
        
        if (p== null) {
            return "No product with code " + code.trim() + ".";
        }
        
        p.setName(name.trim());
        p.setCategory(category.trim());
        p.setPrice(new BigDecimal(priceText.trim()));
        p.setStock(Integer.parseInt(stockText.trim()));
        saveProducts();
        return null;
    }
    
    public String delete (String code) {
        Product p = findByCode(code);
        if (p == null) {
            return "No product with code " + code.trim() + ".";
        }
        products.remove(p);
        saveProducts();
        return null;
    }
    
    public String restock(String code, String qtyText, String user) {
        Product p = findByCode(code);
        if (p == null) {
            return "No product with code " + code.trim() + ".";
        }
        int qty;
        try {
            qty = Integer.parseInt(qtyText.trim());
        } catch (NumberFormatException e) {
            return "Restock quantity must be a whole number.";
        }
        if (qty <= 0) {
            return "Restock quantity must be more than zero.";
        }
        p.setStock(p.getStock() + qty);
        stockLog.add(new StockEntry(Date.now(), p.getCode(), p.getName(),
                qty, p.getStock(), user));
        saveProducts();
        saveStockLog();
        return null;
    }
    
    public String reduceStock(String code, int qty) {
        Product p = findByCode(code);
        if (p == null) {
            return "No product with code "+ code.trim() + ".";
        }
        if (qty <= 0 || qty > p.getStock()) {
            return "Not enough stock for " + p.getName() + ".";
            
        }
        p.setStock(p.getStock() - qty);
        
        saveProducts();
        return null;
    }
    public List<StockEntry> searchStockLog(String keyword) {
        String k = keyword.trim().toLowerCase();
        List<StockEntry> result = new ArrayList<>();
        for (int i = stockLog.size() - 1; i >= 0; i--) {
            StockEntry s = stockLog.get(i);
            if (s.getCode().toLowerCase().contains(k)
                    || s.getName().toLowerCase().contains(k)
                    || s.getBy().toLowerCase().contains(k)) {
                result.add(s);
            }
        }
        return result;
    }      
    
    
}
