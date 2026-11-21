/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

/**
 *
 * @author Admin
 */
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DevWindow {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventoryManager inv = new InventoryManager();
            inv.load();
            TransactionManager tm = new TransactionManager();
            tm.load();
            // ---- keep ONLY the two lines of YOUR screen ---
            SalesPanel panel = new SalesPanel();
            panel.setup(inv, tm, "admin");
            // -----------------------------------------------
            JFrame f = new JFrame("Test window");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(panel);
            f.setSize(900, 620);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
