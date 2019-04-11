package tests;

import java.awt.Point;
import java.util.Scanner;

import exceptions.InvalidPowerDirectionException;
import exceptions.InvalidPowerTargetException;
import exceptions.OccupiedCellException;
import exceptions.PowerAlreadyUsedException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import model.game.*;
import model.pieces.Piece;
import model.pieces.heroes.ActivatablePowerHero;
/*as you know there are some functionality that will be very hard to test with a randomized board and for leaziness
 * JUST COPY  this modified assemble pieces  **with no random** 
 * WHAT IS TESTED ?
 * all you want to test is available just read the instructions and start writing your own cases 
 * how to check if it works right ? 
 * after each move you make the board will be printed and all you have to do is to compare the printed board with the predicted one . 
 * REMEMBER  !!! TO REMOVE THIS ASSEMBLE PIECES MOTHED AND RETUR IT TO ITS RANDOMIZED FORM AND TO RUN M2 TEST AGAIN 
 * IT IS your responsibility !!
 *why ? 
 *just for fun and to avoid mistakes that is not checked in the public tests 
 * public void assemblePieces() {
			for (int j = 0; j < 6; j++) {
			board[0][j] = new Cell();
			board[3][j] = new Cell();
			board[6][j] = new Cell();
				SideKickP2 p2=	new SideKickP2(this, "kickside2" );
				SideKickP1 p1= new SideKickP1(this, "kickside1");
				board[2][j] = new Cell(p2);
				board[4][j] = new Cell(p1);
				p2.setPosI(2);p2.setPosJ(j); 
				p1.setPosI(4);p1.setPosJ(j); 
			}
			Hero[] p1 = new Hero[] { new Armored(player1, this, "Armored1"),
					new Medic(player1, this, "Medic1"),
					new Ranged(player1, this, "Ranged1"),
					new Speedster(player1, this, "Speedster1"),
					new Super(player1, this, "Super1"),
					new Tech(player1, this, "Tech1") };
			Hero[] p2 = new Hero[] { new Armored(player2, this, "Armored2"),
					new Medic(player2, this, "Medic2"),
					new Ranged(player2, this, "Ranged2"),
					new Speedster(player2, this, "Speedster2"),
					new Super(player2, this, "Super2"),
					new Tech(player2, this, "Tech2") };
			for(int a=0;a<6;a++)  {board[1][a] =new Cell(p2[a]);p2[a].setPosI(1);p2[a].setPosJ(a);
			board[5][a] =new Cell(p1[a]);p1[a].setPosI(5);p1[a].setPosJ(a);
				}
		}
 * 
 */
public class manualtest {

	public static void main(String[] args) {
		Player a = new Player("1"
				+ "");
		Player b = new Player("2");
		Game g = new Game(a, b);

		Scanner sc = new Scanner(System.in);

		System.out.println("How to play: \n"+
							"1) To move a Piece: use the instruction \"m i j d\"  without the quotes \n"
							+ "where i is the vertical coordinate, \n"
							+ "j is the horizontal coordinate of the Piece \n"
							+ "and d is the Direction in lowercase letters; \n"
							+ "for example: u, l, ul, dr.  \n"
							+ "2) To use a power of a Piece: use the instruction \"p i j ti tj ni nj d\" \n"
							+ "where i j and d are the same as in 1), \n"
							+ "ti and tj are the coordinates for the target Piece \n"
							+ "and ni and nj are the coordinates of the Point to which the target Piece is teleported. \n \n"
							+ "Notes: \n"
							+ "1) If you do not need a parameter in \"p i j ti tj ni nj d\" \n"
							+ "skip it! \n"
							+ "For example: to use the Ranged's power just use \"p i j d\" \n"
							+ "2) To access a Piece in the grave for player1 set ti = -1 and tj will be your arraylist index \n"
							+ "similarly set ti = -2 for player2's grave. \n");
		
		while (!g.checkWinner()) {
			printGame(g);
			String m = sc.nextLine();
			String[] w =m.split(" ");
			int i =Integer.parseInt(w[1]);
			int j =Integer.parseInt(w[2]);
			Direction d = directionGetter(w[3]);
			if (m.charAt(0) == 'm') {
				try {
					g.getCellAt(i, j).getPiece().move(d);
				} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException e) {
					System.out.println(e.getMessage()+"\n");
				}catch (NullPointerException e) {
					System.out.println("No piece here!");
				}
			} else if (m.charAt(0) == 'p')  {
				Object[] o =transPower(w,g);
				try {
					((ActivatablePowerHero) o[0]).usePower((Direction)o[1], (Piece)o[2], (Point)o[3]);
				} catch (InvalidPowerDirectionException | InvalidPowerTargetException | PowerAlreadyUsedException | WrongTurnException e) {
					System.out.println(e.getMessage()+"\n");
				}
			}
		}
		if(a.getPayloadPos()>=6)
			System.out.println(a.getName()+" won!");
		else
			System.out.println(b.getName()+" won!");
		sc.close();
	}

	private static Direction directionGetter(String s) {
		switch(s) {
			case "u":return Direction.UP;
			case "d":return Direction.DOWN;
			case "l":return Direction.LEFT;
			case "r":return Direction.RIGHT;
			case "ur":return Direction.UPRIGHT;
			case "dr":return Direction.DOWNRIGHT;
			case "ul":return Direction.UPLEFT;
			case "dl":return Direction.DOWNLEFT;
			default: return null;
		}	
	}
	
	private static Object[] transPower(String[] w, Game g) {
		int i =Integer.parseInt(w[1]);
		int j =Integer.parseInt(w[2]);
		Piece p = g.getCellAt(i, j).getPiece() ;
		Direction d = null;
		Piece t = null;
		Point n = null;
		
		int l =w.length;
		if(l==4||l==6) {
			d= directionGetter(w[l-1]); 
		}
		if(l!=4) {
			int ti =Integer.parseInt(w[3]);
			int tj =Integer.parseInt(w[4]);
			try {
				t = g.getCellAt(ti, tj).getPiece();
			}catch (ArrayIndexOutOfBoundsException e) {
				if(ti==-1) {
					t = g.getPlayer1().getDeadCharacters().get(tj);
				}else if(ti==-2) {
					t = g.getPlayer2().getDeadCharacters().get(tj);
				}
			} 
		}
		if(l==7) {
			int ni =Integer.parseInt(w[5]);
			int nj =Integer.parseInt(w[6]);
			n = new Point(ni,nj); 
		}
		Object[] o ={p,d,t,n};
		return o;
	}
	
	private static void printGame(Game g) {
		System.out.print("   ");
		for (int i = 0; i < 6; i++)
			System.out.print(i+"  ");
		System.out.println("");
		for (int i = 0; i < 7; i++) {
			System.out.print( i +"| ");
			for (int j = 0; j < 6; j++) {
				if (g.getCellAt(i,j).getPiece() == null)
					System.out.print("n  ");
				else {
					System.out.print(g.getCellAt(i,j) +
									g.getCellAt(i,j).getPiece().getOwner().getName()+ " ");
				}
			}
			System.out.println("|");
		}
		if(!g.getPlayer1().getDeadCharacters().isEmpty()) {
			System.out.print("Dead pieces for "+ g.getPlayer1().getName() +": ");
			for (Piece i : g.getPlayer1().getDeadCharacters())
				System.out.print(i+"  ");
			System.out.println("");
		}
		if(!g.getPlayer2().getDeadCharacters().isEmpty()) {
			System.out.print("Dead pieces for "+ g.getPlayer2().getName() +": ");
			for (Piece i : g.getPlayer2().getDeadCharacters())
				System.out.print(i+"  ");
			System.out.println("");
		}
	}
}
