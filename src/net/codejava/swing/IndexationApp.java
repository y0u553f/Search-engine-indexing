package net.codejava.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.DropMode;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import java.awt.ComponentOrientation;

    public class IndexationApp extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IndexationApp frame = new IndexationApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IndexationApp() {
		
		Indexation i= new Indexation();
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 835, 748);
		
		Label label = new Label("Chemin de repertoire");
		label.setBounds(25, 55, 177, 24);
		label.setFont(new Font("Bahnschrift", Font.PLAIN, 18));
		label.setAlignment(Label.CENTER);
		
		Label label_1 = new Label("Zone De Recherche");
		label_1.setBounds(396, 100, 167, 24);
		label_1.setFont(new Font("Baskerville Old Face", Font.BOLD, 17));
		
		textField_1 = new JTextField();
		textField_1.setBounds(396, 130, 165, 22);
		textField_1.setColumns(10);
		
		Label res = new Label("");
		res.setBounds(396, 214, 155, 47);
		res.setFont(new Font("Baskerville Old Face", Font.BOLD, 24));
		
		JButton btnRechercher = new JButton("Rechercher");
	
		btnRechercher.setBounds(396,165, 165, 25);
		getContentPane().setLayout(null);
		getContentPane().add(label);
		getContentPane().add(label_1);
		getContentPane().add(textField_1);
		getContentPane().add(res);
		getContentPane().add(btnRechercher);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 100, 339, 515);
		getContentPane().add(scrollPane);
		
		/*table = new JTable(i.data,i.columnNames);
		scrollPane.setViewportView(table);*/
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(378, 275, 427, 340);
		getContentPane().add(scrollPane_1);
		
		JLabel affichage = new JLabel("");
		scrollPane_1.setViewportView(affichage);
		
		JTextPane txtpnsrccorpus = new JTextPane();
		txtpnsrccorpus.setText("./src/corpus");
		txtpnsrccorpus.setBounds(207, 55, 290, 24);
		getContentPane().add(txtpnsrccorpus);
		
		JButton btnIndexer = new JButton("Indexer");
		btnIndexer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				i.CreateDocuments(txtpnsrccorpus.getText());
				i.IndexerIdf();
				i.Indexer();
				
				table = new JTable(i.data,i.columnNames);
				scrollPane.setViewportView(table);

			}
		});
		btnIndexer.setBounds(511, 55, 167, 25);
		getContentPane().add(btnIndexer);
		
		JLabel lblTpIndexation = new JLabel("TP INDEXATION");
		lblTpIndexation.setHorizontalAlignment(SwingConstants.CENTER);
		lblTpIndexation.setFont(new Font("Bahnschrift", Font.BOLD, 20));
		lblTpIndexation.setBounds(299, 6, 318, 24);
		getContentPane().add(lblTpIndexation);
		
	btnRechercher.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				affichage.setText("");
				Integer nbrresult=new Integer(i.Recherche(textField_1.getText()));
				res.setText(nbrresult.toString()+" Doc Trouvé");
				affichage.setText(i.resultatAff);
				
				
			}
		});
	
		
	}
}
