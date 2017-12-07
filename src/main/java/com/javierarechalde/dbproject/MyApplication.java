package com.javierarechalde.dbproject;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MyApplication extends JFrame {

	  public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	        MyApplication app = new MyApplication();
	        app.setVisible(true);
	      }
	    });
	  }

	  private MyApplication() {
	    // create UI here: add buttons, actions etc
	  }
	}