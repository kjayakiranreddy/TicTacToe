package com.isi.java.tictactoe;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.isi.java.tictactoe.view.TicTacToeWindow;

public class Main 
{
	public static void main(String[] args) 
	{
		setSystemLookAndFeel();
		new TicTacToeWindow();
	}
	private static void setSystemLookAndFeel() {
		try { 
			UIManager.setLookAndFeel(
			UIManager.getSystemLookAndFeelClassName()); 
		} 
		catch (ClassNotFoundException |
				InstantiationException | IllegalAccessException |
				UnsupportedLookAndFeelException e) {
			e.getClass();

		} 
	}
}
