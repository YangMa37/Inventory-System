/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author Admin
 */
public class Cart {
    private final List<CartItem> items = new ArrayList<>();
    
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    public int quantityOf(String code) {
        for (CartItem i: items) {
            if (i.getCode().equals(code)) {
                return i.getQuantity();
            }
        }
        
        return 0;
    }
    
    public String add(Product p, String qtyText) {
        if (p == null) {
            return "Choose a product first.";
        }
        
        int qty;
        
        try {
            qty = Integer.parseInt(qtyText.trim());
            
        } catch (NumberFormatException error) {
                return "Quantity must be a whole number.";
        }
        
        if(qty <= 0) {
            return "Quantity must be more than zero.";
        }
        
        int available = p.getStock() - quantityOf(p.getCode());
        if (qty > available) {
            return "Not enough stock. Only " + available + " left to sell.";
        }
        for (CartItem i : items) {
            if (i.getCode().equals(p.getCode())) {
                i.addQuantity(qty);
                return null;
            }
        }
        
        items.add(new CartItem(p.getCode(), p.getName(), p.getPrice(), qty));
        return null;
    }
    
    public void removeAt(int index) {
        if (index >= 0 && index <items.size()) {
            items.remove(index);
        }
    }
    
    public void clear() {
        items.clear();
    }
    
    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal("0.00");
        for (CartItem i: items) {
            total = total.add(i.getSubtotal());
        }
        return total;
    }
}
