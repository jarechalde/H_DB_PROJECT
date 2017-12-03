package com.javierarechalde.dbproject;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
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
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class Application extends JFrame{
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	private static final long serialVersionUID = 2985602890713332714L;
	
	private JTextField roomidTextField;
	private JTextField rcapTextField;
	private JTextArea rtypeTextField;
	
	private DefaultListModel<Room> roomsListModel;
	private JList<Room> roomsList;
	
	private Action refreshAction;
	private Action newAction;
	private Action saveAction;
	private Action deleteAction;
	
	private Room selected;
	
	public Application() {
		initActions();
		initComponents();
		refreshData();
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
		roomsListModel.removeAllElements();
		final SwingWorker<Void, Room> worker = new SwingWorker<Void, Room>() {
			@Override
			protected Void doInBackground() throws Exception{
				final List<Room> rooms = RoomsHelper.getInstance().getRooms();
				for(final Room room: rooms) {
					publish(room);
				}
				return null;
			}
			
			@Override
			protected void process(final List<Room> chunks) {
				for(final Room room: chunks) {
					roomsListModel.addElement(room);
				}
			}
		};
		
		worker.execute();
		
	}
	
	private void createNew() {
		Room room =  new Room();
		room.setrid(000000);
		room.setrcap(00);
		room.setrtype("New Room Type");
		setSelectedRoom(room);
	}
	
	private void save() {
		if (selected!=null) {
		selected.setrid(Integer.parseInt(roomidTextField.getText()));
		selected.setrcap(Integer.parseInt(rcapTextField.getText()));
		selected.setrtype(rtypeTextField.getText());
		try{
			selected.save();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Failed to save the selected contact", "Save", JOptionPane.WARNING_MESSAGE);
		} finally {
			refreshData();
		}
		}

	}
	
	private void delete() {
		if (selected!=null) {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete " + selected +"?", "Delete", JOptionPane.YES_NO_OPTION)) {
			try {
				selected.delete();
			} catch (final SQLException e) {
				JOptionPane.showMessageDialog(this, "Failed to delete the selected room", "Delete", JOptionPane.WARNING_MESSAGE);;
			} finally {
				setSelectedRoom(null);
				refreshData();
				}
			}
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
		roomsListModel = new DefaultListModel<>();
		roomsList = new JList<>(roomsListModel);
		roomsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					Room selected = roomsList.getSelectedValue();
					setSelectedRoom(selected);
				}
			}	
			
		});
		
		return new JScrollPane(roomsList);
	}
	
	private void setSelectedRoom(Room room) {
		this.selected = room;
		if(room==null) {
			roomidTextField.setText("");
			rcapTextField.setText("");
			rtypeTextField.setText("");
		} else {
			roomidTextField.setText(String.valueOf(room.getrid()));
			rcapTextField.setText( String.valueOf(room.getrcap()));
			rtypeTextField.setText( String.valueOf(room.getrtype()));
		}
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
