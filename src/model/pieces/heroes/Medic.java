package model.pieces.heroes;

import java.awt.Point;

import exceptions.InvalidPowerDirectionException;
import exceptions.InvalidPowerTargetException;
import exceptions.PowerAlreadyUsedException;
import exceptions.WrongTurnException;
import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;

public class Medic extends ActivatablePowerHero {

	public Medic(Player player, Game game, String name) {
		super(player, game, name);
	}

	@Override
	public String toString() {
		return "M";
	}
	
	public int getUp()
	{
		if(this.getPosI() == 0)
			return 6;
		else
			return (this.getPosI()-1);
	}
	public int getDown()
	{
		if(this.getPosI() == 6)
			return 0;
		else
			return (this.getPosI()+1);
	}
	public int getRight()
	{
		if(this.getPosJ() == 5)
			return 0;
		else
			return (this.getPosJ()+1);
	}
	public int getLeft()
	{
		if(this.getPosJ() == 0)
			return 5;
		else
			return (this.getPosJ()-1);
	}
	@Override
	public void usePower(Direction d, Piece target, Point newPos) throws InvalidPowerDirectionException, InvalidPowerTargetException, PowerAlreadyUsedException, WrongTurnException {
		if(this.getGame().getCurrentPlayer().equals(this.getOwner()))
		{
			if(!(this.isPowerUsed()))
			{
				if(this.getOwner().getDeadCharacters().contains(target))
				{
					int up = this.getUp();
					int down = this.getDown();
					int right = this.getRight();
					int left = this.getLeft();
					switch(d) {
					case UP:this.usePowerUp(up,target);break;
					case DOWN:this.usePowerDown(down, target);break;
					case RIGHT:this.usePowerRight(right, target);break;
					case LEFT:this.usePowerLeft(left, target);break;
					case UPRIGHT:this.usePowerUpRight(up, right, target);break;
					case UPLEFT:this.usePowerUpLeft(up, left, target);break;
					case DOWNRIGHT:this.usePowerDownRight(down, right, target);break;
					case DOWNLEFT:this.usePowerDownLeft(down, left, target);break;
					}
				}
				else
					if(target.getOwner().equals(this.getOwner()))
					{
						throw new InvalidPowerTargetException("You cannot revive an Alive Friendly Piece" , this , target);
					}
					else
					{
						throw new InvalidPowerTargetException("You cannot revive An Enemy Piece" , this , target);
					}
			}
			else
				throw new PowerAlreadyUsedException(this);
			int n = this.getOwner().getDeadCharacters().indexOf(target);
			if(n != -1)
				this.getOwner().getDeadCharacters().remove(n);
			this.getGame().switchTurns();
		}
		else
			throw new WrongTurnException("This is not your turn" , this);
		
	}
	
	public void usePowerUp(int up , Piece p) throws InvalidPowerTargetException
	{
		Cell finalCell = this.getGame().getCellAt(up, this.getPosJ());
		if(finalCell.getPiece() == null)
		{
			if(p instanceof Armored)
				((Armored)p).setArmorUp(true);
			if(p instanceof ActivatablePowerHero)
				((ActivatablePowerHero) p).setPowerUsed(false);
			finalCell.setPiece(p);
			p.setPosI(up);
			p.setPosJ(this.getPosJ());
			this.setPowerUsed(true);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new InvalidPowerTargetException("You cannot revive in a cell occupied by an ally" , this , p);
			else
				throw new InvalidPowerTargetException("You cannot revive in a cell Occupied by an enemy" , this , p);
	}
	public void usePowerDown(int down , Piece p) throws InvalidPowerTargetException
	{
		Cell finalCell = this.getGame().getCellAt(down, this.getPosJ());
		if(finalCell.getPiece() == null)
		{
			if(p instanceof Armored)
				((Armored)p).setArmorUp(true);
			if(p instanceof ActivatablePowerHero)
				((ActivatablePowerHero) p).setPowerUsed(false);
			finalCell.setPiece(p);
			p.setPosI(down);
			p.setPosJ(this.getPosJ());
			this.setPowerUsed(true);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new InvalidPowerTargetException("You cannot revive in a cell occupied by an ally" , this , p);
			else
				throw new InvalidPowerTargetException("You cannot revive in a cell Occupied by an enemy" , this , p);
	}
	public void usePowerRight(int right , Piece p) throws InvalidPowerTargetException
	{
		Cell finalCell = this.getGame().getCellAt(this.getPosI(), right);
		if(finalCell.getPiece() == null)
		{
			if(p instanceof Armored)
				((Armored)p).setArmorUp(true);
			if(p instanceof ActivatablePowerHero)
				((ActivatablePowerHero) p).setPowerUsed(false);
			finalCell.setPiece(p);
			p.setPosI(this.getPosI());
			p.setPosJ(right);
			this.setPowerUsed(true);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new InvalidPowerTargetException("You cannot revive in a cell occupied by an ally" , this , p);
			else
				throw new InvalidPowerTargetException("You cannot revive in a cell Occupied by an enemy" , this , p);
	}
	public void usePowerLeft(int left , Piece p)throws InvalidPowerTargetException
	{
		Cell finalCell = this.getGame().getCellAt(this.getPosI() ,  left);
		if(finalCell.getPiece() == null)
		{
			if(p instanceof Armored)
				((Armored)p).setArmorUp(true);
			if(p instanceof ActivatablePowerHero)
				((ActivatablePowerHero) p).setPowerUsed(false);
			finalCell.setPiece(p);
			p.setPosI(this.getPosI());
			p.setPosJ(left);
			this.setPowerUsed(true);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new InvalidPowerTargetException("You cannot revive in a cell occupied by an ally" , this , p);
			else
				throw new InvalidPowerTargetException("You cannot revive in a cell Occupied by an enemy" , this , p);
	}
	public void usePowerUpRight(int up , int right , Piece p) throws InvalidPowerTargetException{
		Cell finalCell = this.getGame().getCellAt(up, right);
		if(finalCell.getPiece() == null)
		{
			if(p instanceof Armored)
				((Armored)p).setArmorUp(true);
			if(p instanceof ActivatablePowerHero)
				((ActivatablePowerHero) p).setPowerUsed(false);
			finalCell.setPiece(p);
			p.setPosI(up);
			p.setPosJ(right);
			this.setPowerUsed(true);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new InvalidPowerTargetException("You cannot revive in a cell occupied by an ally" , this , p);
			else
				throw new InvalidPowerTargetException("You cannot revive in a cell Occupied by an enemy" , this , p);
	}
	public void usePowerUpLeft(int up , int left , Piece p) throws InvalidPowerTargetException
	{
		Cell finalCell = this.getGame().getCellAt(up, left);
		if(finalCell.getPiece() == null)
		{
			if(p instanceof Armored)
				((Armored)p).setArmorUp(true);
			if(p instanceof ActivatablePowerHero)
				((ActivatablePowerHero) p).setPowerUsed(false);
			finalCell.setPiece(p);
			p.setPosI(up);
			p.setPosJ(left);
			this.setPowerUsed(true);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new InvalidPowerTargetException("You cannot revive in a cell occupied by an ally" , this , p);
			else
				throw new InvalidPowerTargetException("You cannot revive in a cell Occupied by an enemy" , this , p);
	}
	public void usePowerDownRight(int down , int right , Piece p) throws InvalidPowerTargetException
	{
		Cell finalCell = this.getGame().getCellAt(down, right);
		if(finalCell.getPiece() == null)
		{
			if(p instanceof Armored)
				((Armored)p).setArmorUp(true);
			if(p instanceof ActivatablePowerHero)
				((ActivatablePowerHero) p).setPowerUsed(false);
			finalCell.setPiece(p);
			p.setPosI(down);
			p.setPosJ(right);
			this.setPowerUsed(true);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new InvalidPowerTargetException("You cannot revive in a cell occupied by an ally" , this , p);
			else
				throw new InvalidPowerTargetException("You cannot revive in a cell Occupied by an enemy" , this , p);
	}
	public void usePowerDownLeft(int down , int left , Piece p) throws InvalidPowerTargetException
	{
		Cell finalCell = this.getGame().getCellAt(down, left);
		if(finalCell.getPiece() == null)
		{
			if(p instanceof Armored)
				((Armored)p).setArmorUp(true);
			if(p instanceof ActivatablePowerHero)
				((ActivatablePowerHero) p).setPowerUsed(false);
			finalCell.setPiece(p);
			p.setPosI(down);
			p.setPosJ(left);
			this.setPowerUsed(true);
		}
		else
			if(finalCell.getPiece().getOwner().equals(this.getOwner()))
				throw new InvalidPowerTargetException("You cannot revive in a cell occupied by an ally" , this , p);
			else
				throw new InvalidPowerTargetException("You cannot revive in a cell Occupied by an enemy" , this , p);
	}
	
}
