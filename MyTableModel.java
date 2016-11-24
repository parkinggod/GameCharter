package com.dangerouslogic.gamecharter;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
	
	static boolean DEBUG = true;
	
	private String[] columnNames = {"Time",
                                    "Type",
                                    "Description"};
	        private Object[][] data = {
	    		    {" "," "," "},
	    		    {" "," "," "},
	    		    {" "," "," "},
	    		    {" "," "," "},
	    		    {" "," "," "}
	        };

	        public int getColumnCount() {
	            return columnNames.length;
	        }

	        public int getRowCount() {
	            return data.length;
	        }

	        public String getColumnName(int col) {
	            return columnNames[col];
	        }

	        public Object getValueAt(int row, int col) {
	            return data[row][col];
	        }

	        public void addRowAtBottom(String c0, String c1, String c2) {
	        	for (int i = 0; i < 4; i++) {
	        		setValueAt(getValueAt(i+1,0), i, 0);
	        		setValueAt(getValueAt(i+1,1), i, 1);
	        		setValueAt(getValueAt(i+1,2), i, 2);
	        	}
	        	setValueAt(c0, 4, 0);
	        	setValueAt(c1, 4, 1);
	        	setValueAt(c2, 4, 2);
	        }

	        /*
	         * JTable uses this method to determine the default renderer/
	         * editor for each cell.  If we didn't implement this method,
	         * then the last column would contain text ("true"/"false"),
	         * rather than a check box.
	         */
	        public Class getColumnClass(int c) {
	            return getValueAt(0, c).getClass();
	        }

	        /*
	         * Don't need to implement this method unless your table's
	         * editable.
	         */
	        public boolean isCellEditable(int row, int col) {
	          return false;
	        }

	        /*
	         * Don't need to implement this method unless your table's
	         * data can change.
	         */
	        public void setValueAt(Object value, int row, int col) {
	            if (DEBUG) {
	                System.out.println("Setting value at " + row + "," + col
	                                   + " to " + value
	                                   + " (an instance of "
	                                   + value.getClass() + ")");
	            }

	            data[row][col] = value;
	            fireTableCellUpdated(row, col);

	            if (DEBUG) {
	                System.out.println("New value of data:");
	                printDebugData();
	            }
	        }

	        private void printDebugData() {
	            int numRows = getRowCount();
	            int numCols = getColumnCount();

	            for (int i=0; i < numRows; i++) {
	                System.out.print("    row " + i + ":");
	                for (int j=0; j < numCols; j++) {
	                    System.out.print("  " + data[i][j]);
	                }
	                System.out.println();
	            }
	            System.out.println("--------------------------");
	        }
	    }


