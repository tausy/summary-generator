package com.sumgen.UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sumgen.DB.DBHandler;

@SuppressWarnings("serial")
public class ShowSummary extends JFrame implements ActionListener {

	String summary;
	JLabel heading, summaryLabel;
	JTextArea summaryArea;
	JScrollPane sbt;
	JButton back, quit, save;
	int frmWidth, frmHeight;
	
	public ShowSummary(String summary) {
		super("SumGen");
		this.summary = summary;
		summary = "\n"+this.summary;
		
		//frame width , height 
		frmWidth=500;
		frmHeight=500;
		setLayout(null);
		setSize(frmWidth, frmHeight);
		
		// All Labels here :)
		heading = new JLabel("SumGen - A Webpage Summary Generator");
		heading.setFont(new Font("Verdana", Font.BOLD, 18));
		
		summaryLabel = new JLabel("Summary");
		summaryArea = new JTextArea();
		summaryArea.setEditable(false);
		summaryArea.setLineWrap(true);
		summaryArea.setText(summary);
		summaryArea.setLineWrap(true);
		summaryArea.setWrapStyleWord(true);
		summaryArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		
		sbt = new JScrollPane(summaryArea);
		sbt.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);			
				
		//Buttons here :)
		back = new JButton("Back");
		quit = new JButton("Quit");
		save = new JButton("Save Summary");
		
		//All set Bounds Here :)
		heading.setBounds(20, 20, 600, 60);
		heading.setFont(new Font("Verdana",30, 20));
				
		summaryLabel.setBounds(60, frmHeight-420, 350, 20);
		sbt.setBounds(60, frmHeight-400, 375, 200);
				
		back.setBounds(30, frmHeight-150, 120, 30);
		quit.setBounds(190, frmHeight-150, 120, 30);
		save.setBounds(330, frmHeight-150, 120, 30);
		back.addActionListener(this);
		quit.addActionListener(this);
		save.addActionListener(this);
				
		add(heading);
		add(summaryLabel);
		add(sbt);
		add(back);
		add(quit);
		add(save);
		
		// Displaying Frame in Center of the Screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object  clicked = e.getSource();
		
		if(clicked == quit){
			int confirmed = JOptionPane.showConfirmDialog(null, 
			        "Are you sure you want to exit ?", "Exit Program Message Box",
			        JOptionPane.YES_NO_OPTION);
			    if (confirmed == JOptionPane.YES_OPTION) {
			      System.exit(DO_NOTHING_ON_CLOSE);
			    }
		}
		
		if(clicked == back) {
			dispose();
			new SumGenHome();
		}
		
		if(clicked == save) {
			boolean success = new DBHandler().saveSummaryToDB(summary);
			if(success) {
				//a pop-up box
                JOptionPane.showMessageDialog(null, "Summary saved successfully","Success",
                                    JOptionPane.INFORMATION_MESSAGE);
				}
			else {
				//a pop-up box
                JOptionPane.showMessageDialog(null, "Unable to save summary!","Failed!!",
                                    JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void main(String...s) {
		new ShowSummary("Hello!!");
	}
}
