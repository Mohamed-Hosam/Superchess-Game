package model.pieces.heroes;

import java.awt.Point;

import exceptions.InvalidPowerDirectionException;
import exceptions.PowerAlreadyUsedException;
import exceptions.WrongTurnException;
import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;

public class Super extends ActivatablePowerHero {

	public Super(Player player, Game game, String name) {
		super(player, game, name);
	}

	@Override
	public String toString() {
		return "P";
	}
	
	public  int getUp()
	{
		if(this.getPosI() == 0)
			return -1;
		else
			return (this.getPosI()-1);
	}
	public int getDown()
	{
		if(this.getPosI() == 6)
			return -1;
		else
			return (this.getPosI()+1);
	}
	public int getRight()
	{
		if(this.getPosJ() == 5)
			return -1;
		else
			return (this.getPosJ()+1);
	}
	public int getLeft()
	{
		if(this.getPosJ() == 0)
			return -1;
		else
			return (this.getPosJ()-1);
	}
	@Override
	public void usePower(Direction d, Piece target, Point newPos) throws InvalidPowerDirectionException, PowerAlreadyUsedException, WrongTurnException {
		if(this.getGame().getCurrentPlayer().equals(this.getOwner()))
		{
			if(!(this.isPowerUsed()))
			{
				int up =  this.getUp();
				int down = this.getDown();
				int right = this.getRight();
				int left = this.getLeft();
				switch(d){
				case UP: this.usePowerUp(up, this.getPosJ(), d);this.getGame().switchTurns();break;
				case DOWN:this.usePowerDown(down, this.getPosJ(), d);this.getGame().switchTurns();break;
				case RIGHT:this.usePowerRight(this.getPosI(), right , d);this.getGame().switchTurns();break;
				case LEFT:this.usePowerLeft(this.getPosI() , left , d);this.getGame().switchTurns();break;
				case UPRIGHT:throw new InvalidPowerDirectionException("You cannot Use Power Here" , this , d);
				case UPLEFT:throw new InvalidPowerDirectionException("You cannot Use Power Here" , this , d);
				case DOWNRIGHT:throw new InvalidPowerDirectionException("You cannot Use Power Here" , this , d);
				case DOWNLEFT:throw new InvalidPowerDirectionException("You cannot Use Power Here" , this , d);
				}
			}
			else
				throw new PowerAlreadyUsedException("You have already used Your Power",this);
		}
		else
			throw new WrongTurnException("This is not your turn" , this);
		
	}
	

	public void usePowerUp(int i , int j , Direction d)
	{
		
		if(i == -1)
			this.setPowerUsed(true);
		else
		{
			Cell finalCell1 = this.getGame().getCellAt(i, j);
			if(finalCell1.getPiece() != null)
				if(!(finalCell1.getPiece().getOwner().equals(this.getOwner())))
				{
					this.attack(finalCell1.getPiece());
				}
			if(i > 0)//changed i from 1 to zero
			{
				Cell finalCell2 = this.getGame().getCellAt(i-1, j);
				if(finalCell2.getPiece() != null)
					if(!(finalCell2.getPiece().getOwner().equals(this.getOwner())))
					{
						this.attack(finalCell2.getPiece());
					}
			}
			this.setPowerUsed(true);
		}
	}
	public void usePowerDown(int i , int j , Direction d)
	{
		
		if(i == -1)
			this.setPowerUsed(true);
		else
		{
			Cell finalCell1 = this.getGame().getCellAt(i, j);
			if(finalCell1.getPiece() != null && !(finalCell1.getPiece().getOwner().equals(this.getOwner())))
				this.attack(finalCell1.getPiece());
			if(i <6) //changed from 5 to 6
			{
				Cell finalCell2 = this.getGame().getCellAt(i+1, j);
				if(finalCell2.getPiece()!=null)
					if(!(finalCell2.getPiece().getOwner().equals(this.getOwner())))
						this.attack(finalCell2.getPiece());
			}
			this.setPowerUsed(true);
		}
	}
	public void usePowerRight(int i , int j , Direction d) 
	{
		
		if(j == -1)
			this.setPowerUsed(true);
		else
		{
			Cell finalCell1 = this.getGame().getCellAt(i, j);
			if(finalCell1.getPiece()!=null)
				if(!(finalCell1.getPiece().getOwner().equals(this.getOwner())))
					this.attack(finalCell1.getPiece());
			if(j < 5)//changed from  4 to 5
			{
				Cell finalCell2 = this.getGame().getCellAt(i, j+1);
				if(finalCell2.getPiece()!=null)
					if(!(finalCell2.getPiece().getOwner().equals(this.getOwner())))
						this.attack(finalCell2.getPiece());
			}
			this.setPowerUsed(true);
		}
	}
	public void usePowerLeft(int i , int j , Direction d)
	{
		
		if(j == -1)
		{
			this.setPowerUsed(true);
		}
		else
		{
			Cell finalCell1 = this.getGame().getCellAt(i, j);
			if(finalCell1.getPiece()!=null)
				if(!(finalCell1.getPiece().getOwner().equals(this.getOwner())))
					this.attack(finalCell1.getPiece());
			if(j > 0) //changed  from 1 to zero
			{
				Cell finalCell2 = this.getGame().getCellAt(i, j-1);
				if(finalCell2.getPiece()!=null)
					if(!(finalCell2.getPiece().getOwner().equals(this.getOwner())))
						this.attack(finalCell2.getPiece());
			}
			this.setPowerUsed(true);
		}
	}
}
