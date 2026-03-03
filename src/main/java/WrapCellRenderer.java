import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class WrapCellRenderer extends JTextArea implements TableCellRenderer {

    public WrapCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value != null ? value.toString() : "");
        setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        return this;
    }
}
