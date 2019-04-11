package game.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import exceptions.InvalidPowerDirectionException;
import exceptions.InvalidPowerTargetException;
import exceptions.OccupiedCellException;
import exceptions.PowerAlreadyUsedException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import model.pieces.heroes.ActivatablePowerHero;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKick;

public class GameView extends JFrame implements ActionListener {
	private Game game;
	private JButton[][] gameBoard = new JButton[7][6];
	private ImagePanel Board = new ImagePanel("Images\\BG1.png");
	private final ImageIcon icon = new ImageIcon("Images\\Icon.jpg");
	private ImagePanel infoPanel = new ImagePanel("Images\\BG1.png"); //changed from BG3.jpg to BG1.png
	private JPanel btnsPnl = new JPanel();
	private JLabel currPiece = new JLabel();
	private Point firstClick = null;
	private JProgressBar Progress1 = new JProgressBar();
	private JProgressBar Progress2 = new JProgressBar();
	private ImagePanel pnlP1 = new ImagePanel("Images\\pnlBackground.jpg");
	private ImagePanel pnlP2 = new ImagePanel("Images\\pnlBackground.jpg");
	private JPanel DeadP1 = new JPanel(); //for dead Characters of Player 1
	private JPanel DeadP2 = new JPanel(); //for Dead character of player 2
	private JButton usePower = new JButton();
	private JButton currP = new JButton(); //el button el b-indicate le current player
	private boolean usePowerFlag = false;
	private boolean techSelection = false;
	private boolean techRestore = false;
	private boolean techHack = false;
	private boolean techTeleport1 = false;
	private boolean techTeleport2 = false;
	private ArrayList<JButton> DeadCharsP1 = new ArrayList<JButton>();
	private ArrayList<JButton> DeadCharsP2 = new ArrayList<JButton>();
	private boolean medicSelection = false;
	private Point techTarget = null;
	String medicDirection = "";
	public GameView() throws IOException {
//---------------------------------------------------------------------------------------------------------------
		// Setting all the basic window settings
		this.setTitle("Super Hero Chess");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(270, 20, 800, 700);
		this.setIconImage(icon.getImage());
		this.setResizable(false);
		// this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
//---------------------------------------------------------------------------------------------------------------
		// Asking for input and creating the game and setting PayLoad Bars
		String P1 = JOptionPane.showInputDialog("Enter The Name Of the First Player");
		while (P1.isEmpty())
			P1 = JOptionPane.showInputDialog("Enter The Name Of the First Player");
		String P2 = JOptionPane.showInputDialog("Enter The Name Of the Second Player");
		while (P2.isEmpty())
			P2 = JOptionPane.showInputDialog("Enter The Name Of the Second Player");
		Player PlayerOne = new Player(P1);
		Player PlayerTwo = new Player(P2);
		game = new Game(PlayerOne, PlayerTwo);
		this.setProgresBar();
//---------------------------------------------------------------------------------------------------------------
		// setting the pieces
		Board.setPreferredSize(new Dimension(this.getWidth(), 400));
		Board.setVisible(true);
		this.setPieces();
		this.add(Board, BorderLayout.CENTER);
//---------------------------------------------------------------------------------------------------------------
		//setting the info Panel and the current player's name
		infoPanel.setPreferredSize(new Dimension(this.getWidth(), 100)); //5alet-ha 100
		infoPanel.setLayout(new BorderLayout());
		currP.setText(game.getCurrentPlayer().getName() + "'s Turn");
		usePower.addActionListener(this);
		usePower.setContentAreaFilled(false);
		usePower.setBorderPainted(false);
		ImageIcon usePowerIcon = new ImageIcon("Images\\ult.png");
		usePower.setActionCommand("Use Power");
		usePower.setIcon(usePowerIcon);
		infoPanel.add(usePower , BorderLayout.EAST);
		infoPanel.add(currPiece , BorderLayout.WEST);
//---------------------------------------------------------------------------------------------------------------	
		// setting pnl of each player to dead Characters and Payload bars and dead character panels
		pnlP1.setLayout(new BorderLayout());
		pnlP2.setLayout(new BorderLayout());
		pnlP1.add(Progress1 , BorderLayout.WEST );
		pnlP2.add(Progress2 , BorderLayout.EAST);
		DeadP1.setLayout(new GridLayout(6,2));
		DeadP2.setLayout(new GridLayout(6,2));
		DeadP1.setOpaque(false);
		DeadP2.setOpaque(false);
		pnlP1.add(DeadP1 , BorderLayout.EAST);
		pnlP2.add(DeadP2 , BorderLayout.WEST);
		this.add(pnlP1 , BorderLayout.EAST);
		this.add(pnlP2, BorderLayout.WEST);
		
//---------------------------------------------------------------------------------------------------------------
		//Setting the btnsPnl and currentPiece
//		currPiece.setMinimumSize(new Dimension(100,70));
//		currPiece.setPreferredSize(new Dimension(100,70));
//		currPiece.setMaximumSize(new Dimension(100,70));
		this.setButtonsPnl();
		infoPanel.add(btnsPnl, BorderLayout.CENTER);
		this.add(infoPanel, BorderLayout.SOUTH);
		this.setVisible(true);
		repaint();
		this.validate();
	}

	public void setPieces() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (game.getCellAt(i, j).getPiece() == null) {
					gameBoard[i][j] = new JButton();
					gameBoard[i][j].setContentAreaFilled(false);
					gameBoard[i][j].setBorderPainted(true);
					gameBoard[i][j].addActionListener(this);
					Board.add(gameBoard[i][j]);
				} else {
					String x = game.getCellAt(i, j).getPiece().getName();
					switch (x) {
					case "Hanzo":
						gameBoard[i][j] = new JButton();
						ImageIcon img = new ImageIcon("Images\\Hanzo.gif");
						setButton(i,j,img);
						break;

					case "Sombra":
						gameBoard[i][j] = new JButton();
						ImageIcon img2 = new ImageIcon("Images\\Sombra.gif");
						setButton(i,j,img2);
						break;

					case "D.Va":
						gameBoard[i][j] = new JButton();
						ImageIcon img3 = new ImageIcon("Images\\Dva.gif");
						setButton(i,j,img3);
						break;

					case "Reinhardt":
						gameBoard[i][j] = new JButton();
						ImageIcon img4 = new ImageIcon("Images\\rein.gif");
						setButton(i,j,img4);
						break;
					case "Mercy":
						gameBoard[i][j] = new JButton();
						ImageIcon img5 = new ImageIcon("Images\\Mercy.gif");
						setButton(i,j,img5);
						break;

					case "Tracer":
						gameBoard[i][j] = new JButton();
						ImageIcon img6 = new ImageIcon("Images\\Tracer.gif");
						setButton(i,j,img6);
						break;

					case "Symmetra":
						gameBoard[i][j] = new JButton();
						ImageIcon img7 = new ImageIcon("Images\\Symm.png");
						setButton(i,j,img7);
						break;

					case "Zarya":
						gameBoard[i][j] = new JButton();
						ImageIcon img8 = new ImageIcon("Images\\Zarya.gif");
						setButton(i,j,img8);
						break;

					case "Zenyatta":
						gameBoard[i][j] = new JButton();
						ImageIcon img9 = new ImageIcon("Images\\Zenn.gif");
						setButton(i,j,img9);
						break;

					case "Genji":
						gameBoard[i][j] = new JButton();
						ImageIcon img10 = new ImageIcon("Images\\Genji.gif");
						setButton(i,j,img10);
						break;

					case "Pharah":
						gameBoard[i][j] = new JButton();
						ImageIcon img11 = new ImageIcon("Images\\Pharah.gif");
						setButton(i,j,img11);
						break;

					case "Winston":
						gameBoard[i][j] = new JButton();
						ImageIcon img12 = new ImageIcon("Images\\Winston.gif");
						setButton(i,j,img12);
						break;

					case "Torb":
						gameBoard[i][j] = new JButton();
						ImageIcon img13 = new ImageIcon("Images\\Torb.gif");
						setButton(i,j,img13);
						Board.add(gameBoard[i][j]);
						break;

					case "Minion":
						gameBoard[i][j] = new JButton();
						ImageIcon img14 = new ImageIcon("Images\\SymmP2.png");
						setButton(i,j,img14);
						break;
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new GameView();
	}

	class ImagePanel extends JPanel {

		private Image img;

		public ImagePanel(String img) {
			this(new ImageIcon(img).getImage());
		}

		public ImagePanel(Image img) {
			this.img = img;
			// Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
			// setPreferredSize(size);
			// setMinimumSize(size);
			// setMaximumSize(size);
			// setSize(size);
			setLayout(new GridLayout(7, 6));
		}

		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		try {
			if(b.getParent().equals(btnsPnl) && firstClick != null && !(usePowerFlag) && game.getCellAt(firstClick.x, firstClick.y).getPiece() != null) //i added a5r condition (movement)
			{
				System.out.println("hi");
				Cell currCell = game.getCellAt(firstClick.x, firstClick.y);
				Piece currHero = currCell.getPiece();
				String s = b.getActionCommand();
				switch(s) 
				{
				case "Up" : currHero.move(Direction.UP);break;
				case "Down": currHero.move(Direction.DOWN);break;
				case "Left" : currHero.move(Direction.LEFT);break;
				case "Right" : currHero.move(Direction.RIGHT);break;
				case "Up Left": currHero.move(Direction.UPLEFT);break;
				case "Up Right": currHero.move(Direction.UPRIGHT);break;
				case "Down Left" : currHero.move(Direction.DOWNLEFT);break;
				case "Down Right" :currHero.move(Direction.DOWNRIGHT);break;
				}
				this.refresh();
				System.out.println(game.checkWinner());
				if(game.checkWinner())
				{
					JOptionPane.showMessageDialog(this, game.getCurrentPlayer().getName() + " Wins");
					this.dispose();
				}
			}
			else {
				System.out.println(789);
				if(b.getParent().equals(Board) && !techSelection && !medicSelection)
				{
					System.out.println("Hello");
					Point p = search(b);
					firstClick = new Point(p.x,p.y);
					Piece firstPiece = game.getCellAt(firstClick.x, firstClick.y).getPiece();
					usePowerFlag = false; //added deh (2.0) 3ashan case lama mercy use power then clicks on piece
					if(firstPiece != null)
						currPiece.setText(this.getInfo(firstPiece));
				}
				else
				{
					if(b.getParent().equals(infoPanel) && !(game.getCellAt(firstClick.x, firstClick.y).getPiece() instanceof Tech) && game.getCellAt(firstClick.x, firstClick.y).getPiece() != null ) // I added a5r condition (movement)
					{
						if(game.getCellAt(firstClick.x, firstClick.y).getPiece() instanceof SideKick ||
								game.getCellAt(firstClick.x, firstClick.y).getPiece() instanceof Armored ||
								game.getCellAt(firstClick.x, firstClick.y).getPiece() instanceof Speedster)
						{
							JOptionPane.showMessageDialog(this, "This Character has no Super Power");
							return;
						}
						else
						{
							JOptionPane.showMessageDialog(this, "Please Choose A Direction");
							usePowerFlag = true;
							System.out.println(91011);
						}
					}
					else
					{
						if(b.getParent().equals(btnsPnl) && firstClick != null && usePowerFlag || (b.getParent().equals(DeadP1)||b.getParent().equals(DeadP2) || b.getParent().equals(Board)) && firstClick!=null && usePowerFlag) // added b.getParent.equals(Board)
						{
							System.out.println("1");
							Piece currHero = game.getCellAt(firstClick.x, firstClick.y).getPiece();
							if(currHero instanceof ActivatablePowerHero)
							{
								System.out.println("123");
								if(!(currHero instanceof Medic))
								{
									String x = b.getActionCommand();
									switch(x)
									{
									case "Up" : ((ActivatablePowerHero)currHero).usePower(Direction.UP , null , null );break;
									case "Down": ((ActivatablePowerHero)currHero).usePower(Direction.DOWN , null , null );break;
									case "Left" : ((ActivatablePowerHero)currHero).usePower(Direction.LEFT , null , null );break;
									case "Right" : ((ActivatablePowerHero)currHero).usePower(Direction.RIGHT , null , null );break;
									case "Up Left": ((ActivatablePowerHero)currHero).usePower(Direction.UPLEFT , null , null );break;
									case "Up Right": ((ActivatablePowerHero)currHero).usePower(Direction.UPRIGHT , null , null );break;
									case "Down Left" : ((ActivatablePowerHero)currHero).usePower(Direction.DOWNLEFT , null , null );break;
									case "Down Right" :((ActivatablePowerHero)currHero).usePower(Direction.DOWNRIGHT , null , null );break;
									}
									this.refresh();
								}
								else
								{
									if(!medicSelection)
									{
										System.out.println("leh");
										JOptionPane.showMessageDialog(this, "Please Choose A Target");
										medicDirection = b.getActionCommand();
										medicSelection = true;
									}
									else
									{
										System.out.println("henlo1");
										Point medicTargetPos = null;
										if(b.getParent().equals(Board))
										{
											medicTargetPos = search(b);
											Cell CurrCell = game.getCellAt(medicTargetPos.x, medicTargetPos.y);
											System.out.println("henlo");
											if(CurrCell.getPiece() != null)
											{
												switch(medicDirection)
												{
												case "Up" : ((ActivatablePowerHero)currHero).usePower(Direction.UP , CurrCell.getPiece() , null );break;
												case "Down": ((ActivatablePowerHero)currHero).usePower(Direction.DOWN , CurrCell.getPiece() , null );break;
												case "Left" : ((ActivatablePowerHero)currHero).usePower(Direction.LEFT , CurrCell.getPiece() , null );break;
												case "Right" : ((ActivatablePowerHero)currHero).usePower(Direction.RIGHT , CurrCell.getPiece() , null );break;
												case "Up Left": ((ActivatablePowerHero)currHero).usePower(Direction.UPLEFT , CurrCell.getPiece() , null );break;
												case "Up Right": ((ActivatablePowerHero)currHero).usePower(Direction.UPRIGHT , CurrCell.getPiece() , null );break;
												case "Down Left" : ((ActivatablePowerHero)currHero).usePower(Direction.DOWNLEFT , CurrCell.getPiece() , null );break;
												case "Down Right" :((ActivatablePowerHero)currHero).usePower(Direction.DOWNRIGHT , CurrCell.getPiece() , null );break;
												}
											}
											else
											{
												JOptionPane.showMessageDialog(this, "This Is Not A Valid Target");
											}
											this.refresh();
											medicSelection = false;
										}
										else
										{
											int index = 0;
											System.out.println("hehehehe");
											if(b.getParent().equals(DeadP1))
											{
												index = DeadCharsP1.indexOf(b);
												System.out.println(index);
												Piece CurrCell = game.getPlayer1().getDeadCharacters().get(index);
												switch(medicDirection)
												{
												case "Up" : ((ActivatablePowerHero)currHero).usePower(Direction.UP , CurrCell , null );break;
												case "Down": ((ActivatablePowerHero)currHero).usePower(Direction.DOWN , CurrCell , null );break;
												case "Left" : ((ActivatablePowerHero)currHero).usePower(Direction.LEFT , CurrCell , null );break;
												case "Right" : ((ActivatablePowerHero)currHero).usePower(Direction.RIGHT , CurrCell , null );break;
												case "Up Left": ((ActivatablePowerHero)currHero).usePower(Direction.UPLEFT , CurrCell , null );break;
												case "Up Right": ((ActivatablePowerHero)currHero).usePower(Direction.UPRIGHT , CurrCell , null );break;
												case "Down Left" : ((ActivatablePowerHero)currHero).usePower(Direction.DOWNLEFT , CurrCell , null );break;
												case "Down Right" :((ActivatablePowerHero)currHero).usePower(Direction.DOWNRIGHT , CurrCell , null );break;
												}

												DeadCharsP1.remove(index);
												medicSelection = false;
												this.refresh();
											}
											if(b.getParent().equals(DeadP2))
											{
												index = DeadCharsP2.indexOf(b);
												Piece CurrCell = game.getPlayer2().getDeadCharacters().get(index);
												switch(medicDirection)
												{
												case "Up" : ((ActivatablePowerHero)currHero).usePower(Direction.UP , CurrCell , null );break;
												case "Down": ((ActivatablePowerHero)currHero).usePower(Direction.DOWN , CurrCell , null );break;
												case "Left" : ((ActivatablePowerHero)currHero).usePower(Direction.LEFT , CurrCell , null );break;
												case "Right" : ((ActivatablePowerHero)currHero).usePower(Direction.RIGHT , CurrCell , null );break;
												case "Up Left": ((ActivatablePowerHero)currHero).usePower(Direction.UPLEFT , CurrCell , null );break;
												case "Up Right": ((ActivatablePowerHero)currHero).usePower(Direction.UPRIGHT , CurrCell , null );break;
												case "Down Left" : ((ActivatablePowerHero)currHero).usePower(Direction.DOWNLEFT , CurrCell , null );break;
												case "Down Right" :((ActivatablePowerHero)currHero).usePower(Direction.DOWNRIGHT , CurrCell , null );break;
												}
												DeadCharsP2.remove(index);
												medicSelection = false;
												this.refresh();
											}
											
										}
									}
								}
							}
						}
						else
						{
							if(b.getParent().equals(infoPanel) && firstClick != null)
							{
								System.out.println("2");
								Piece currHero = game.getCellAt(firstClick.x, firstClick.y).getPiece();
								if(currHero instanceof Tech)
								{
									if(!techSelection)
										this.getTechPower();
								}
								
							}
							else
							{
								Piece currHero = game.getCellAt(firstClick.x, firstClick.y).getPiece();
								System.out.println(3);
								if(b.getParent().equals(Board)&&techSelection)
								{
									System.out.println("xxxxxx");
									if(techTeleport1)
									{
										techTarget = search(b);
										Cell techTargetHero = game.getCellAt(techTarget.x, techTarget.y);
										if(techTargetHero.getPiece() != null)
										{
											techTeleport1 = false;
											techTeleport2 = true;
											JOptionPane.showMessageDialog(this, "Choose a position");
											System.out.println("tech selection is " + techSelection);
										}
										else
										{
											JOptionPane.showMessageDialog(this, "You Cannot Teleport An Empty Cell");
											return;
										}
									}
									else
									{
										System.out.println(4);
										if(techHack)
										{
											techTarget = search(b);
											Cell techTargetHero = game.getCellAt(techTarget.x, techTarget.y);
											if(techTargetHero.getPiece() != null && !(techTargetHero.getPiece() instanceof Speedster) && !(techTargetHero.getPiece() instanceof SideKick))
											{
												((ActivatablePowerHero)currHero).usePower(null,techTargetHero.getPiece() , null);
												techHack = false;
												this.refresh();
												usePowerFlag = false;
												techSelection = false;
											}
											else
											{
												if(techTargetHero.getPiece() instanceof Speedster || techTargetHero.getPiece() instanceof SideKick)
												{
													JOptionPane.showMessageDialog(this, "This Piece has no Special Ability");
													return;
												}
												else
												{
													JOptionPane.showMessageDialog(this, "You Cannot Hack An Empty Cell");
													return;
												}
											}
										}
										if(techRestore)
										{
											techTarget = search(b);
											Cell techTargetHero = game.getCellAt(techTarget.x, techTarget.y);
											if(techTargetHero.getPiece() != null && !(techTargetHero.getPiece() instanceof Speedster) && !(techTargetHero.getPiece() instanceof SideKick))
											{
												((ActivatablePowerHero)currHero).usePower(null,techTargetHero.getPiece() ,null);
												techRestore = false;
												usePowerFlag = false;
												this.refresh();
												techSelection = false;
											}
											else
											{
												if(techTargetHero.getPiece() instanceof Speedster || techTargetHero.getPiece() instanceof SideKick)
												{
													JOptionPane.showMessageDialog(this, "This Piece has no Special ability");
													return;
												}
												else
												{
													JOptionPane.showMessageDialog(this, "You Cannot Restore An Empty cell's Power");
													return;
												}
											}
										}
										if(techTeleport2)
										{
											techTeleport2 = false;
											Point teleportTarget = search(b);
											System.out.println(teleportTarget.toString());
											((ActivatablePowerHero)currHero).usePower(null,game.getCellAt(techTarget.x,techTarget.y).getPiece() , teleportTarget);
											this.refresh();
											usePowerFlag = false;
											techSelection = false;
										}
									}
									
								}
								
							}
						}
					}
				}
			}
		} catch (OccupiedCellException e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage());
		}
		// gameBoard[(p.x)-1][p.y].setIcon(b.getIcon());
		// gameBoard[p.x][p.y].setIcon(null);
		catch (UnallowedMovementException e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage());
		} catch (WrongTurnException e1) {
			usePowerFlag = false;
			techSelection = false;  //added all the flag resets here
			techTeleport1 = false;
			techTeleport2 = false;
			techHack = false;
			techRestore = false;
			medicSelection = false;
			JOptionPane.showMessageDialog(this, e1.getMessage());
		} catch (InvalidPowerDirectionException e1) {
			usePowerFlag = false; //added the flag reset here
			medicSelection = false;
			JOptionPane.showMessageDialog(this,e1.getMessage());
		} catch (InvalidPowerTargetException e1) {
			usePowerFlag = false;
			techSelection = false;  //added all the flag resets here
			techTeleport1 = false;
			techTeleport2 = false;
			techHack = false;
			techRestore = false;
			medicSelection = false;
			JOptionPane.showMessageDialog(this,e1.getMessage());
		} catch (PowerAlreadyUsedException e1) {
			usePowerFlag = false;
			techSelection = false;  //added all the flag resets here
			techTeleport1 = false;
			techTeleport2 = false;
			techHack = false;
			techRestore = false;
			medicSelection = false;
			JOptionPane.showMessageDialog(this,e1.getMessage());
		}

	}

	public Point search(JButton b) {
		int x = 0;
		int y = 0;
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 6; j++) {
				if (gameBoard[i][j] != null) {
					if (gameBoard[i][j].equals(b)) {
						x = i;
						y = j;
					}
				}
			}
		return new Point(x, y);
	}
	public void refresh()
	{
		this.remove(Board);
		this.Board = new ImagePanel("Images\\BG1.png");
		this.setPieces();
		this.add(Board);
		double perc1 = (game.getPlayer1().getPayloadPos()*100.0)/6.0;
		Progress1.setValue((int)Math.ceil(perc1));
		Progress1.updateUI();
		double perc2 = (game.getPlayer2().getPayloadPos()*100.0)/6.0;
		Progress2.setValue((int)Math.ceil(perc2));
		Progress2.updateUI();
		this.searchDeadCharactersP1();
		this.searchDeadCharactersP2();
		currP.setText(game.getCurrentPlayer().getName()+ "'s Turn");
		usePowerFlag = false;
		this.repaint();
		this.validate();
	}
	public void setButton(int i , int j , ImageIcon img)
	{
		gameBoard[i][j].setIcon(img);
		gameBoard[i][j].setVisible(true);
		gameBoard[i][j].setContentAreaFilled(false);
		gameBoard[i][j].addActionListener(this);
		Board.add(gameBoard[i][j]);
	}
	public void setButtonsPnl()
	{
		JButton up = new JButton();
		ImageIcon upIcon = new ImageIcon("Images\\up.png");
		up.setIcon(upIcon);
		up.setActionCommand("Up");
//		 up.setPreferredSize(new Dimension(10,10));
//		 up.addActionListener( this);
//		 up.setContentAreaFilled(false);
//		 up.setBorderPainted(false);
		JButton down = new JButton();
		ImageIcon downIcon = new ImageIcon("Images\\down.png");
		down.setIcon(downIcon);
		down.setActionCommand("Down");
//		 down.setPreferredSize(new Dimension(10,10));
//		 down.addActionListener( this);
//		 down.setContentAreaFilled(false);
//		 up.setBorderPainted(false);
		JButton left = new JButton();
		ImageIcon leftIcon = new ImageIcon("Images\\left.png");
		left.setIcon(leftIcon);
		left.setActionCommand("Left");
//		 left.setPreferredSize(new Dimension(10,10));
//		 left.addActionListener( this);
//		 left.setContentAreaFilled(false);
//		 up.setBorderPainted(b);
		JButton right = new JButton();
		ImageIcon rightIcon = new ImageIcon("Images\\right.png");
		right.setIcon(rightIcon);
		right.setActionCommand("Right");
//		 right.setPreferredSize(new Dimension(10,10));
//		 right.addActionListener( this);
//		 right.setContentAreaFilled(false);
		JButton upLeft = new JButton();
		ImageIcon upleftIcon = new ImageIcon("Images\\upleft.png");
		upLeft.setIcon(upleftIcon);
		upLeft.setActionCommand("Up Left");
//		 upLeft.setPreferredSize(new Dimension(10,10));
//		 upLeft.addActionListener( this);
//		 upLeft.setContentAreaFilled(false);
		JButton upRight = new JButton();
		ImageIcon upRightIcon = new ImageIcon("Images\\upright.png");
		upRight.setIcon(upRightIcon);
		upRight.setActionCommand("Up Right");
//		 upRight.setPreferredSize(new Dimension(10,10));
//		 upRight.addActionListener( this);
//		 upRight.setContentAreaFilled(false);
		JButton downLeft = new JButton();
		ImageIcon downLeftIcon = new ImageIcon("Images\\downleft.png");
		downLeft.setIcon(downLeftIcon);
		downLeft.setActionCommand("Down Left");
//		 downLeft.setPreferredSize(new Dimension(10,10));
//		 downLeft.addActionListener( this);
//		 downLeft.setContentAreaFilled(false);
		JButton downRight = new JButton();
		ImageIcon downRightIcon = new ImageIcon("Images\\downright.png");
		downRight.setIcon(downRightIcon);
		downRight.setActionCommand("Down Right");
//		 downRight.setPreferredSize(new Dimension(10,10));
//		 downRight.addActionListener( this);
//		 downRight.setContentAreaFilled(false);
//		usePower.addActionListener(this);
//		usePower.setContentAreaFilled(false);
		currP.setContentAreaFilled(false);
		currP.setBorderPainted(false);
		btnsPnl.setOpaque(false);
//		btnsPnl.setMinimumSize(new Dimension(600,70));
//		btnsPnl.setPreferredSize(new Dimension(600, 70));
//		btnsPnl.setMaximumSize(new Dimension(600,70));
		btnsPnl.setLayout(new GridLayout(3, 3));
//		btnsPnl.add(upLeft);
//		btnsPnl.add(up);
//		btnsPnl.add(upRight);
//		btnsPnl.add(left);
//		btnsPnl.add(usePower);
//		btnsPnl.add(right);
//		btnsPnl.add(downLeft);
//		btnsPnl.add(down);
//		btnsPnl.add(downRight);
//		btnsPnl.setVisible(true);
		JButton btns[] = {upLeft,up,upRight,left,currP,right,downLeft,down,downRight};
		for(int i = 0;i<btns.length;i++)
		{
			 if(btns[i].equals(currP))
			 {
				 btns[i].setPreferredSize(new Dimension(10,10));
				 btns[i].setContentAreaFilled(false);
				 btns[i].setBorderPainted(false);
				 btnsPnl.add(btns[i]);
			 }
			 else
			 {
				 btns[i].setPreferredSize(new Dimension(10,10));
				 btns[i].addActionListener(this);
				 btns[i].setContentAreaFilled(false);
				 btns[i].setBorderPainted(false);
				 btnsPnl.add(btns[i]);
			 }
		}
	}
	public void setProgresBar()
	{
		Progress1.setPreferredSize(new Dimension(40,this.getHeight()));
		Progress2.setPreferredSize(new Dimension(40,this.getHeight()));
		Progress1.setStringPainted(true);
		Progress2.setStringPainted(true);
		Progress1.setOrientation(SwingConstants.VERTICAL);
		Progress2.setOrientation(SwingConstants.VERTICAL);
		Progress1.setOpaque(false);
		Progress2.setOpaque(false);
		Progress1.setString(game.getPlayer1().getName());
		Progress2.setString(game.getPlayer2().getName());
	}
	public void searchDeadCharactersP1()
	{
		pnlP1.remove(DeadP1);
		DeadP1 = new JPanel();
		DeadP1.setOpaque(false);
		DeadP1.setLayout(new GridLayout(6,2));
		ArrayList<Piece> a = game.getPlayer1().getDeadCharacters(); 
		DeadCharsP1 = new ArrayList<JButton>();
		for(int i = 0; i<a.size();i++)
		{
			String x = a.get(i).getName();
			JButton b = new JButton();
			b.setContentAreaFilled(false);;
			b.setPreferredSize(new Dimension(50,50));
			b.addActionListener(this);
			switch(x)
			{
			case "Hanzo":
				ImageIcon img = new ImageIcon("Images\\Hanzo.gif");
				b.setIcon(img);
				DeadCharsP1.add(b);
				break;

			case "Sombra":
				ImageIcon img2 = new ImageIcon("Images\\Sombra.gif");
				b.setIcon(img2);
				DeadCharsP1.add(b);
				break;

			case "D.Va":
				ImageIcon img3 = new ImageIcon("Images\\Dva.gif");
				b.setIcon(img3);
				DeadCharsP1.add(b);
				break;

			case "Reinhardt":
				ImageIcon img4 = new ImageIcon("Images\\rein.gif");
				b.setIcon(img4);
				DeadCharsP1.add(b);
				break;
			case "Mercy":
				ImageIcon img5 = new ImageIcon("Images\\Mercy.gif");
				b.setIcon(img5);
				DeadCharsP1.add(b);
				break;

			case "Tracer":
				ImageIcon img6 = new ImageIcon("Images\\Tracer.gif");
				b.setIcon(img6);
				DeadCharsP1.add(b);
				break;

			case "Symmetra":
				ImageIcon img7 = new ImageIcon("Images\\Symm.png");
				b.setIcon(img7);
				DeadCharsP1.add(b);
				break;
				
			case "Zarya":
				ImageIcon img8 = new ImageIcon("Images\\Zarya.gif");
				b.setIcon(img8);
				DeadCharsP1.add(b);
				break;

			case "Zenyatta":
				ImageIcon img9 = new ImageIcon("Images\\Zenn.gif");
				b.setIcon(img9);
				DeadCharsP1.add(b);
				break;

			case "Genji":
				ImageIcon img10 = new ImageIcon("Images\\Genji.gif");
				b.setIcon(img10);
				DeadCharsP1.add(b);
				break;

			case "Pharah":
				ImageIcon img11 = new ImageIcon("Images\\Pharah.gif");
				b.setIcon(img11);
				DeadCharsP1.add(b);
				break;

			case "Winston":
				ImageIcon img12 = new ImageIcon("Images\\Winston.gif");
				b.setIcon(img12);
				DeadCharsP1.add(b);
				break;

			case "Torb":
				ImageIcon img13 = new ImageIcon("Images\\Torb.gif");
				b.setIcon(img13);
				DeadCharsP1.add(b);
				break;

			case "Minion":
				ImageIcon img14 = new ImageIcon("Images\\SymmP2.png");
				b.setIcon(img14);
				DeadCharsP1.add(b);
				break;
			}
			DeadP1.add(b);
		}
		pnlP1.add(DeadP1);
	}
	public void searchDeadCharactersP2()
	{
		pnlP2.remove(DeadP2);
		DeadP2 = new JPanel();
		DeadP2.setOpaque(false);
		DeadP2.setLayout(new GridLayout(6,2));
		ArrayList<Piece> a = game.getPlayer2().getDeadCharacters();
		DeadCharsP2 = new ArrayList<JButton>();
		for(int i = 0; i<a.size();i++)
		{
			String x = a.get(i).getName();
			JButton b = new JButton();
			b.setContentAreaFilled(false);
			b.addActionListener(this);
			b.setPreferredSize(new Dimension(50,50));
			switch(x)
			{
			case "Zarya":
				ImageIcon img8 = new ImageIcon("Images\\Zarya.gif");
				b.setIcon(img8);
				DeadCharsP2.add(b);
				break;

			case "Zenyatta":
				ImageIcon img9 = new ImageIcon("Images\\Zenn.gif");
				b.setIcon(img9);
				DeadCharsP2.add(b);
				break;

			case "Genji":
				ImageIcon img10 = new ImageIcon("Images\\Genji.gif");
				b.setIcon(img10);
				DeadCharsP2.add(b);
				break;

			case "Pharah":
				ImageIcon img11 = new ImageIcon("Images\\Pharah.gif");
				b.setIcon(img11);
				DeadCharsP2.add(b);
				break;

			case "Winston":
				ImageIcon img12 = new ImageIcon("Images\\Winston.gif");
				b.setIcon(img12);
				DeadCharsP2.add(b);
				break;

			case "Torb":
				ImageIcon img13 = new ImageIcon("Images\\Torb.gif");
				b.setIcon(img13);
				DeadCharsP2.add(b);
				break;

			case "Minion":
				ImageIcon img14 = new ImageIcon("Images\\SymmP2.png");
				b.setIcon(img14);
				DeadCharsP2.add(b);
				break;
				
			case "Hanzo":
				ImageIcon img = new ImageIcon("Images\\Hanzo.gif");
				b.setIcon(img);
				DeadCharsP2.add(b);
				break;

			case "Sombra":
				ImageIcon img2 = new ImageIcon("Images\\Sombra.gif");
				b.setIcon(img2);
				DeadCharsP2.add(b);
				break;

			case "D.Va":
				ImageIcon img3 = new ImageIcon("Images\\Dva.gif");
				b.setIcon(img3);
				DeadCharsP2.add(b);
				break;

			case "Reinhardt":
				ImageIcon img4 = new ImageIcon("Images\\rein.gif");
				b.setIcon(img4);
				DeadCharsP2.add(b);
				break;
			case "Mercy":
				ImageIcon img5 = new ImageIcon("Images\\Mercy.gif");
				b.setIcon(img5);
				DeadCharsP2.add(b);
				break;

			case "Tracer":
				ImageIcon img6 = new ImageIcon("Images\\Tracer.gif");
				b.setIcon(img6);
				DeadCharsP2.add(b);
				break;

			case "Symmetra":
				ImageIcon img7 = new ImageIcon("Images\\Symm.png");
				b.setIcon(img7);
				DeadCharsP2.add(b);
				break;
			}
			DeadP2.add(b);
			
		}
		pnlP2.add(DeadP2);
	}
	
	public void getTechPower()
	{
		Object options[] = {"Hack" , "Teleport" , "Restore"};
		int n = JOptionPane.showOptionDialog(this, "Please Choose The Special Ability", "Choose An Ability ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, options , null);
		if(n == 0)
		{
			JOptionPane.showMessageDialog(this, "Choose A Target");
			techHack = true;
		}
		if(n == 1)
		{
			techTeleport1 =  true;
			JOptionPane.showMessageDialog(this, "Choose A Target");
		}
		if(n == 2)
		{
			JOptionPane.showMessageDialog(this, "Choose A Target");
			techRestore = true;
		}
		if(n != -1)
		{
			techSelection = true;
		}
		System.out.println("hehe");
		
	}
	public String getInfo(Piece p)
	{
		String Owner = "<html>Owner: "+p.getOwner().getName();
		String type = "";
		String Power = "";
		if(p instanceof Armored)
		{
			type = "<br/>Type: Armored";
			if(((Armored) p).isArmorUp())
				Power = "<br/>Armor is Up</html>";
			else
				Power = "<br/>Armor is Down</html>";
			return Owner  + type  + Power;
		}
		if(!(p instanceof Speedster) && !(p instanceof SideKick))
		{
			if(p instanceof Tech)
				type = "<br/>Type: Tech";
			if(p instanceof Medic)
				type = "<br/>Type: Medic";
			if(p instanceof Ranged)
				type = "<br/>Type :Ranged";
			if(p instanceof Super)
				type = "<br/>Type :Super";
			
			if(((ActivatablePowerHero)p).isPowerUsed())
				Power = "<br/>Power Used</html>";
			else
				Power = "<br/>Power Ready</html>";
			
		}
		else
		{
			if(p instanceof Speedster)
				type = "<br/>Type: Speedster";
			if(p instanceof SideKick)
				type = "<br/>Type: SideKick";
			Power = "<br/>No Power</html>";
		}
		return Owner + type + Power;
	}

}
