package com.isi.java.tictactoe.model;

public interface ITicTacToeModel 
{
	int getButtonValue(int i, int j);
	void setButtonValue(int i,int j,int token);
	int []nextWinningMove(int token);
	int []nextMove(int token);
	boolean isWin(int token);
}
