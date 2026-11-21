/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Transaction {
    public static final String SHOP_NAME = "Nuelan's Store";
    
    private static final int WIDTH = 40;
    
    private final int number;
    private final String dateTime;
    private final String cashier;
    private final BigDecimal paid;
    private final List<CartItem> items = new ArrayList<>();

    public Transaction(int number, String dateTime, String cashier, BigDecimal paid) {
        this.number = number;
        this.dateTime = dateTime;
        this.cashier = cashier;
        this.paid = paid.setScale(2, RoundingMode.HALF_UP);
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public int getNumber() {
        return number;
    }
    public String getNumberText() {
         return String.format("%04d", number);
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getCashier() {
        return cashier;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }
    
    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal("0.00");
        for (CartItem i: items) {
            total = total.add(i.getSubtotal());
   
        }
        return total;
    }
    
    public BigDecimal getChange() {
        return paid.subtract(getTotal());
    }
    
    public int getItemCount() {
        int count = 0;
        for (CartItem i : items) {
           count += i.getQuantity();
            
        }
            return count;
     }
    
    public List<String> toCsvLines() {
        List<String> lines = new ArrayList<>();
        for (CartItem i: items) {
            lines.add(getNumberText() + "," + dateTime + "," + cashier + "," + paid.toPlainString() + "," + i.getCode() + "," +i.getName() + "," + i.getQuantity() + "," + i.getUnitPrice().toPlainString());
        }
        return lines;
    }
    
    public String toReceipt() {
        String line = "-".repeat(WIDTH);
        StringBuilder sb = new StringBuilder();
        sb.append(center(SHOP_NAME)).append("\n");
        sb.append(center("Transaction #" + getNumberText())).append("\n");
        sb.append(center(dateTime)).append("\n");
        sb.append(center("Cashier: "+ cashier)).append("\n");
        sb.append(line).append("\n");
        
        for (CartItem i : items) {
            sb.append(String.format("%-16.16s %3d x %7s %9s", i.getName(), i.getQuantity(), i.getUnitPrice().toPlainString(), i.getSubtotal().toPlainString())).append("\n");
        }
        
        sb.append(line).append("\n");
        sb.append(String.format("%-10s%30s", "TOTAL",
                getTotal().toPlainString())).append("\n");
        sb.append(String.format("%-10s%30s", "PAID",
                paid.toPlainString())).append("\n");
        sb.append(String.format("%-10s%30s", "CHANGE",
                getChange().toPlainString())).append("\n");
        sb.append("\n").append(center("Thank you!")).append("\n");
        
        return sb.toString();
    }
    
    private String center(String text) {
        int pad = Math.max(0, (WIDTH - text.length()) / 2);
        return " ".repeat(pad) + text;
    }
    
}
