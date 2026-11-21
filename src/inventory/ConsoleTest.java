package inventory;

public class ConsoleTest {

    public static void main(String[] args) {
        InventoryManager inv = new InventoryManager();
        inv.load();
        System.out.println("Loaded: " + inv.getProducts().size());
        System.out.println("Add P100: "
                + inv.add("P100", "Eraser", "School", "7.50", "30"));
        System.out.println("Add P100 again: "
                + inv.add("P100", "Eraser", "School", "7.50", "30"));
        System.out.println("Empty name: "
                + inv.add("P101", "", "School", "7.50", "30"));
        System.out.println("Bad price: "
                + inv.add("P101", "Pencil", "School", "abc", "30"));
        System.out.println("Negative stock: "
                + inv.add("P101", "Pencil", "School", "5", "-1"));
        System.out.println("Restock P100 +20: "
                + inv.restock("P100", "20", "admin"));
        System.out.println("P100 stock: "
                + inv.findByCode("P100").getStock());
        System.out.println("Restock zero: "
                + inv.restock("P100", "0", "admin"));
        System.out.println("Search school: "
                + inv.search("school").size());
        System.out.println("Delete P100: " + inv.delete("P100"));
        System.out.println("Final count: " + inv.getProducts().size());
        System.out.println("Stock log lines: " + inv.getStockLog().size());
    }
}
