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

public class Ranged extends ActivatablePowerHero {

	public Ranged(Player player, Game game, String name) {
		super(player, game, name);
	}

	public String toString() {
		return "R";
	}

	@Override
	public void usePower(Direction d, Piece target, Point newPos) throws InvalidPowerDirectionException, PowerAlreadyUsedException, WrongTurnException {
		if(this.getGame().getCurrentPlayer().equals(this.getOwner()))
		{
			if(!(this.isPowerUsed()))
			{
				switch(d) {
				case UP: this.usePowerUp(d);this.getGame().switchTurns();break;
				case DOWN:this.usePowerDown(d);this.getGame().switchTurns();break;
				case RIGHT:this.userPowerRight(d);this.getGame().switchTurns();break;
				case LEFT:this.userPowerLeft(d);this.getGame().switchTurns();break;
				default:throw new InvalidPowerDirectionException("You cannot use your ability in this direction" ,this , d );
				}
			}
			else
				throw new PowerAlreadyUsedException("You already used your ability",this);
		}
		else
			throw new WrongTurnException("This is not your turn" , this);
		
	}
	public void usePowerUp(Direction d) throws InvalidPowerDirectionException
	{
		Cell finalCell = this.getGame().getCellAt(this.getPosI(), this.getPosJ());
		Point p1 = new Point(this.getPosI() , this.getPosJ());
		for(int i = this.getPosI()-1;i>=0;i--) //added -1
		{
			finalCell = this.getGame().getCellAt(i, this.getPosJ());
			if(finalCell.getPiece() != null)
			{
				break;
			}
		}
		if(finalCell.getPiece() == null)
			throw new InvalidPowerDirectionException("You will not hit any enemies if you use your ability in this direction" , this ,d);
		else
		{
			Point p2 = new Point(finalCell.getPiece().getPosI() , finalCell.getPiece().getPosJ());
			if(!(p1.equals(p2))&& !(finalCell.getPiece().getOwner().equals(this.getOwner())))
			{
				this.attack(finalCell.getPiece());
				this.setPowerUsed(true);
			}
			else
			{
				if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				{
					this.setPowerUsed(false);
					throw new InvalidPowerDirectionException("You cannot use your power in this direction" , this ,d );
				}
//				else
//					if(p1.equals(p2))
//						this.setPowerUsed(false);
			}
		}
	}
	public void usePowerDown(Direction d) throws InvalidPowerDirectionException {
		Cell finalCell = this.getGame().getCellAt(this.getPosI(), this.getPosJ());
		Point p1 = new Point(this.getPosI() , this.getPosJ());
		for(int i = this.getPosI()+1;i<=6;i++) //added +1
		{
			finalCell = this.getGame().getCellAt(i, this.getPosJ());
			if(finalCell.getPiece() != null)
				break;
		}
		if(finalCell.getPiece() == null)
			throw new InvalidPowerDirectionException("You will not hit any enemies if you use your ability in this direction" , this ,d);
		else
		{
			Point p2 = new Point(finalCell.getPiece().getPosI() , finalCell.getPiece().getPosJ());
			if(!(p1.equals(p2))&& !(finalCell.getPiece().getOwner().equals(this.getOwner())))
			{
				this.attack(finalCell.getPiece());
				this.setPowerUsed(true);
			}
			else
			{
				if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				{
					this.setPowerUsed(false);
					throw new InvalidPowerDirectionException("You cannot use your power in this direction" , this ,d );
				}
			}
		}
	}
	public void userPowerRight(Direction d) throws InvalidPowerDirectionException
	{
		Cell finalCell = this.getGame().getCellAt(this.getPosI(), this.getPosJ());
		Point p1 = new Point(this.getPosI() , this.getPosJ());
		for(int i = this.getPosJ()+1;i<=5;i++) //added +1
		{
			finalCell = this.getGame().getCellAt(this.getPosI(), i);
			if(finalCell.getPiece() != null)
				break;
		}
		if(finalCell.getPiece() == null)
			throw new InvalidPowerDirectionException("You will not hit any enemies if you use your ability in this direction" , this ,d);
		else
		{
			Point p2 = new Point(finalCell.getPiece().getPosI() , finalCell.getPiece().getPosJ());
			if(!(p1.equals(p2))&& !(finalCell.getPiece().getOwner().equals(this.getOwner())))
			{
				this.attack(finalCell.getPiece());
				this.setPowerUsed(true);
			}
			else
			{
				if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				{
					this.setPowerUsed(false);
					throw new InvalidPowerDirectionException("You cannot use your power in this direction" , this ,d );
				}
//				else
//					if(p1.equals(p2))
//						this.setPowerUsed(false);
			}
		}
	}
	public void userPowerLeft(Direction d)throws InvalidPowerDirectionException
	{
		Cell finalCell = this.getGame().getCellAt(this.getPosI(), this.getPosJ());
		Point p1 = new Point(this.getPosI() , this.getPosJ());
		for(int i = this.getPosJ()-1;i>=0;i--) //aded -1
		{
			finalCell = this.getGame().getCellAt(this.getPosI(), i);
			if(finalCell.getPiece() != null)
				break;
		}
		if(finalCell.getPiece() == null)
			throw new InvalidPowerDirectionException("You will not hit any enemies if you use your ability in this direction" , this ,d);
		else
		{
			Point p2 = new Point(finalCell.getPiece().getPosI() , finalCell.getPiece().getPosJ());
			if(!(p1.equals(p2))&& !(finalCell.getPiece().getOwner().equals(this.getOwner())))
			{
				this.attack(finalCell.getPiece());
				this.setPowerUsed(true);
			}
			else
			{
				if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				{
					this.setPowerUsed(false);
					throw new InvalidPowerDirectionException("You cannot use your power in this direction" , this ,d );
				}
//				else
//					if(p1.equals(p2))
//						this.setPowerUsed(false);
			}
		}
	}
}
