import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class BoughtHeroTableModel extends AbstractTableModel {

	private List<Hero> boughtHeroes;

    public BoughtHeroTableModel() {
        boughtHeroes = new ArrayList<>();
    }

    public void setBoughtHeroes(List<Hero> boughtHeroes) {
        this.boughtHeroes = boughtHeroes;
    }

    @Override
    public int getRowCount() {
        return boughtHeroes.size();
    }

    @Override
    public int getColumnCount() {
        return 2; // Assuming you want to display hero name and price
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Hero hero = boughtHeroes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return hero.getName();
            case 1:
                return hero.getPrice();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Name";
            case 1:
                return "Price";
            default:
                return null;
        }
    }
}
