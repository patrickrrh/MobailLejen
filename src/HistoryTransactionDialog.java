import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoryTransactionDialog extends JDialog {
    private JTable historyTable;
    private DefaultTableModel historyTableModel;

    public void setUpTransactionDialog() {
    	setTitle("History Transaction");
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
    }
    
    public HistoryTransactionDialog(List<Hero> historyHeroes) {
        setUpTransactionDialog();

        // Create table and table model
        String[] columnNames = {"Name", "Price"};
        Object[][] data = new Object[historyHeroes.size()][2];
        for (int i = 0; i < historyHeroes.size(); i++) {
            Hero hero = historyHeroes.get(i);
            data[i][0] = hero.getName();
            data[i][1] = hero.getPrice();
        }
        historyTableModel = new DefaultTableModel(data, columnNames);
        historyTable = new JTable(historyTableModel);

        // Add table to a scroll pane
        JScrollPane tableScrollPane = new JScrollPane(historyTable);
        tableScrollPane.setPreferredSize(new Dimension(380, 250));
        add(tableScrollPane, BorderLayout.CENTER);
    }
}
