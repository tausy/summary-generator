package com.sumgen.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.sumgen.DB.DBHandler;

@SuppressWarnings("serial")

public class LoadSummaries extends JFrame implements ActionListener {

	Connection con;
	Statement stmt;

	PreparedStatement preStatement, updatePreStmt;
	JLabel title;
	JButton exitButton, backButton;
	JPanel panel;
	JTable table;
	DefaultTableModel model;
	JScrollPane scrollpane;

	public static void main(String...s) {
		new LoadSummaries();
	}
	
	public LoadSummaries() {
		// TODO Auto-generated constructor stub

		super("SumGen");
		setSize(600, 500);
		setLayout(null);

		// Defining Labels

		title = new JLabel("SumGen - Past Summaries");
		title.setFont(new Font("Verdana", Font.BOLD, 18));
		title.setBounds(200, 7, 600, 60);

		// fixing all Label,TextField,RadioButton
		add(title);

		// Defining Exit Button
		exitButton = new JButton("Exit");
		exitButton.setBounds(300, 400, 130, 25);
		exitButton.addActionListener(this);

		// Defining Exit Button
		backButton = new JButton("Back");
		backButton.setBounds(150, 400, 130, 25);
		backButton.addActionListener(this);
		
		// fixing all Buttons
		add(exitButton);
		add(backButton);
		
		// Defining Panel
		panel = new JPanel();
		panel.setLayout(new GridLayout());
		panel.setBounds(50, 80, 500, 300);
		panel.setBorder(BorderFactory.createDashedBorder(Color.blue));

		add(panel);

		// Defining Model for table
		model = new DefaultTableModel();
		
		
		// Adding object of DefaultTableModel into JTable
		table = new JTable(model);
		//table.setEnabled(false);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent event) {
	            int selectedRow = table.getSelectedRow();
	             @SuppressWarnings("unchecked")
				Vector<String> row = (Vector<String>)model.getDataVector().elementAt(selectedRow);
	            String summary = row.elementAt(1);
	            new ShowSummaryFromPastSummaries(summary);
	        }
	    });
		
		// Fixing Columns move
		table.getTableHeader().setReorderingAllowed(false);

		// Defining Column Names on model
		model.addColumn("ID");
		model.addColumn("Summary");
		model.addColumn("Generation Time");

		// Enable Scrolling on table
		scrollpane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(scrollpane);
		
		//add data rows to panel
		DBHandler dbHandler = new DBHandler();
		ArrayList<String> data = dbHandler.fetchSummariesFromDB();
		Object[] row=null;
		
		for(String dataRow : data) {
		row = dataRow.split("\\|");
        // Adding records in table model 
        model.addRow(row);
		}
		
        
		// Displaying Frame in Center of the Screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2,
				dim.height / 2 - this.getSize().height / 2);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Object clicked = e.getSource();
		if(clicked == exitButton) {
			int confirmed = JOptionPane.showConfirmDialog(null, 
			        "Are you sure you want to exit ?", "Exit Program Message Box",
			        JOptionPane.YES_NO_OPTION);
			    if (confirmed == JOptionPane.YES_OPTION) {
			      System.exit(0);
			    }
		}
		
		if(clicked == backButton) {
			new SumGenHome();
			dispose();
		}
	}
}
