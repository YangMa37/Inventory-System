/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

import java.math.BigDecimal;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Admin
 */
public class NumberRenderer extends DefaultTableCellRenderer {

    public NumberRenderer() {
        setHorizontalAlignment(RIGHT);
    }

    @Override
    protected void setValue(Object value) {
        if (value instanceof BigDecimal) {
            setText(((BigDecimal) value).toPlainString());
        } else {
            super.setValue(value);
        }
    }
}
