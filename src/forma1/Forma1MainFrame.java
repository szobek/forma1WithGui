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
import java.net.URL;

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
		frame.setBounds(100, 100, 885, 743);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		createRows();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 429, 441);
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
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setToolTipText("Pilóták");

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

		table.setRowHeight(40);

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

		JLabel lblRowCountText = new JLabel("A sorok száma: ");
		lblRowCountText.setBounds(278, 463, 131, 30);
		frame.getContentPane().add(lblRowCountText);

		lblRowCount = new JLabel("");
		lblRowCount.setBounds(371, 463, 59, 37);
		frame.getContentPane().add(lblRowCount);

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					System.out.println(e.getItem().toString()+"//////////****************");
					lblSelectedNation.setText(e.getItem().toString());
					createRowsByFilter(e.getItem().toString());
				}
			}
		});
		lblSelectedNation.setText("Mind");
		countTableRows();

		// enable sort
		table.setAutoCreateRowSorter(true);

		createMenu();
		frame.setResizable(false);
		frame.setIconImage(new ImageIcon(this.getClass().getResource("/forma1_auto.png")).getImage());
		
	}

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

	public int removeAllRows() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		int rowCount = dm.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			dm.removeRow(i);
		}
		return dm.getRowCount();
	}

	private void createOneRow(Pilota pilota) {
		Object[] row = new Object[4];
		row[0] = pilota.getName();

		row[1] = pilota.getStartNum();
		row[2] = pilota.getNationCode();
		row[3] = new ImageIcon(this.getClass().getResource(selectFlagByNation(pilota.getNationCode())));
		tableModel.addRow(row);
	}

	private void createRows() {

		for (int i = 0; i < pilots.size(); i++) {
			tableData[i][0] = pilots.get(i).getName();

			tableData[i][1] = pilots.get(i).getStartNum();
			tableData[i][2] = pilots.get(i).getNationCode();
			tableData[i][3] = new ImageIcon(
					this.getClass().getResource(selectFlagByNation(pilots.get(i).getNationCode())));
		}

	}

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

	private void countTableRows() {
		lblRowCount.setText(table.getRowCount() + "");
	}

	private void firstColTextCenter() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

	}

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

	private void nameStartWithH() {
		removeAllRows();
		for (int i = 0; i < pilots.size(); i++) {
			if (pilots.get(i).getName().split(" ")[1].startsWith("H")) {
				createOneRow(pilots.get(i));
			}
		}
	}

	private void createAllRow() {
		for (int i = 0; i < pilots.size(); i++) {

			createOneRow(pilots.get(i));

		}
	}
	
	private void exit() {
		if(JOptionPane.showConfirmDialog(frame, "Biztos kilép?", "Kilépés", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
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
