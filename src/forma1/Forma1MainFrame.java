package forma1;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ItemListener;

import java.awt.event.ItemEvent;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.Color;

import java.awt.Font;

public class Forma1MainFrame {
	private DefaultTableModel tableModel;
	private List<Pilota> pilots = new ArrayList<Pilota>();
	String[] columnNames = { "Név", "Rajtszám", "Nemzetiség", "Zászló" };
	Object[][] tableData;
	private JFrame frame;
	private JTable table;
	private final JComboBox<String> comboBox = new JComboBox<String>();
	private JLabel lblRowCount;
	private JLabel lblSelectedNation;
	private JScrollPane scrollPane;
	private JLabel lblRowCountText;

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

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});

		createRows();
		scrollPane = new JScrollPane();

		frame.getContentPane().add(scrollPane);
		tableModel = new DefaultTableModel(tableData, columnNames);
		table = new JTable(tableModel) {
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
				return (column == 3) ? Icon.class : Object.class;
			}

		};

		ListSelectionModel select = table.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		select.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {

					StringBuilder row = new StringBuilder();
					row.append("Név: " + table.getModel().getValueAt(table.getSelectedRow(), 0).toString() + " ");
					row.append("rajtszám: " + table.getModel().getValueAt(table.getSelectedRow(), 1).toString() + " ");

					row.append("országkód: " + table.getModel().getValueAt(table.getSelectedRow(), 2).toString());

					JOptionPane.showMessageDialog(frame, row, "adatok", JOptionPane.PLAIN_MESSAGE, null);
				}
			}
		});

		firstColTextCenter();
		scrollPane.setViewportView(table);
		comboBox.setToolTipText("Nemzetiség");
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Mind", "GBR", "GER", "ITA" }));
		comboBox.setBounds(500, 36, 108, 30);
		frame.getContentPane().add(comboBox);

		JLabel lblNewLabel = new JLabel("A választott: ");
		lblNewLabel.setBounds(474, 11, 88, 14);
		frame.getContentPane().add(lblNewLabel);

		lblSelectedNation = new JLabel("");
		lblSelectedNation.setBounds(574, 11, 46, 14);
		frame.getContentPane().add(lblSelectedNation);

		lblRowCountText = new JLabel("A sorok száma: ");
		
		frame.getContentPane().add(lblRowCountText);

		lblRowCount = new JLabel("");
		
		frame.getContentPane().add(lblRowCount);

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {

					lblSelectedNation.setText(e.getItem().toString());
					createRowsByFilter(e.getItem().toString());
				}
			}
		});
		lblSelectedNation.setText("Mind");
		countTableRows();


		createMenu();
		setDetails();
	}

	/**
	 * set component details,like background, bound,etc
	 */
	private void setDetails() {
		frame.setBounds(100, 100, 885, 743);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(0, 153, 204));
		frame.setBackground(new Color(51, 153, 204));
		frame.setResizable(false);
		frame.setIconImage(new ImageIcon(this.getClass().getResource("/forma1_auto.png")).getImage());

		table.setToolTipText("Pilóták");
		table.setBackground(new Color(144, 238, 144));
		table.setSelectionBackground(new Color(0, 204, 255));
		table.setRowHeight(40);

		// enable sort
		table.setAutoCreateRowSorter(true);
		
		
		scrollPane.setBounds(10, 11, 429, 441);
		scrollPane.getViewport().setBackground(new Color(153, 255, 255));

		lblRowCount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRowCount.setForeground(new Color(255, 255, 255));
		lblRowCount.setBounds(391, 463, 59, 37);
		
		lblRowCountText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRowCountText.setForeground(new Color(255, 255, 255));
		lblRowCountText.setBounds(278, 463, 131, 30);
	}

	/**
	 * create menu just in another method
	 * 
	 */

	private void createMenu() {

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnTasks = new JMenu("Feladatok");
		menuBar.add(mnTasks);

		JMenuItem mntmNameStartsH = new JMenuItem("H betűs vezetéknevek");
		mntmNameStartsH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameStartWithH();
			}
		});
		mnTasks.add(mntmNameStartsH);

		JMenuItem mntmAllPeople = new JMenuItem("Mindenki");
		mntmAllPeople.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAllRows();
				createAllRow();
			}
		});
		mnTasks.add(mntmAllPeople);

		JMenuItem mntmExit = new JMenuItem("Kilépés");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				exit();
			}
		});

		JMenuItem mntmWriteCsv = new JMenuItem("csv írása");
		mntmWriteCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileHandler.writeCsvIfCount(pilots);
			}
		});
		mnTasks.add(mntmWriteCsv);
		mnTasks.add(mntmExit);

		JMenu mnNewMenu = new JMenu("Egyéb");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Adatok db-be");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBHandler.insertAllToDb(pilots);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

	}

	/**
	 * remove all rows from table and return count rows
	 * 
	 * @return integer
	 */

	public int removeAllRows() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		int rowCount = dm.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			dm.removeRow(i);
		}
		return dm.getRowCount();
	}

	/**
	 * create one row in to table with pilot fields
	 * 
	 * @param pilota
	 */
	private void createOneRow(Pilota pilota) {
		Object[] row = new Object[4];
		row[0] = pilota.getName();

		row[1] = pilota.getStartNum();
		row[2] = pilota.getNationCode();
		row[3] = new ImageIcon(this.getClass().getResource(selectFlagByNation(pilota.getNationCode())));
		tableModel.addRow(row);
	}

	/**
	 * create rows if initialize app
	 * 
	 */
	private void createRows() {

		for (int i = 0; i < pilots.size(); i++) {
			tableData[i][0] = pilots.get(i).getName();

			tableData[i][1] = pilots.get(i).getStartNum();
			tableData[i][2] = pilots.get(i).getNationCode();
			tableData[i][3] = new ImageIcon(
					this.getClass().getResource(selectFlagByNation(pilots.get(i).getNationCode())));
		}

	}

	/**
	 * create row if equal nationcode with param
	 * 
	 * @param nation
	 */

	private void createRowsByFilter(String nation) {
		removeAllRows();
		if (nation.equals("Mind")) {
			nation = "";
		}

		for (int i = 0; i < pilots.size(); i++) {
			if (pilots.get(i).getNationCode().contains(nation)) {
				createOneRow(pilots.get(i));
			}
		}
		countTableRows();
	}

	/**
	 * just show the number of rows in label
	 */
	private void countTableRows() {
		lblRowCount.setText(table.getRowCount() + "");
	}

	/**
	 * the table first column text align center
	 */
	private void firstColTextCenter() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

	}

	/**
	 * select image url by natiocode
	 * 
	 * @param nation
	 * @return String
	 */
	private String selectFlagByNation(String nation) {

		String url = "";
		switch (nation) {
		case "GBR":
			url = "/flag_brit.png";
			break;
		case "GER":
			url = "/flag_nemet.png";
			break;
		case "BEL":
			url = "/flag_belgium.png";
			break;
		case "AUS":
			url = "/flag_austria.png";
			break;
		case "FIN":
			url = "/flag_finnish.png";
			break;
		case "ITA":
			url = "/flag_olasz.png";
			break;
		default:
			url = "/";
		}
		;
		return url;
	}

	/**
	 * filter if firstname start with "h"
	 */
	private void nameStartWithH() {
		removeAllRows();
		for (int i = 0; i < pilots.size(); i++) {
			if (pilots.get(i).getName().split(" ")[1].startsWith("H")) {
				createOneRow(pilots.get(i));
			}
		}
	}

	/**
	 * Create all row to table
	 */
	private void createAllRow() {
		for (int i = 0; i < pilots.size(); i++) {

			createOneRow(pilots.get(i));

		}
	}

	/**
	 * exit method,before question
	 */

	private void exit() {
		if (JOptionPane.showConfirmDialog(frame, "Biztos kilép?", "Kilépés",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	// to tests

	public String getFrameTitle() {

		return frame.getTitle();
	}

	public int listSize() {
		return pilots.size();
	}
}
