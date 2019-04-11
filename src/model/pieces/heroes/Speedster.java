package model.pieces.heroes;

import exceptions.OccupiedCellException;
import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;

public class Speedster extends NonActivatablePowerHero {

	public Speedster(Player player, Game game, String name) {
		super(player, game, name);
	}

	@Override
	public String toString() {
		return "S";
	}
	public void moveUp() throws OccupiedCellException
	{
		int finalUp = 0;
		if(this.getPosI() == 0)
			finalUp = 5;
		else
			if(this.getPosI() == 1)
				finalUp = 6;
			else
				finalUp = this.getPosI()-2; //changed this
		if(getGame().getCellAt(finalUp, this.getPosJ()).getPiece()==null) // el destination cell fadya
		{
			getGame().getCellAt(finalUp, this.getPosJ()).setPiece(this);
			getGame().getCellAt(this.getPosI(), this.getPosJ()).setPiece(null);
			getGame().getCellAt(finalUp , this.getPosJ()).getPiece().setPosI(finalUp);
			getGame().getCellAt(finalUp , this.getPosJ()).getPiece().setPosJ(this.getPosJ());
		}
		else
			if(getGame().getCellAt(finalUp, this.getPosJ()).getPiece().getOwner().equals(this.getOwner())) // lw feh ally piece
				throw new OccupiedCellException("You Cannot Move into a Cell occupied by an ally" , this , Direction.UP);
			else
			{
				this.attack(getGame().getCellAt(finalUp, this.getPosJ()).getPiece()); //enemy piece
				if(getGame().getCellAt(finalUp, this.getPosJ()).getPiece() == null)//lw armored w only the shield is dropped
				{
					getGame().getCellAt(finalUp, this.getPosJ()).setPiece(getGame().getCellAt(this.getPosI(), this.getPosJ()).getPiece());
					getGame().getCellAt(this.getPosI(), this.getPosJ()).setPiece(null);
					getGame().getCellAt(finalUp , this.getPosJ()).getPiece().setPosI(finalUp);
					getGame().getCellAt(finalUp , this.getPosJ()).getPiece().setPosJ(this.getPosJ());
				}
			}
	}
	
	public void moveDown() throws OccupiedCellException{
		int finalDown = 0;
		if(this.getPosI() == 6)
			finalDown = 1;
		else
			if(this.getPosI() == 5)
				finalDown = 0;
			else
				finalDown = this.getPosI()+2; //changed this
		Cell finalCell = getGame().getCellAt(finalDown, this.getPosJ());
		Cell currCell = getGame().getCellAt(this.getPosI(), this.getPosJ());
		if(finalCell.getPiece() == null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			finalCell.getPiece().setPosI(finalDown);
			finalCell.getPiece().setPosJ(this.getPosJ());
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
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
		if(this.getPosJ() == 5)
			finalRight = 1;
		else
			if(this.getPosJ() == 4)
				finalRight = 0;
			else
				finalRight = this.getPosJ()+2; //changed this
		
		Cell finalCell = getGame().getCellAt(this.getPosI(), finalRight);
		Cell currCell = getGame().getCellAt(this.getPosI(), this.getPosJ());
		if(finalCell.getPiece() == null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			finalCell.getPiece().setPosI(this.getPosI());
			finalCell.getPiece().setPosJ(finalRight);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new OccupiedCellException("You Cannot Move into a Cell occupied by an ally" , this , Direction.RIGHT);
			else
			{
				//System.out.println(finalCell.toString());
				this.attack(finalCell.getPiece());
				System.out.println(finalCell.toString());
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
		if(this.getPosJ() == 0)
			finalLeft = 4;
		else
			if(this.getPosJ() == 1)
				finalLeft = 5;
			else
				finalLeft = this.getPosJ()-2; //changed this
		Cell finalCell = getGame().getCellAt(this.getPosI(), finalLeft);
		Cell currCell =getGame().getCellAt(this.getPosI(), this.getPosJ());
		if(finalCell.getPiece()==null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			finalCell.getPiece().setPosI(this.getPosI());
			finalCell.getPiece().setPosJ(finalLeft);
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
		int finalUpRighti = 0;
		if(this.getPosI() == 0)
			finalUpRighti = 5;
		else
			if(this.getPosI() == 1)
				finalUpRighti = 6;
			else
				finalUpRighti = this.getPosI()-2; //changed this
		int finalUpRightj = 0;
		if(this.getPosJ() == 5)
			finalUpRightj = 1;
		else
			if(this.getPosJ() == 4)
				finalUpRightj = 0;
			else
				finalUpRightj = this.getPosJ()+2; //changed this
		
		Cell finalCell = getGame().getCellAt(finalUpRighti, finalUpRightj);
		Cell currCell = getGame().getCellAt(this.getPosI(), this.getPosJ());
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
		int finalUpLefti = 0;
		if(this.getPosI() == 0)
			finalUpLefti = 5;
		else
			if(this.getPosI() == 1)
				finalUpLefti = 6;
			else
				finalUpLefti = this.getPosI()-2; //changed this
		int finalUpLeftj = 0;
		if(this.getPosJ() == 0)
			finalUpLeftj = 4;
		else
			if(this.getPosJ() == 1)
				finalUpLeftj = 5;
			else
				finalUpLeftj = this.getPosJ()-2; //changed this
		Cell finalCell = getGame().getCellAt(finalUpLefti, finalUpLeftj);
		Cell currCell = getGame().getCellAt(this.getPosI(), this.getPosJ());
		if(finalCell.getPiece() == null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			finalCell.getPiece().setPosI(finalUpLefti);
			finalCell.getPiece().setPosJ(finalUpLeftj);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
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
		int finalDownRighti = 0;
		if(this.getPosI() == 6)
			finalDownRighti = 1;
		else
			if(this.getPosI() == 5)
				finalDownRighti = 0;
			else
				finalDownRighti = this.getPosI()+2; //changed this
		
		int finalDownRightj = 0;
		if(this.getPosJ() == 5)
			finalDownRightj = 1;
		else
			if(this.getPosJ() == 4)
				finalDownRightj = 0;
			else
				finalDownRightj = this.getPosJ()+2; //changed this
		
		Cell finalCell  = getGame().getCellAt(finalDownRighti, finalDownRightj);
		Cell currCell = getGame().getCellAt(this.getPosI(), this.getPosJ());
		if(finalCell.getPiece()==null)
		{
			finalCell.setPiece(currCell.getPiece());
			currCell.setPiece(null);
			finalCell.getPiece().setPosI(finalDownRighti);
			finalCell.getPiece().setPosJ(finalDownRightj);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
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
		int finalDownLefti = 0;
		if(this.getPosI() == 6)
			finalDownLefti = 1;
		else
			if(this.getPosI() == 5)
				finalDownLefti = 0;
			else
				finalDownLefti = this.getPosI()+2; //changed this
		int finalDownLeftj = 0;
		if(this.getPosJ() == 0)
			finalDownLeftj = 4;
		else
			if(this.getPosJ() == 1)
				finalDownLeftj = 5;
			else
				finalDownLeftj = this.getPosJ()-2; //changed this
		
		Cell finalCell = getGame().getCellAt(finalDownLefti, finalDownLeftj);
		Cell currCell = getGame().getCellAt(this.getPosI() , this.getPosJ());
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
