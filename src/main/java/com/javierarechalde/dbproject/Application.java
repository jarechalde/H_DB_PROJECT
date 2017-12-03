package com.javierarechalde.dbproject;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class Application extends JFrame{

	private static final long serialVersionUID = 2985602890713332714L;
	
	private JTextField roomidTextField;
	private JTextField rcapTextField;
	private JTextArea rtypeTextField;
	
	
	private JList<Room> roomsList;
	
	private Action refreshAction;
	private Action newAction;
	private Action saveAction;
	private Action deleteAction;
	
	public Application() {
		initActions();
		initComponents();
	}
	
	private JToolBar createToolBar() {
		final JToolBar toolBar = new JToolBar();
		toolBar.add(refreshAction);
		toolBar.addSeparator();
		toolBar.add(newAction);
		toolBar.addSeparator();
		toolBar.add(saveAction);
		toolBar.addSeparator();
		toolBar.add(deleteAction);
		
		return toolBar;
	}
	
	private ImageIcon load(final String name) {
		return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
	}
	
	private void refreshData() {
		
	}
	
	private void createNew() {
		
	}
	
	private void save() {
		
	}
	
	private void delete() {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete?","Delete", JOptionPane.YES_NO_OPTION)) {
			//Delete here
		}
	}
	
	private void initActions() {
		
		refreshAction = new AbstractAction("Refresh", load("Refresh")) {
			private static final long serialVersionUID = -3876237444679320139L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				refreshData();
				
			}
		};
		
		newAction = new AbstractAction("New", load("New")) {
			private static final long serialVersionUID = -605237333970985709L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				createNew();
				
			}
		};
		
		saveAction = new AbstractAction("Save", load("Save")) {
			private static final long serialVersionUID = 2918460914014829628L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				save();
				
			}
		};
		
		deleteAction = new AbstractAction("Delete", load("Delete")) {
			private static final long serialVersionUID = 9008483889368221463L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				delete();
				
			}
		};
	}

	private void initComponents() {
		
		add(createToolBar(), BorderLayout.PAGE_END);
		add(createListPane(), BorderLayout.WEST);
		add(createEditor(), BorderLayout.CENTER);
		
	}
	
	private JComponent createListPane() {
		roomsList = new JList<>();
		return new JScrollPane(roomsList);
	}
	
	private JComponent createEditor() {
		final JPanel panel =  new JPanel(new GridBagLayout());
		
		//Room ID
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		panel.add(new JLabel("Room ID"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		roomidTextField = new JTextField();
		//roomidTextField.setEditable(false);
		panel.add(roomidTextField, constraints);
		
		//Room Capacity
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		panel.add(new JLabel("Room Capacity"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		rcapTextField = new JTextField();
		panel.add(rcapTextField, constraints);
		
		//Room Type
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		panel.add(new JLabel("Room Type"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		rtypeTextField = new JTextArea();
		panel.add(new JScrollPane(rtypeTextField), constraints);
		
		return panel;
	}
	
}
