package model.pieces;

import exceptions.InvalidMovementException;
import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Hero;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKick;
import model.pieces.sidekicks.SideKickP1;
import model.pieces.sidekicks.SideKickP2;

public abstract class Piece implements Movable {

	private String name;
	private Player owner;
	private Game game;
	private int posI;
	private int posJ;

	public Piece(Player p, Game g, String name) {
		this.owner = p;
		this.game = g;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getPosI() {
		return posI;
	}

	public void setPosI(int i) {
		posI = i;
	}

	public int getPosJ() {
		return posJ;
	}

	public void setPosJ(int j) {
		posJ = j;
	}

	public Game getGame() {
		return game;
	}

	public Player getOwner() {
		return owner;
	}

	// methods I wrote
	public void attack(Piece target)
	{
		int i = target.getPosI();
		int j = target.getPosJ();
		Cell c = game.getCellAt(i, j);
		Cell curr = game.getCellAt(this.posI, this.posJ);
		if(target instanceof Armored)
		{
			if(((Armored)target).isArmorUp())
			{
				((Armored) target).setArmorUp(false);
			}
			else
			{
				c.setPiece(null);
				if(this instanceof SideKick)
					curr.setPiece(new Armored(this.getOwner() ,this.getGame() ,  target.getName()));
				target.getOwner().getDeadCharacters().add(target);
				this.getOwner().setPayloadPos(this.getOwner().getPayloadPos()+1);
				this.getGame().checkWinner();
			}
		}
		else
		{
			if(target instanceof SideKick)
			{
				if(this.getOwner().getSideKilled()%2==1)
				{
					this.getOwner().setPayloadPos(this.getOwner().getPayloadPos()+1);
					target.getOwner().getDeadCharacters().add(target);
				}
				else
				{
					target.getOwner().getDeadCharacters().add(target);
				}
				this.getOwner().setSideKilled(this.getOwner().getSideKilled()+1);
				c.setPiece(null);
			}
			else
			{
				if(this instanceof SideKick && target instanceof Hero) //if SideKick kills a hero
				{
					target.getOwner().getDeadCharacters().add(target);
					if(target instanceof Medic)
						curr.setPiece(new Medic(this.getOwner() , this.getGame() ,target.getName()));
					if(target instanceof Ranged)
						curr.setPiece(new Ranged(this.getOwner() , this.getGame() , target.getName()));
					if(target instanceof Speedster)
						curr.setPiece(new Speedster(this.getOwner() , this.getGame() , target.getName()));
					if(target instanceof Super)
						curr.setPiece(new Super(this.getOwner() , this.getGame() , target.getName()));
					if(target instanceof Tech)
						curr.setPiece(new Tech(this.getOwner() , this.getGame() , target.getName()));
					curr.getPiece().setPosI(this.getPosI());
					curr.getPiece().setPosJ(this.getPosJ());
					c.setPiece(null);
					this.getOwner().setPayloadPos(this.getOwner().getPayloadPos()+1);
				}
				else
				{
					target.getOwner().getDeadCharacters().add(target);
					c.setPiece(null);
					this.getOwner().setPayloadPos(this.getOwner().getPayloadPos()+1);
				}
			}
			this.getGame().checkWinner();
		}
		
//		this.getGame().checkWinner();
		
		
	}
	public void move(Direction r) throws OccupiedCellException, UnallowedMovementException, WrongTurnException  //wala InvalidMovement??
	{
		if(this.getGame().getCurrentPlayer().equals(this.getOwner()))
		{
			switch(r) {
			case UP :
				if(this instanceof Tech || this instanceof SideKickP2)
					throw new UnallowedMovementException("You cannot move here" , this , r);
				else
					this.moveUp();break;
			case DOWN : 
				if(this instanceof Tech || this instanceof SideKickP1)
					throw new UnallowedMovementException("You cannot move here" , this , r);
				else
					this.moveDown();break;
			case RIGHT : 
				if(this instanceof Tech)
					throw new UnallowedMovementException("You cannot move here" , this , r);
				else
					this.moveRight();break;
			case LEFT : 
				if(this instanceof Tech)
					throw new UnallowedMovementException("You cannot move here" , this , r);
				else
					this.moveLeft();break;
			case UPLEFT : 
				if(this instanceof Ranged || this instanceof Armored || this instanceof Speedster || this instanceof SideKickP1 || this instanceof Tech)
					this.moveUpLeft();
				else
					throw new UnallowedMovementException("You cannot move here" , this , Direction.UPLEFT);
				break;
			case UPRIGHT : 
				if(this instanceof Ranged || this instanceof Armored || this instanceof Speedster || this instanceof SideKickP1 || this instanceof Tech)
					this.moveUpRight();
				else
					throw new UnallowedMovementException("You cannot move here" , this , Direction.UPRIGHT);
				break;
			case DOWNLEFT : 
				if(this instanceof Ranged || this instanceof Armored || this instanceof Speedster || this instanceof SideKickP2 || this instanceof Tech)
					this.moveDownLeft();
				else
					throw new UnallowedMovementException("You cannot move here" , this , Direction.DOWNLEFT);
				break;
			case DOWNRIGHT :
				if(this instanceof Ranged || this instanceof Armored || this instanceof Speedster || this instanceof SideKickP2 || this instanceof Tech)
					this.moveDownRight();
				else
					throw new UnallowedMovementException("You cannot move here" , this , Direction.DOWNRIGHT);
				break;
//			default:throw new UnallowedMovementException("You cannot move here" , this , r);
			//switching turns
			}
			game.switchTurns();
		}
		else
		{
			throw new WrongTurnException("This Is Not Your Turn" , this);
		}
	}

//	public void moveUp() throws OccupiedCellException
//	{
//		int finalUp = 0;
//		
//		if(this.getPosI() == 0)
//			finalUp = 6;
//		else
//			finalUp = this.posI-1;
//		if(game.getCellAt(finalUp, this.posJ).getPiece()==null) // el destination cell fadya
//		{
//			game.getCellAt(finalUp, this.posJ).setPiece(this);
//			game.getCellAt(this.posI, this.posJ).setPiece(null);
//			this.setPosI(finalUp);
//		}
//		else
//			if(game.getCellAt(finalUp, this.posJ).getPiece().getOwner().equals(this.getOwner())) // lw feh ally piece
//				throw new OccupiedCellException("You Cannot Move into a Cell occupied by an ally" , this , Direction.UP);
//			else
//			{
//				this.attack(game.getCellAt(finalUp, this.posJ).getPiece()); //enemy piece
//				if(game.getCellAt(finalUp, this.posJ).getPiece() == null)//lw armored w only the shield is dropped
//				{
//					game.getCellAt(finalUp, this.posJ).setPiece(game.getCellAt(this.posI, this.posJ).getPiece());
//					game.getCellAt(this.posI, this.posJ).setPiece(null);
//					this.setPosI(finalUp);
//					this.setPosJ(this.getPosJ());
//				}
//			}
//	}
	public void moveUp() throws OccupiedCellException
	{
		int finalUp = 0;
		
		if(this.getPosI() == 0)
			finalUp = 6;
		else
			finalUp = this.posI-1;
		Cell currCell = game.getCellAt(this.posI, this.posJ);
		Cell finalCell = game.getCellAt(finalUp, this.getPosJ());
		if(finalCell.getPiece()==null) // el destination cell fadya
		{
			finalCell.setPiece(this);
			currCell.setPiece(null);
			this.setPosI(finalUp);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner())) // lw feh ally piece
				throw new OccupiedCellException("You Cannot Move into a Cell occupied by an ally" , this , Direction.UP);
			else
			{
				this.attack(finalCell.getPiece()); //enemy piece
				if(finalCell.getPiece() == null)//lw armored w only the shield is dropped
				{
					finalCell.setPiece(currCell.getPiece());
					currCell.setPiece(null);
					finalCell.getPiece().setPosI(finalUp);
					finalCell.getPiece().setPosJ(this.getPosJ());
				}
			}
	}

	public void moveDown() throws OccupiedCellException{
		int finalDown = 0;
		if(this.getPosI() < 6)
			finalDown = this.posI+1;
		Cell finalCell = game.getCellAt(finalDown, this.posJ);
		Cell currCell = game.getCellAt(this.posI, this.posJ);
		if(finalCell.getPiece() == null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			this.setPosI(finalDown);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.owner))
				throw new OccupiedCellException("You Cannot Move into a Cell occupied by an ally" , this , Direction.DOWN);
			else
			{
				this.attack(finalCell.getPiece());
				if(finalCell.getPiece()==null)
				{
					finalCell.setPiece(currCell.getPiece());
					currCell.setPiece(null);
					finalCell.getPiece().setPosI(finalDown);
					finalCell.getPiece().setPosJ(this.getPosJ());
				}
			}
	}
	public void moveRight() throws OccupiedCellException{
		int finalRight = 0;
		if(this.posJ == 5)
			finalRight = 0;
		else
			finalRight = this.posJ+1;
		Cell finalCell = game.getCellAt(this.posI, finalRight);
		Cell currCell = game.getCellAt(this.posI, this.posJ);
		if(finalCell.getPiece() == null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			this.posJ = finalRight;
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new OccupiedCellException("You Cannot Move into a Cell occupied by an ally" , this , Direction.RIGHT);
			else
			{
				//System.out.println(finalCell.toString());
				this.attack(finalCell.getPiece());
//				System.out.println(finalCell.toString());
				if(finalCell.getPiece()==null)
				{
					finalCell.setPiece(currCell.getPiece());
					currCell.setPiece(null);
					finalCell.getPiece().setPosI(this.getPosI());
					finalCell.getPiece().setPosJ(finalRight);
				}
			}
	}
	public void moveLeft() throws OccupiedCellException{
		int finalLeft = 0;
		if(this.posJ == 0)
			finalLeft = 5;
		else
			finalLeft = this.posJ-1;
		Cell finalCell = game.getCellAt(this.posI, finalLeft);
		Cell currCell =game.getCellAt(this.posI, this.posJ);
		if(finalCell.getPiece()==null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			this.posJ = finalLeft;
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new OccupiedCellException("You Cannot Move into a Cell occupied by an ally" , this , Direction.LEFT);
			else
			{
				this.attack(finalCell.getPiece());
				if(finalCell.getPiece() == null)
				{
					finalCell.setPiece(currCell.getPiece());
					currCell.setPiece(null);
					finalCell.getPiece().setPosI(this.getPosI());
					finalCell.getPiece().setPosJ(finalLeft);
				}
			}
	}
	public void moveUpRight() throws OccupiedCellException{
//		int finalUpRighti = 0;
//		int finalUpRightj = 0;
//		if(this.posI == 0)
//			if(this.posJ<5) // top
//			{
//				finalUpRighti = 6;
//				finalUpRightj = this.posJ+1;
//			}
//			else //corner
//			{
//				finalUpRighti = 6;
//				finalUpRightj = 0;
//			}
//		else
//			if(this.posJ < 5) // 3adee
//			{
//				finalUpRighti = this.posI-1;
//				finalUpRightj = this.posJ+1;
//			}
//			else //a5r col
//			{
//				finalUpRighti = this.posI-1;
//				finalUpRightj = 0;
//			}
		int finalUpRighti = 0;
		if(this.getPosI() == 0)
			finalUpRighti = 6;
		else
			finalUpRighti = this.posI-1;
		int finalUpRightj = 0;
		if(this.posJ == 5)
			finalUpRightj = 0;
		else
			finalUpRightj = this.posJ+1;
		Cell finalCell = game.getCellAt(finalUpRighti, finalUpRightj);
		Cell currCell = game.getCellAt(this.posI, this.posJ);
		if(finalCell.getPiece()==null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			finalCell.getPiece().setPosI(finalUpRighti);
			finalCell.getPiece().setPosJ(finalUpRightj);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new OccupiedCellException("You Cannot Move into a Cell occupied by an ally" , this , Direction.UPRIGHT);
			else
			{
				this.attack(finalCell.getPiece());
				if(finalCell.getPiece() == null)
				{
					finalCell.setPiece(currCell.getPiece());
					currCell.setPiece(null);
					finalCell.getPiece().setPosI(finalUpRighti);
					finalCell.getPiece().setPosJ(finalUpRightj);
				}
			}
					
	}
	
	public void moveUpLeft() throws OccupiedCellException{
//		int finalUpLefti = 0;
//		int finalUpLeftj = 0;
//		if(this.posI == 0)
//		{
//			finalUpLefti = 6;
//			if(this.posJ == 5) // a5r kol awl row
//			{
//				finalUpLeftj = this.posJ-1;
//			}
//			else //awl cell 5ales (0,0)
//				finalUpLeftj = 5;
//		}
//		else
//		{
//			finalUpLefti = this.posI-1; //awl col
//			if(this.posJ == 0)
//			{	
//				finalUpLeftj = 5;
//			}
//			else //3adee
//			{
//				finalUpLeftj = this.posJ-1;
//			}
//		}
		int finalUpLefti = 0;
		if(this.getPosI() == 0)
			finalUpLefti = 6;
		else
			finalUpLefti = this.posI-1;
		int finalUpLeftj = 0;
		if(this.posJ == 0)
			finalUpLeftj = 5;
		else
			finalUpLeftj = this.posJ-1;
		Cell finalCell = game.getCellAt(finalUpLefti, finalUpLeftj);
		Cell currCell = game.getCellAt(this.posI, this.posJ);
		if(finalCell.getPiece() == null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			finalCell.getPiece().setPosI(finalUpLefti);
			finalCell.getPiece().setPosJ(finalUpLeftj);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.owner))
				throw new OccupiedCellException("You Cannot Move into a Cell occupied by an ally" , this , Direction.UPLEFT);
			else
			{
				this.attack(finalCell.getPiece());
				if(finalCell.getPiece() == null)
				{
					finalCell.setPiece(currCell.getPiece());
					currCell.setPiece(null);
					finalCell.getPiece().setPosI(finalUpLefti);
					finalCell.getPiece().setPosJ(finalUpLeftj);
				}
			}
		
	}
	public void moveDownRight() throws OccupiedCellException{
//		int finalDownRighti = 0;
//		int finalDownRightj = 0;
//		if(this.posI == 6)
//		{
//			finalDownRighti = 0;
//			if(this.posJ == 5)//a5r row  a5r cell 3al yemen
//				finalDownRightj = 0;
//			else
//				finalDownRightj = this.posJ+1; //a5r row in general except a5r wa7da
//		}
//		else
//		{
//			finalDownRighti = this.posI+1; 
//			if(this.posJ == 5)//a5r col m3ada a5r wa7da t7t
//			{
//				finalDownRightj = 0;
//			}
//			else
//				finalDownRightj = this.posJ+1; //3adee
//		}
		int finalDownRighti = 0;
		if(this.getPosI() < 6)
			finalDownRighti = this.posI+1;
		int finalDownRightj = 0;
		if(this.posJ == 5)
			finalDownRightj = 0;
		else
			finalDownRightj = this.posJ+1;
		Cell finalCell  = game.getCellAt(finalDownRighti, finalDownRightj);
		Cell currCell = game.getCellAt(this.posI, this.posJ);
		if(finalCell.getPiece()==null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			finalCell.getPiece().setPosI(finalDownRighti);
			finalCell.getPiece().setPosJ(finalDownRightj);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.owner))
				throw new OccupiedCellException("You Cannot Move into a Cell occupied by an ally" , this , Direction.DOWNRIGHT);
			else
			{
				this.attack(finalCell.getPiece());
				if(finalCell.getPiece() == null)
				{
					finalCell.setPiece(currCell.getPiece());
					currCell.setPiece(null);
					finalCell.getPiece().setPosI(finalDownRighti);
					finalCell.getPiece().setPosJ(finalDownRightj);
				}
			}
			
		
	}
	
	public void moveDownLeft() throws OccupiedCellException{
//		int finalDownLefti = 0;
//		int finalDownLeftj = 0;
//		
//		if(this.posI == 6)
//		{
//			finalDownLefti = 0;
//			if(this.posJ == 0)//a5r row a5r cell 3l shemal
//				finalDownLeftj = 5;
//			else
//				finalDownLeftj = this.posJ-1; //a5r row row in general m3ada el shemal cell
//		}
//		else
//		{
//			finalDownLefti = this.posI+1;
//			if(this.posJ == 0) // awl row awl cell shemal
//				finalDownLeftj = 5;
//			else
//				finalDownLeftj = this.posJ-1; // 3adee
//		}
		int finalDownLefti = 0;
		if(this.getPosI() < 6)
			finalDownLefti = this.posI+1;
		int finalDownLeftj = 0;
		if(this.posJ == 0)
			finalDownLeftj = 5;
		else
			finalDownLeftj = this.posJ-1;
		Cell finalCell = game.getCellAt(finalDownLefti, finalDownLeftj);
		Cell currCell = game.getCellAt(this.posI , this.posJ);
		if(finalCell.getPiece() == null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			finalCell.getPiece().setPosI(finalDownLefti);
			finalCell.getPiece().setPosJ(finalDownLeftj);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new OccupiedCellException("You Cannot Move into a Cell occupied by an ally" , this , Direction.DOWNLEFT);
			else
			{
				this.attack(finalCell.getPiece());
				if(finalCell.getPiece() == null)
				{
					finalCell.setPiece(currCell.getPiece());
					currCell.setPiece(null);
					finalCell.getPiece().setPosI(finalDownLefti);
					finalCell.getPiece().setPosJ(finalDownLeftj);
				}
			}
	}
	
	

}
