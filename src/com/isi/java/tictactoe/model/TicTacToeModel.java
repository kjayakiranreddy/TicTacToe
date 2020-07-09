package com.isi.java.tictactoe.model;

public class TicTacToeModel implements ITicTacToeModel
{
	// the board 
	private int boardGame[][];
	// empty 
	public static final int EMPTY = 0;
	// player one 
	public static final int ONE = 1;
	// player two or computer 
	public static final int TWO = 2;

	public TicTacToeModel() {
		boardGame = new int[3][3];
	}

	// get the board value for position (i,j) 
	public int getButtonValue(int i,int j) {
		if(i < 0 || i >= 3) return EMPTY;
		if(j < 0 || j >= 3) return EMPTY;
		return boardGame[i][j];
	}

	// set the board value for position (i,j) 
	public void setButtonValue(int i,int j,int playerType) {
		if(i < 0 || i >= 3)
			return;
		if(j < 0 || j >= 3) 
			return;
		boardGame[i][j] = playerType;
	}

	// calculate the winning move 
	public int []nextWinningMove(int playerType) {

		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(getButtonValue(i, j)==EMPTY) {
					boardGame[i][j] = playerType;
					boolean win = isWin(playerType);
					boardGame[i][j] = EMPTY;
					if(win) return new int[]{i,j};
				}

		return null;
	}

	public int exchange(int token) {
		return token==ONE ? TWO : ONE;
	}

	// calculate the best move
	public int []nextMove(int playerType) {

		// position in the center of board
		if(getButtonValue(1, 1)==EMPTY) return new int[]{1,1};

		// if we can move on the next turn 
		int winMove[] = nextWinningMove(playerType);
		if(winMove!=null) return winMove;

		// choose the move that prevent enemy to win 
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(getButtonValue(i, j)==EMPTY)
				{
					boardGame[i][j] = playerType;
					boolean ok = nextWinningMove(exchange(playerType)) == null;
					boardGame[i][j] = EMPTY;
					if(ok) return new int[]{i,j};
				}

		// choose available move 
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(getButtonValue(i, j)==EMPTY)
					return new int[]{i,j};

		// no move is available 
		return null;
	}
	// determine if current token is win or not win 
	public boolean isWin(int playerType) {
		final int DI[]={-1,0,1,1};
		final int DJ[]={1,1,1,0};
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++) {
				// we skip if the token in position(i,j) not equal current token 
				if(getButtonValue(i, j)!=playerType) 
					continue;
				for(int k=0;k<4;k++) {
					int ctr = 0;
					while(getButtonValue(i+DI[k]*ctr, j+DJ[k]*ctr)==playerType) 
						ctr++;
					if(ctr==3) 
						return true;
				}
			}
		return false;
	}

}
