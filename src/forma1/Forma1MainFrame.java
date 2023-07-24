package forma1;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Forma1MainFrame {
	private DefaultTableModel tableModel;
	List<Pilota> pilots = new ArrayList<Pilota>();
	String[] columnNames = { "Név", "Születédi idő", "Rajtszám", "Nemzetiség", "Zászló" };
	Object[][] tableData;
	private JFrame frame;
	private JTable table;
	private final JComboBox comboBox = new JComboBox();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Forma1MainFrame window = new Forma1MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Forma1MainFrame() {
		FileHandler.readCsv(pilots);

		tableData = new Object[pilots.size()][columnNames.length];
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Forma-1 pilóták");
		frame.setBounds(100, 100, 885, 743);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		createRows();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 818, 584);
		frame.getContentPane().add(scrollPane);
		tableModel = new DefaultTableModel(tableData, columnNames);
		table = new JTable(tableModel) {
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
				return (column == 4) ? Icon.class : Object.class;
			}
		};
		table.setToolTipText("Pilóták");

		firstColTextCenter();
		scrollPane.setViewportView(table);

		table.setRowHeight(40);

		comboBox.setToolTipText("Nemzetiség");
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Mind", "Brit", "Német", "Olasz"}));
		comboBox.setBounds(91, 644, 108, 30);
		frame.getContentPane().add(comboBox);

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					System.out.println(e.getItem().toString().toLowerCase());
					createRowsByFilter(e.getItem().toString());
				}
			}
		});

	}

	public int removeAllRows() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		int rowCount = dm.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			dm.removeRow(i);
		}
		return dm.getRowCount();
	}

	private void createRows() {
		for (int i = 0; i < pilots.size(); i++) {
			tableData[i][0] = pilots.get(i).getName();
			tableData[i][1] = pilots.get(i).getDateOfBirth();
			tableData[i][2] = pilots.get(i).getStartNum();
			tableData[i][3] = pilots.get(i).getNation();
			tableData[i][4] = new ImageIcon(this.getClass().getResource("/flag_brit.png"));
		}
	}

	private void createRowsByFilter(String nation) {
		removeAllRows();
		if(nation.equals("Mind")) {nation="";}
		Object[] row = new Object[5];
		for (int i = 0; i < pilots.size(); i++) {
			if (pilots.get(i).getNation().contains(nation.toLowerCase())) {
				row[0] = pilots.get(i).getName();
				row[1] = pilots.get(i).getDateOfBirth();
				row[2] = pilots.get(i).getStartNum();
				row[3] = pilots.get(i).getNation();
				row[4] = new ImageIcon(this.getClass().getResource("/flag_brit.png"));
				tableModel.addRow(row);
			}
		}
	}

	private void firstColTextCenter() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

	}
}
