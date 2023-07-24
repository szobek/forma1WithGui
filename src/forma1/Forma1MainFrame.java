package forma1;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Forma1MainFrame {
private DefaultTableModel tableModel;
List<Pilota> pilots = new ArrayList<Pilota>();

	private JFrame frame;
	private JTable table;
	private JTable table_1;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 885, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		FileHandler.readCsv(pilots);
		String[] columnNames = {"Név","Születédi idő","Rajtszám","Nemzetiség","Zászló"};
		Object[][] tableData = new Object[pilots.size()][columnNames.length];
		for(int i =0;i<pilots.size();i++) {
			tableData[i][0] = pilots.get(i).getName();;
			tableData[i][1] = pilots.get(i).getDateOfBirth();
			tableData[i][2] = pilots.get(i).getStartNum();
			tableData[i][3] = pilots.get(i).getNation();
			tableData[i][4] = new ImageIcon(this.getClass().getResource("/flag_brit.png"));
		}
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 818, 221);
		frame.getContentPane().add(scrollPane);
		tableModel=new DefaultTableModel(tableData,columnNames);
		table_1 = new JTable(tableModel) {
			 public Class getColumnClass(int column) {
			        return (column == 4) ? Icon.class : Object.class;
			      }
		};
		scrollPane.setViewportView(table_1);
		
		table_1.setRowHeight(40);

		
	     
		
	}
	
	private void createRow(Pilota pilota) {
		Object[] o = new Object[4];
		o[0] = pilota.getName();
		o[1] = "";
		o[2] = new ImageIcon("https://th.bing.com/th?id=OSK.67c1b2e26b1fbba303979ae68213b79e&w=148&h=148&c=7&o=6&pid=SANGAM");
		if(pilota.getStartNum() != null) {o[3] = String.valueOf(pilota.getStartNum());} else {o[3] = "";}
		
		tableModel.addRow(o);
	}
}
