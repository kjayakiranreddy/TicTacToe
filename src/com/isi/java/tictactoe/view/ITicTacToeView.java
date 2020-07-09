package com.isi.java.tictactoe.view;

public interface ITicTacToeView 
{
	void setStatus(String s);
	void setButtonsEnabled(boolean enabled);
	void computerTurn();
	void gameOver(String s);
	void checkState();
	void click(int i,int j);
	void play();
	void showButton();
}
