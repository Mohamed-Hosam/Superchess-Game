package model.game;

import java.util.Random;

import javax.swing.JFrame;

import exceptions.InvalidPowerDirectionException;
import exceptions.OccupiedCellException;
import exceptions.PowerAlreadyUsedException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Hero;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKickP1;
import model.pieces.sidekicks.SideKickP2;

public class Game extends JFrame{

	private final int payloadPosTarget = 6;
	private final int boardWidth = 6;
	private final int boardHeight = 7;
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private Cell[][] board;
	public static void main(String [] args) throws OccupiedCellException, UnallowedMovementException, WrongTurnException, InvalidPowerDirectionException, PowerAlreadyUsedException
	{
//		Player Player1 = new Player("Player 1");
//		Player Player2 = new Player("Player 2");
//		Game game = new Game(Player1 , Player2);
////		((Ranged)game.getCellAt(5, 2).getPiece()).usePower(Direction.UP, null, null);
//		System.out.println(game.toString());
	}

	public Game(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		currentPlayer = player1;
		board = new Cell[boardHeight][boardWidth];
//		this.assemblePiecesForTests();
		this.assemblePieces();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public int getPayloadPosTarget() {
		return payloadPosTarget;
	}

	@Override
	public String toString() {
		String s = "";
		System.out.println("      " + getPlayer2().getName());
		System.out.print("| ");
		for (int i = 0; i < board[0].length; i++)
			System.out.print("--");
		System.out.println("|");
		for (int i = 0; i < board.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == null)
					System.out.print("n ");
				else
					System.out.print(board[i][j] + " ");
			}
			System.out.println("|");
		}
		System.out.print("| ");
		for (int i = 0; i < board[0].length; i++)
			System.out.print("--");
		System.out.println("|");
		System.out.println("    " + getPlayer1().getName());
		return s;
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public int getBoardHeight() {
		return boardHeight;
	}
	
	//Methods That I wrote
	public void assemblePieces()
	{
		//Player 1
		Armored A1 = new Armored(this.getPlayer1() , this , "D.Va");
		Medic M1 = new Medic(this.getPlayer1() , this , "Mercy");
		Ranged R1 = new Ranged(this.getPlayer1() , this , "Hanzo");
		Speedster S1 = new Speedster(this.getPlayer1() , this ,"Tracer");
		Super SU1 = new Super(this.getPlayer1() , this , "Reinhardt");
		Tech T1 = new Tech(this.getPlayer1() , this , "Sombra");
		
		//Player 2
		Armored A2 = new Armored(this.getPlayer2() , this , "Zarya"); //from kled to Zarya
		Medic M2 = new Medic(this.getPlayer2() , this , "Zenyatta"); // from soraka to zenyatta
		Ranged R2 = new Ranged(this.getPlayer2() , this , "Pharah"); //from jhin to jinxed to pharah
		Speedster S2 = new Speedster(this.getPlayer2() , this ,"Genji"); //from Kassadin to genji
		Super SU2 = new Super(this.getPlayer2() , this , "Winston"); //from galio to riven to  Winston
		Tech T2 = new Tech(this.getPlayer2() , this , "Torb"); // from heimer to Torb
		
		//Player 1:
		
		//Heroes
		Random R = new Random();
		Hero[] H1 = {A1 , M1 , R1 , S1 , SU1 , T1};
		boolean a[] = new boolean[6];
		for(int i = 0;i<H1.length;i++)
		{
			int x = R.nextInt(6);
			while(a[x])
				x = R.nextInt(6);
			a[x] = true;
			board[5][x] = new Cell(H1[i]);
			board[5][x].getPiece().setPosI(5);
			board[5][x].getPiece().setPosJ(x);
		}
		
		//SideKicks:
		for(int i = 0;i<6;i++) {
			board[4][i] = new Cell(new SideKickP1(this , "Symmetra"));  //shelt el i counting (+i)
			board[4][i].getPiece().setPosI(4);
			board[4][i].getPiece().setPosJ(i);
		}
		//-----------------------------------------------------------------
		
		//Player 2:
		//Heroes
		Random RPlayer2 = new Random();
		Hero[] H2 = {A2 , M2 , R2 , S2 , SU2 , T2};
		boolean b[] = new boolean[6];
		for(int i = 0;i<H2.length;i++)
		{
			int y = RPlayer2.nextInt(6);
			while(b[y])
				y = RPlayer2.nextInt(6);
			b[y] = true;
			board[1][y] = new Cell(H2[i]);
			board[1][y].getPiece().setPosI(1);
			board[1][y].getPiece().setPosJ(y);
		}
		
		//setting the rest of the cells to to a null piece
			for(int j = 0;j<6;j++)
			{
				board[0][j] = new Cell(null);
				board[3][j] = new Cell(null);
				board[6][j] = new Cell(null);
			}
		
		//SideKicks:
		for(int i = 0;i<6;i++)
		{
			board[2][i] = new Cell(new SideKickP2(this , "Minion"));  //shelt el i counting (+i)
			board[2][i].getPiece().setPosI(2);
			board[2][i].getPiece().setPosJ(i);
		}
		
			
	}
	
	public Cell getCellAt(int i , int j)
	{
		return board[i][j];
	}
	public boolean checkWinner()
	{
		if(this.getCurrentPlayer().getPayloadPos() == payloadPosTarget)
			return true;
		else
			return false;
	}
	public void switchTurns()
	{
		if(this.getCurrentPlayer().equals(this.player1))
			this.setCurrentPlayer(this.player2);
		else
			this.setCurrentPlayer(this.player1);
	}
	public void assemblePiecesForTests()
	{
		Armored A1 = new Armored(this.getPlayer1() , this , "D.Va");
		Medic M1 = new Medic(this.getPlayer1() , this , "Mercy");
		Ranged R1 = new Ranged(this.getPlayer1() , this , "Hanzo");
		Speedster S1 = new Speedster(this.getPlayer1() , this ,"Tracer");
		Super SU1 = new Super(this.getPlayer1() , this , "Reinhardt");
		Tech T1 = new Tech(this.getPlayer1() , this , "Sombra");
		
		//Player 2
		Armored A2 = new Armored(this.getPlayer2() , this , "Kled");
		Medic M2 = new Medic(this.getPlayer2() , this , "Soraka");
		Ranged R2 = new Ranged(this.getPlayer2() , this , "Jinx"); //changed to jinx
		Speedster S2 = new Speedster(this.getPlayer2() , this ,"Kassadin");
		Super SU2 = new Super(this.getPlayer2() , this , "Riven"); //changed from galio to riven
		Tech T2 = new Tech(this.getPlayer2() , this , "Heimerdinger");
		
		Hero[] H1 = {A1 , M1 , R1 , S1 , SU1 , T1};
		for(int i = 0;i<H1.length;i++)
			board[5][i] = new Cell(H1[i]);
		Hero[] H2 = {A2 , M2 , R2 , S2 , SU2 , T2};
		for(int i = 0;i<H2.length;i++)
			board[1][i] = new Cell(H2[i]);
		for(int j = 0;j<6;j++)
		{
			board[0][j] = new Cell(null);
			board[3][j] = new Cell(null);
			board[6][j] = new Cell(null);
		}
	}

}
