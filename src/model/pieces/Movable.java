package model.pieces;

import exceptions.InvalidMovementException;
import exceptions.WrongTurnException;
import model.game.Direction;

public interface Movable {
	void move(Direction r) throws InvalidMovementException, WrongTurnException;
	void moveUp() throws InvalidMovementException;
	void moveDown() throws InvalidMovementException;
	void moveLeft() throws InvalidMovementException;
	void moveRight() throws InvalidMovementException;
	void moveDownLeft() throws InvalidMovementException;
	void moveDownRight() throws InvalidMovementException;
	void moveUpLeft() throws InvalidMovementException;
	void moveUpRight() throws InvalidMovementException;
	
}
