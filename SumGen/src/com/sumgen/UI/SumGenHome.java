package com.sumgen.UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sumgen.controller.Controller;

@SuppressWarnings("serial")
public class SumGenHome extends JFrame implements ActionListener  {
 
	JLabel heading, urlLabel, numLinesLabel;
	JButton submit, quit, load;
	JTextField urlField;
	JComboBox<Integer> numLinesSelector;
	int frmWidth,frmHeight;
	boolean flag=false;
	int[] items = {5, 8, 12};
	
	public SumGenHome()
	{
		super("SumGen");
		frmWidth=500;
		frmHeight=350;
		setSize(frmWidth, frmHeight);
		setLayout(null);
		
		// All Labels here :)
		heading = new JLabel("SumGen - A Webpage Summary Generator");
		heading.setFont(new Font("Verdana", Font.BOLD, 18));
		
		
		urlLabel = new JLabel("Url:");
		urlField = new JTextField();
		
		numLinesLabel = new JLabel("Summary Lines:");
		numLinesSelector = new JComboBox<Integer>();
		numLinesSelector.addItem(items[0]);
		numLinesSelector.addItem(items[1]);
		numLinesSelector.addItem(items[2]);
		
		//Buttons here :)
		submit = new JButton("Summarize");
		quit = new JButton("Quit");
		load = new JButton("Load Previous Summaries");
		
		//All set Bounds Here :)
		heading.setBounds(25, 20, 600, 60);
		//heading.setFont(new Font("Verdana",30, 20));
		
		urlLabel.setBounds(60, 100, 350, 30);
		urlField.setBounds(85, 100, 350, 30);
		
		numLinesLabel.setBounds(140, 150, 150, 30);
		numLinesSelector.setBounds(240, 150, 150, 30);
		
		submit.setBounds(120, 200, 120, 30);
		quit.setBounds(280, 200, 120, 30);
		submit.addActionListener(this);
		quit.addActionListener(this);
		load.setBounds(90, 250, 350, 30);
		load.addActionListener(this);
		
		add(heading);
		add(urlLabel);
		add(urlField);
		add(numLinesLabel);
		add(numLinesSelector);
		add(submit);
		add(quit);
		add(load);
		
		// Displaying Frame in Center of the Screen
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setResizable(false);
				setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		
		Object  clicked =ev.getSource();
		String summary = null;
		if(clicked == submit){
			String url = urlField.getText();
			int numLines = items[numLinesSelector.getSelectedIndex()];
			
			Controller controller = new Controller();
			try {
				summary = controller.getSummary(url, numLines);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new ShowSummary(summary);
			this.dispose();
		}
		
		if(clicked == quit) {
			int confirmed = JOptionPane.showConfirmDialog(null, 
			        "Are you sure you want to exit ?", "Exit Program Message Box",
			        JOptionPane.YES_NO_OPTION);
			    if (confirmed == JOptionPane.YES_OPTION) {
			      dispose();
			    }
		}
		
		if(clicked == load) {
			new LoadSummaries();
		}
			
	}
	
		/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	 javax.swing.SwingUtilities.invokeLater(new Runnable() {
	  //          public void run() {
	            	new SumGenHome();
	    //        }
	      //  });
			
	}
	
	

}
