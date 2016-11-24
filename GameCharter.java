package com.dangerouslogic.gamecharter;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.swing.*;

public class GameCharter extends JPanel
implements ActionListener {
	
	JButton startB, endB, grabTimeB, addKeyB, addMaybeKeyB, addHighlightB, addReplayB;
	JTextField elapsedTimeTF, grabbedTimeTF, descriptionTF, lastEntryTF;
	
	LocalDateTime gameStart;
	String grabbedTime, description, lastEntry;
	
	Clipboard clipboard;
	
	FileWriter fw;
	
	MyTableModel myTable = new MyTableModel();
    JTable table = new JTable(myTable);
	
	static final String START = "start";
	static final String END = "end";
	static final String GRABTIME = "grabTime";
	static final String ADDKEY = "addKey";
	static final String ADDMAYBEKEY = "addMaybeKey";
	static final String ADDHIGHLIGHT = "addHighlight";
	static final String ADDREPLAY = "addReplay";
	
	public GameCharter() {
		
		super(new GridBagLayout());
		
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		try {
			fw = new FileWriter(new File("C:\\GameCharter files\\game_chart.tsv"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// GridBagLayout gridbag = (GridBagLayout)getLayout();
        GridBagConstraints c = new GridBagConstraints();
        
		startB = new JButton("Start");
		startB.setActionCommand(START);
		startB.addActionListener(this);

		endB = new JButton("End");
		endB.setActionCommand(END);
		endB.addActionListener(this);
		endB.setEnabled(false);

		grabTimeB = new JButton("Grab time");
		grabTimeB.setActionCommand(GRABTIME);
		grabTimeB.addActionListener(this);
		grabTimeB.setEnabled(false);

		addKeyB = new JButton("Key Play");
		addKeyB.setActionCommand(ADDKEY);
		addKeyB.addActionListener(this);
		addKeyB.setEnabled(false);

		addMaybeKeyB = new JButton("Maybe Key Play");
		addMaybeKeyB.setActionCommand(ADDMAYBEKEY);
		addMaybeKeyB.addActionListener(this);
		addMaybeKeyB.setEnabled(false);

		addHighlightB = new JButton("Highlight");
		addHighlightB.setActionCommand(ADDHIGHLIGHT);
		addHighlightB.addActionListener(this);
		addHighlightB.setEnabled(false);
		
		addReplayB = new JButton("Grab + \nReplay");
		addReplayB.setActionCommand(ADDREPLAY);
		addReplayB.addActionListener(this);
		addReplayB.setEnabled(false);
		
		elapsedTimeTF = new JTextField(8);
		elapsedTimeTF.setEditable(false);

		grabbedTimeTF = new JTextField(8);
		grabbedTimeTF.setEditable(false);
		
		descriptionTF = new JTextField(30);
		descriptionTF.setEditable(false);
		
		lastEntryTF = new JTextField(80);
		lastEntryTF.setEditable(false);
	
	    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    table.setFillsViewportHeight(true);

	    //Create the scroll pane and add the table to it.
	    // JScrollPane scrollPane = new JScrollPane(table);

	    //Add the scroll pane to this panel.
	    //add(scrollPane);

	    // TODO getRootPane().setDefaultButton(grabTimeB);

	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.weightx = 0.33;
	    c.insets = new Insets(0,0,10,0);  //bottom padding
	    //c.gridwidth = 1;
	    c.anchor = GridBagConstraints.PAGE_START;
	    add(startB, c);
	 
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 1;
	    c.gridy = 0;
	    c.weightx = 0.33;
	    c.insets = new Insets(0,0,10,0);  //bottom padding
	    c.anchor = GridBagConstraints.PAGE_START;
	    add(elapsedTimeTF, c);
	 
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 2;
	    c.gridy = 0;
	    c.insets = new Insets(0,0,10,0);  //bottom padding
	    c.weightx = 0.33;
	    c.anchor = GridBagConstraints.PAGE_START;
	    add(endB, c);
	 
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.ipady = 40;      //make this component tall
	    c.gridwidth = 2;
	    c.gridx = 0;
	    c.gridy = 2;
	    c.weightx = 0.33;
	    c.insets = new Insets(10,10,10,10);  //top padding
	    c.anchor = GridBagConstraints.CENTER;
	    add(grabTimeB, c);
	 
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.ipady = 0;       //reset to default
	//    c.weighty = 1.0;   //request any extra vertical space
	    //c.insets = new Insets(10,0,0,0);  //top padding
	    c.gridx = 2;       //aligned with button 2
	    //c.gridwidth = 2;   //2 columns wide
	    c.gridy = 2;       //third row
	    add(grabbedTimeTF, c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    //c.ipady = 40;      //make this component tall
	    c.weightx = 0.0;
	    c.gridwidth = 1;
	    c.gridx = 0;
	    c.gridy = 4;
	    add(descriptionTF, c);
	 
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 0;
	    c.gridy = 5;
	    c.gridwidth = 1;
	    c.anchor = GridBagConstraints.PAGE_END; //bottom of space
	    add(addKeyB, c);

	    c.gridx = 1;
	    add(addMaybeKeyB, c);
	    
	    c.gridx = 2;
	    add(addHighlightB, c);
	 
	    c.gridx = 3;
	    add(addReplayB, c);
	    
	    c.gridy = 6;
	    c.gridx = 0;
	    add (lastEntryTF, c);
	 
	    c.gridy = 7;
	    c.gridx = 0;
	    add (table, c);
	    
	 }
 
	public void actionPerformed(ActionEvent e) {
		if (START.equals(e.getActionCommand())) {
			gameStart = LocalDateTime.now();
			System.out.println(START + ": " + gameStart.toString());
			startB.setEnabled(false);
			endB.setEnabled(true);
			grabTimeB.setEnabled(true);
			
		}
		else if (END.equals(e.getActionCommand())) {
			System.out.println(END);
			try {
				fw.close ();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
			System.exit(0);
		}
		else if (GRABTIME.equals(e.getActionCommand())) {
			System.out.println(GRABTIME);
			updateGrabbedTime();
			
			grabbedTimeTF.setText(grabbedTime);
			descriptionTF.setEditable(true);
			descriptionTF.setText("");
			descriptionTF.requestFocus();
			addKeyB.setEnabled(true);
			addMaybeKeyB.setEnabled(true);
			addHighlightB.setEnabled(true);
			addReplayB.setEnabled(true);

			StringSelection selection = new StringSelection(grabbedTime + "xx\t");
			clipboard.setContents(selection, selection);
		}
		else if (ADDKEY.equals(e.getActionCommand())) {
			System.out.println(ADDKEY);
			postHighlight("K");
		}
		else if (ADDMAYBEKEY.equals(e.getActionCommand())) {
			System.out.println(ADDMAYBEKEY);
			postHighlight("k");
		}
		else if (ADDHIGHLIGHT.equals(e.getActionCommand())) {
			System.out.println(ADDHIGHLIGHT);
			postHighlight("");
		}
		else if (ADDREPLAY.equals(e.getActionCommand())) {
			System.out.println(ADDREPLAY);
			updateGrabbedTime();
			grabbedTimeTF.setText(grabbedTime);
			System.out.println (grabbedTime + "\t" + ' ' + "\t" + "  R");
			lastEntryTF.setText(grabbedTime + "\t" + ' ' + "\t" + "  R");
			try {
				fw.write (grabbedTime + "\t" + ' ' + "\t" + "  R");
				fw.write(System.lineSeparator());
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			myTable.addRowAtBottom(grabbedTime, " ", "R");
			
		}
	}
	
	private void getDescription() {
		try {
			description = descriptionTF.getText();
		}
		catch (Exception e) {
			description = "Exception raised; no description";
			System.out.println(e);
		}
		
	}
	
	private void postHighlight(String playType) {
		getDescription();
		System.out.println (grabbedTime + "\t" + playType + "\t" + description);
		lastEntryTF.setText(grabbedTime + "\t" + playType + "\t" + description);
		try {
			fw.write (grabbedTime + "\t" + playType + "\t" + description);
			fw.write(System.lineSeparator());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		myTable.addRowAtBottom(grabbedTime, playType, description);
	}
	
	private void updateGrabbedTime () {
		System.out.println("now: " + LocalDateTime.now());
		Duration grabbedTimeDuration = Duration.between(gameStart, LocalDateTime.now());
		
		grabbedTime = (grabbedTimeDuration.minusNanos(grabbedTimeDuration.getNano())).toString();
		grabbedTime = grabbedTime.substring(2);
		grabbedTime = grabbedTime.replace('H', 'h');
		grabbedTime = grabbedTime.replace('M', 'm');
		grabbedTime = grabbedTime.replace('S', 's');
	}
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("GameCharter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        JComponent newContentPane = new GameCharter();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
