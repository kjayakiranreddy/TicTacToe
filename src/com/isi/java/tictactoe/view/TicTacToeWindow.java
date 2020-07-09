package com.isi.java.tictactoe.view;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.isi.java.tictactoe.model.TicTacToeModel;

@SuppressWarnings("serial")
public class TicTacToeWindow extends JFrame implements ActionListener, ItemListener, ITicTacToeView
{
	
	private JButton [][]buttons;
	private JButton playButton;
	private JLabel statusLabel;
	private TicTacToeModel game = null;
	private int human = 0;
	private int aI = 0;
	private boolean isPlay,vsHuman,anotherHumanTurn;
	private String []options=new String[]{"","X","O"};
	Checkbox aICheckbox, friendCheckbox;

	JPanel centerPanel ;
	JPanel southPanel;
	JPanel northPanel ;
	Font font ;

	public void setStatus(String s) {
		statusLabel.setText(s);
	}

	public void setButtonsEnabled(boolean enabled) {
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++) {
				buttons[i][j].setEnabled(enabled);
				if(enabled) buttons[i][j].setText(" ");
			}
	}

	public TicTacToeWindow() {

		setTitle("Tic Tac Toe");
		
		createComponents();
		setupPanels();

		add(aICheckbox);
		add(friendCheckbox);
		aICheckbox.addItemListener(this);
		friendCheckbox.addItemListener(this);

		buttons = new JButton[3][3];
		playButton.addActionListener(this);
		
		add(northPanel,"North");
		add(centerPanel,"Center");
		add(southPanel,"South");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		setSize(400,400);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void setupPanels() 
	{
		centerPanel = new JPanel(new GridLayout(3,3));
		font = new Font("Arial",Font.BOLD, 32);
		southPanel = new JPanel();
		northPanel = new JPanel();
		vsHuman=true;
		northPanel.add(statusLabel);
	}

	private void createComponents() 
	{
		playButton = new JButton("Play");
		statusLabel = new JLabel("");
		CheckboxGroup checkboxGroup = new CheckboxGroup();

		aICheckbox = new Checkbox(" vs AI", checkboxGroup, false);
		friendCheckbox = new Checkbox(" vs friend", checkboxGroup, false);
		aICheckbox.setBounds(120, 80, 100, 10);
		friendCheckbox.setBounds(120, 180, 100, 10);
		
	}

	public void computerTurn() {
		if(!isPlay) return;

		int []pos = game.nextMove(aI);
		if(pos!=null) {
			int i = pos[0];
			int j = pos[1];
			buttons[i][j].setText(options[aI]);
			game.setButtonValue(i,j,aI);
		}
		checkState();
	}

	public void gameOver(String s) {
		setStatus(s);
		setButtonsEnabled(false);
		isPlay = false;
	}

	public void checkState() {
		if(game.isWin(human)) {
			gameOver("Congratulations, X Won!");
		}
		if(game.isWin(aI)) {
			gameOver("Better luck next time, You Lose!");
		}
		if(game.isWin(aI)&&vsHuman) {
			gameOver("Congratulations, O Won!");
		}

		if(game.nextMove(human)==null && game.nextMove(aI)==null) {
			gameOver("Draw, Click 'Play' For Rematch!");
		}
	}

	public void click(int i,int j) {
		if(game.getButtonValue(i,j)==TicTacToeModel.EMPTY && !vsHuman) {
			buttons[i][j].setText(options[human]);
			game.setButtonValue(i,j,human);
			checkState();
			computerTurn();
		}else {
			if(game.getButtonValue(i,j)==TicTacToeModel.EMPTY && !anotherHumanTurn) {
				
				buttons[i][j].setText(options[human]);
				game.setButtonValue(i,j,human);
				setStatus("~~~~~~~ O turn ~~~~~~~");
				checkState();
				
				anotherHumanTurn=true;
			}else if(game.getButtonValue(i,j)==TicTacToeModel.EMPTY){
				
				buttons[i][j].setText(options[aI]);
				game.setButtonValue(i,j,aI);
				setStatus("~~~~~~~ X turn ~~~~~~~");
				checkState();
				
				anotherHumanTurn=false;
			}else {
				JOptionPane.showMessageDialog(this, "Warring: Draw on empty Box");
			}
		}

	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==playButton) {
			play();
		}else {
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
					if(event.getSource()==buttons[i][j])
						click(i,j);
		}
	}
	public void play() {
		anotherHumanTurn=false;
		game = new TicTacToeModel();
		human = TicTacToeModel.ONE;
		aI = TicTacToeModel.TWO;

		setButtonsEnabled(true);
		isPlay = true;
	}

	public void itemStateChanged(ItemEvent e) {
		if (aICheckbox.getState()) {
			vsHuman = false;
		}
		else if (friendCheckbox.getState()) {
			vsHuman = true;
		}
		remove(aICheckbox);
		remove(friendCheckbox);
		//repaint(0, 0, 330, 450);
		showButton();
	}
	public void showButton() {

		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++) {
				buttons[i][j] = new JButton(" ");
				buttons[i][j].setFont(font);
				buttons[i][j].addActionListener(this);
				buttons[i][j].setFocusable(false);
				centerPanel.add(buttons[i][j]);
			}
		southPanel.add(playButton);

		setStatus("Play");
		setButtonsEnabled(true);
		play();

	}

}
