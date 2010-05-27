package jge3d.GUI;

import java.awt.Component;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;

public class EntityTable extends JTable{
	private static final long serialVersionUID = 1L;
	public static EntityTable uniqueInstance= new EntityTable();
    
    public static EntityTable getInstance()
    {
    	return uniqueInstance;
    }
    
    public EntityTable() {
    	initComponent();
    }
    
    class InteractiveRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		
		protected int interactiveColumn;

        public InteractiveRenderer(int interactiveColumn) {
            this.interactiveColumn = interactiveColumn;
        }

        public Component getTableCellRendererComponent(JTable table,
           Object value, boolean isSelected, boolean hasFocus, int row,
           int column)
        {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == interactiveColumn && hasFocus) {
                if ((EntityTableModel.getInstance().getRowCount() - 1) == row)
                {
                	//EntityTable.this.tableModel.addEmptyRow();
                }

                highlightLastRow(row);
            }

            return c;
        }
    }
    
    public void initComponent() {
        EntityTableModel tableModel = EntityTableModel.getInstance();
        tableModel.addTableModelListener(new EntityTable.InteractiveTableModelListener());
        this.setModel(tableModel);
        this.setSurrendersFocusOnKeystroke(true);
    }

    public void highlightLastRow(int row) {
        int lastrow = EntityTableModel.getInstance().getRowCount();
        if (row == lastrow - 1) {
            this.setRowSelectionInterval(lastrow - 1, lastrow - 1);
        } else {
        	this.setRowSelectionInterval(row + 1, row + 1);
        }

        this.setColumnSelectionInterval(0, 0);
    }

    public class InteractiveTableModelListener implements TableModelListener {
        public void tableChanged(TableModelEvent evt) {
        	revalidate();
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                setColumnSelectionInterval(column + 1, column + 1);
                setRowSelectionInterval(row, row);
            }
        }
    }
}
