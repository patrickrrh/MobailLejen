import java.util.List;

import javax.swing.table.AbstractTableModel;

public class HeroTableModel extends AbstractTableModel {
        private List<Hero> heroes;
        private String[] columnNames = {"Name", "Price"};

        public HeroTableModel(List<Hero> heroes) {
            this.heroes = heroes;
        }

        public void setHeroes(List<Hero> heroes) {
            this.heroes = heroes;
        }

        @Override
        public int getRowCount() {
            return heroes.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Hero hero = heroes.get(rowIndex);

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
            return columnNames[column];
        }
    }