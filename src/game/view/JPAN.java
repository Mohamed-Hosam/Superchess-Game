package game.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class JPAN extends JPanel {
	Image img;
	
	public JPAN()
	{
		img = Toolkit.getDefaultToolkit().createImage("C:\\Users\\Ahmad\\Desktop\\Game\\BG1");
	}
	public void paintComponent(Graphics g)
	{
		 g.drawImage(img,0,0,null); 
	}

}
