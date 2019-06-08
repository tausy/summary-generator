package com.sumgen.UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ShowSummaryFromPastSummaries extends JFrame implements ActionListener {

	String summary;
	JLabel heading, summaryLabel;
	JTextArea summaryArea;
	JScrollPane sbt;
	JButton closeButton;
	int frmWidth, frmHeight;
	
	public ShowSummaryFromPastSummaries(String summary) {
		super("SumGen");
		this.summary = summary;
		summary = "\n"+this.summary;
		
		//frame width , height 
		frmWidth=500;
		frmHeight=400;
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
		closeButton = new JButton("Close");
		
		//All set Bounds Here :)
		heading.setBounds(20, 20, 600, 60);
		heading.setFont(new Font("Verdana",30, 20));
				
		summaryLabel.setBounds(60, 80, 350, 20);
		sbt.setBounds(60, 100, 375, 200);
				
		closeButton.setBounds(150, 320, 200, 30);
		closeButton.addActionListener(this);
				
		add(heading);
		add(summaryLabel);
		add(sbt);
		add(closeButton);
		
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
		
		if(clicked == closeButton){
			this.dispose();
		}
	}
	
	public static void main(String...s) {
		new ShowSummaryFromPastSummaries("Hello!!");
	}
}
