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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.TableColumn;

public class GameCharter extends JPanel
implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7020594083809321560L;
	
	private static final String file_dir = "C:\\GameCharter files\\";
	
	JButton startB, endB, grabTimeB, addKeyB, addMaybeKeyB, addHighlightB, addReplayB, oopsB, backupB;
	JTextField elapsedTimeTF, grabbedTimeTF, descriptionTF, lastEntryTF, filenameTF;
	JPanel buttonPanel;
	
	ArrayList <String> entries = new ArrayList();
	
	LocalDateTime gameStart;
	String grabbedTime, description, lastEntry, filename;
	
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
	static final String OOPS = "oops";
	static final String BACKUP = "backup";
	
	public GameCharter() {
		
		super(new GridBagLayout());
		
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		buttonPanel = new JPanel();
		
		// GridBagLayout gridbag = (GridBagLayout)getLayout();
        GridBagConstraints c = new GridBagConstraints();
        
		startB = new JButton("Start");
		startB.setActionCommand(START);
		startB.addActionListener(this);

		endB = new JButton("End");
		endB.setActionCommand(END);
		endB.addActionListener(this);
		endB.setEnabled(false);

		backupB = new JButton("Backup");
		backupB.setActionCommand(BACKUP);
		backupB.addActionListener(this);
		backupB.setEnabled(false);

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
		
		oopsB = new JButton("Oops");
		oopsB.setActionCommand(OOPS);
		oopsB.addActionListener(this);
		oopsB.setEnabled(false);
		
		filenameTF = new JTextField(20);
		filenameTF.setEditable(true);
		
		elapsedTimeTF = new JTextField(8);
		elapsedTimeTF.setEditable(false);

		grabbedTimeTF = new JTextField(8);
		grabbedTimeTF.setEditable(false);
		
		descriptionTF = new JTextField(30);
		descriptionTF.setEditable(false);
		
		lastEntryTF = new JTextField(40);
		lastEntryTF.setEditable(false);
	
	    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    table.setFillsViewportHeight(true);

	    //Create the scroll pane and add the table to it.
	    // JScrollPane scrollPane = new JScrollPane(table);

	    //Add the scroll pane to this panel.
	    //add(scrollPane);

	    // TODO getRootPane().setDefaultButton(grabTimeB);
 
	    // TODO button to save interim file with timestamp
	    // TODO refactor to use maybe an ArrayList and only write file at end or when interim button pressed
	    // TODO Enter key on description saves as regular highlight
/*	    
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
*/
	    
//	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridx = 0;
	    c.gridy = 0;
//	    c.weightx = 0.33;
//	    c.insets = new Insets(0,0,10,0);  //bottom padding
//	    //c.gridwidth = 1;
//	    c.anchor = GridBagConstraints.PAGE_START;
	    add(filenameTF, c);

	    c.gridx = 0;
	    c.gridy = 1;
	    add(startB, c);
	 
//	    c.fill = GridBagConstraints.HORIZONTAL;
//	    c.weightx = 0.5;
	    c.gridx = 1;
	    c.gridy = 0;
//	    c.weightx = 0.33;
//	    c.insets = new Insets(0,0,10,0);  //bottom padding
//	    c.anchor = GridBagConstraints.PAGE_START;
	    add(elapsedTimeTF, c);
	 
	    c.gridx = 2;
	    c.gridy = 0;
	    add(backupB, c);
	    
//	    c.fill = GridBagConstraints.HORIZONTAL;
//	    c.weightx = 0.5;
	    c.gridx = 3;
	    c.gridy = 0;
//	    c.insets = new Insets(0,0,10,0);  //bottom padding
//	    c.weightx = 0.33;
//	    c.anchor = GridBagConstraints.PAGE_START;
	    add(endB, c);
	 
//	    c.fill = GridBagConstraints.HORIZONTAL;
//	    c.ipady = 40;      //make this component tall
//	    c.gridwidth = 2;
	    c.gridx = 0;
	    c.gridy = 2;
//	    c.weightx = 0.33;
//	    c.insets = new Insets(10,10,10,10);  //top padding
//	    c.anchor = GridBagConstraints.CENTER;
	    add(grabTimeB, c);
	 
//	    c.fill = GridBagConstraints.HORIZONTAL;
//	    c.ipady = 0;       //reset to default
//	//    c.weighty = 1.0;   //request any extra vertical space
//	    //c.insets = new Insets(10,0,0,0);  //top padding
	    c.gridx = 1;       //aligned with button 2
//	    //c.gridwidth = 2;   //2 columns wide
	    c.gridy = 2;       //third row
	    add(grabbedTimeTF, c);
	    
//	    c.fill = GridBagConstraints.HORIZONTAL;
//	    //c.ipady = 40;      //make this component tall
//	    c.weightx = 0.0;
//	    c.gridwidth = 1;
	    c.gridx = 0;
	    c.gridy = 4;
	    add(descriptionTF, c);
	 
//	    c.fill = GridBagConstraints.HORIZONTAL;
//	    c.weightx = 0.5;
	    c.gridx = 0;
	    c.gridy = 5;
//	    c.gridwidth = 1;
//	    c.anchor = GridBagConstraints.PAGE_END; //bottom of space

	    buttonPanel.add(addKeyB);

//	    c.gridx = 1;
	    buttonPanel.add(addMaybeKeyB);
	    
//	    c.gridx = 2;
	    buttonPanel.add(addHighlightB);
	 
//	    c.gridx = 3;
	    buttonPanel.add(addReplayB);
	    
	    buttonPanel.add(oopsB);
	    
	    add(buttonPanel, c);
	    
	    c.gridy = 6;
	    c.gridx = 0;
	    c.weightx = 2.0;
	    add (lastEntryTF, c);
	 

	    //TableColumn col;
	    table.getColumnModel().getColumn(0).setPreferredWidth(60);
	    table.getColumnModel().getColumn(1).setPreferredWidth(5);
	    table.getColumnModel().getColumn(2).setPreferredWidth(350);
	    
	    c.gridy = 7;
	    c.gridx = 0;
	    add (table, c);

	}
 
	public void actionPerformed(ActionEvent e) {
		if (START.equals(e.getActionCommand())) {
			gameStart = LocalDateTime.now();
			System.out.println(START + ": " + gameStart.toString());
			startB.setEnabled(false);
			backupB.setEnabled(true);
			endB.setEnabled(true);
			grabTimeB.setEnabled(true);
			filenameTF.setEnabled(false);
			
			try { filename = filenameTF.getText(); }
			catch (NullPointerException npe) {
				System.out.println("No filename given; using game_chart");
				filename = "game_chart";
			}
			
			if ((filename == null) || (filename.equals(""))) { filename = "game_chart"; }
			
			System.out.println("filename>" + filename + "<");
			
			try {
				fw = new FileWriter(new File(file_dir + filename + ".csv"));
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
		else if (BACKUP.equals(e.getActionCommand())) {
			System.out.println(BACKUP);
			produceBackupFile();
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
			oopsB.setEnabled(true);

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
			String play = grabbedTime + "\t" + ' ' + "\t" + "  R";
			System.out.println (play);
			lastEntryTF.setText(play);
			entries.add(play);
			try {
				fw.write (play);
				fw.write(System.lineSeparator());
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			myTable.addRowAtBottom(grabbedTime, " ", "R");
			
		}
		else if (OOPS.equals(e.getActionCommand())) {
			System.out.println(OOPS);
			System.out.println("\t" + ' ' + "\t" + "disregard previous");
			try {
				fw.write ("\t" + ' ' + "\t" + "disregard previous");
				entries.add("\t" + ' ' + "\t" + "disregard previous");
				fw.write(System.lineSeparator());
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			myTable.addRowAtBottom(" ", " ", "disregard previous");
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
	
	private void produceBackupFile() {
		FileWriter fw2;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MMdd_HHmmss");
			String backupFileNameSuffix = "_backup_" + LocalDateTime.now().format(formatter);
			fw2 = new FileWriter(new File(file_dir + filename +	backupFileNameSuffix + ".csv"));
		
			for(String entry : entries) {
				try {
					fw2.write (entry);
					fw2.write(System.lineSeparator());
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			fw2.close ();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void postHighlight(String playType) {
		getDescription();
		String play = new String(grabbedTime + "\t" + playType + "\t" + description);
		System.out.println (play);
		lastEntryTF.setText(play);
		entries.add(play);
		try {
			fw.write (play);
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
