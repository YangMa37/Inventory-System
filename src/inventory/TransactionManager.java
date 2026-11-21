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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class TransactionManager {

    private final File file;
    private final File receiptsDir;
    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionManager() {
        this(new File("data"));
    }

    public TransactionManager(File dir) {
        dir.mkdirs();
        receiptsDir = new File(dir, "receipts");
        receiptsDir.mkdirs();
        file = new File(dir, "transactions.csv");
    }

    private List<String> readLines(File f) {
        try {
            return Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
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

    public void load() {
        transactions.clear();
        Map<Integer, Transaction> byNumber = new LinkedHashMap<>();
        for (String line : readLines(file)) {
            String[] p = line.split(",");
            if (p.length != 8) {
                continue;
            }
            try {
                int number = Integer.parseInt(p[0].trim());
                BigDecimal paid = new BigDecimal(p[3].trim());
                int qty = Integer.parseInt(p[6].trim());
                BigDecimal price = new BigDecimal(p[7].trim());
                Transaction t = byNumber.get(number);
                if (t == null) {
                    t = new Transaction(number, p[1].trim(), p[2].trim(),
                            paid);
                    byNumber.put(number, t);
                }
                t.addItem(new CartItem(p[4].trim(), p[5].trim(),
                        price, qty));
            } catch (NumberFormatException e) {
                // a damaged line: skip it and carry on
            }
        }
        transactions.addAll(byNumber.values());
    }

    private void save() {
        List<String> lines = new ArrayList<>();
        for (Transaction t : transactions) {
            lines.addAll(t.toCsvLines());
        }
        writeLines(file, lines);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public int nextNumber() {
        int max = 0;
        for (Transaction t : transactions) {
            max = Math.max(max, t.getNumber());
        }
        return max + 1;
    }

    public String nextNumberText() {
        return String.format("%04d", nextNumber());
    }

    public Transaction findByNumber(int number) {
        for (Transaction t : transactions) {
            if (t.getNumber() == number) {
                return t;
            }
        }
        return null;
    }

    public List<Transaction> search(String keyword) {
        String k = keyword.trim().toLowerCase();
        List<Transaction> result = new ArrayList<>();
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getNumberText().contains(k)
                    || t.getDateTime().toLowerCase().contains(k)
                    || t.getCashier().toLowerCase().contains(k)) {
                result.add(t);
            }
        }
        return result;
    }

    public String validateSale(Cart cart, String paidText,
            InventoryManager inv) {
        if (cart.isEmpty()) {
            return "The cart is empty.";
        }
        for (CartItem i : cart.getItems()) {
            Product p = inv.findByCode(i.getCode());
            if (p == null) {
                return i.getName() + " is no longer in the product list.";
            }
            if (i.getQuantity() > p.getStock()) {
                return "Not enough stock for " + i.getName()
                        + ". Only " + p.getStock() + " left.";
            }
        }
        BigDecimal paid;
        try {
            paid = new BigDecimal(paidText.trim());
        } catch (NumberFormatException e) {
            return "Amount paid must be a number.";
        }
        if (paid.compareTo(cart.getTotal()) < 0) {
            return "Amount paid is less than the total.";
        }
        return null;
    }

    public Transaction complete(Cart cart, String paidText,
            String cashier, InventoryManager inv) {
        if (validateSale(cart, paidText, inv) != null) {
            return null;
        }
        Transaction t = new Transaction(nextNumber(), Date.now(), cashier,
                new BigDecimal(paidText.trim()));
        for (CartItem i : cart.getItems()) {
            t.addItem(new CartItem(i.getCode(), i.getName(),
                    i.getUnitPrice(), i.getQuantity()));
            inv.reduceStock(i.getCode(), i.getQuantity());
        }
        transactions.add(t);
        save();
        writeReceipt(t, receiptFile(t));
        return t;
    }

    public File receiptFile(Transaction t) {
        return new File(receiptsDir, "receipt_" + t.getNumberText() + ".txt");
    }

    public boolean writeReceipt(Transaction t, File f) {
        try {
            Files.writeString(f.toPath(), t.toReceipt(),
                    StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}


