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

public class Tech extends ActivatablePowerHero {

	public Tech(Player player, Game game, String name) {
		super(player, game, name);
	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public void usePower(Direction d, Piece target, Point newPos) throws InvalidPowerDirectionException,
			InvalidPowerTargetException, PowerAlreadyUsedException, WrongTurnException {
		if (this.getGame().getCurrentPlayer().equals(this.getOwner())) {
			if (!(this.isPowerUsed())) {
				if (newPos != null && target.getOwner() != this.getOwner())
					teleport(d, target, newPos);
				// throw new InvalidPowerTargetException("you can not teleport an enemy",this,
				// target);
				if (target.getOwner().equals(this.getOwner()) && newPos != null)
					this.teleport(d, target, newPos);
				else if (!(target.getOwner().equals(this.getOwner())))
					this.hack(target);
				else if (target.getOwner().equals(this.getOwner()) && newPos == null)
					this.restore(target);
			} else
				throw new PowerAlreadyUsedException("You already used your special Ability", this);
		} else
			throw new WrongTurnException("This is not your turn", this);

	}

	public void teleport(Direction d, Piece target, Point newPos) throws InvalidPowerTargetException {
		if (!(target.getOwner().equals(this.getOwner())))
			throw new InvalidPowerTargetException("You cannnot teleport an enemy piece", this, target);
		else {
			Cell targetCell = this.getGame().getCellAt(newPos.x, newPos.y);
			if (targetCell.getPiece() == null) {
				this.getGame().getCellAt(target.getPosI(), target.getPosJ()).setPiece(null);
				targetCell.setPiece(target);
				target.setPosI(newPos.x);
				target.setPosJ(newPos.y);
				this.setPowerUsed(true);
			} else if (targetCell.getPiece().getOwner().equals(this.getOwner())) {
				throw new InvalidPowerTargetException("You cannot teleport to a cell occupied by an ally", this,
						targetCell.getPiece());
			} else
				throw new InvalidPowerTargetException("You cannot teleport to a cell occupied by an enemy", this,
						targetCell.getPiece());
		}
	}

	public void hack(Piece target) throws InvalidPowerTargetException {
		if (target.getOwner().equals(this.getOwner()))
			throw new InvalidPowerTargetException("You cannot hack a friendly Piece", this, target);
		else {
			if (target instanceof Armored) {
				if (!((Armored) target).isArmorUp()) {
					throw new InvalidPowerTargetException("Armored Has already dropped it's armor", this, target);
				} else {
					((Armored) target).setArmorUp(false);
					this.setPowerUsed(true);
				}

			} else {
				if (((ActivatablePowerHero) target).isPowerUsed())
					throw new InvalidPowerTargetException("This target has already used it's power", this, target);
				else {
					((ActivatablePowerHero) target).setPowerUsed(true);
					this.setPowerUsed(true);
				}
			}
		}

	}

	public void restore(Piece target) throws InvalidPowerTargetException {
		if (!(target.getOwner().equals(this.getOwner())))
			throw new InvalidPowerTargetException("You cannot restore an enemy Piece's ability", this, target);
		else {
			if (target instanceof Armored) {
				if (((Armored) target).isArmorUp())
					throw new InvalidPowerTargetException("Armored Hasn't dropped it's armor yet", this, target);
				else {
					((Armored) target).setArmorUp(true);
					this.setPowerUsed(true);
				}
			} else {
				if (((ActivatablePowerHero) target).isPowerUsed()) {
					((ActivatablePowerHero) target).setPowerUsed(false);
					this.setPowerUsed(true);
				} else
					throw new InvalidPowerTargetException("This hero hasn't used it's ability yet", this, target);
			}

		}
	}

}
