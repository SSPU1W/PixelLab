import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.ButtonGroup;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class Square extends JPanel
{
	private int red;
	private int green;
	private int blue;
	private Rectangle rectangle;

	public Square()
	{
	
		red = 255;
		green = 255;
		blue = 255;
	}

	public boolean contains(int a, int b)
	{
		return rectangle.contains(a, b);
	}

	public void drawMe(int x, int y, Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g.setColor(Color.black);
		g.drawRect(x,y, 50, 50);
		Color color = new Color(red, green, blue);
		g.setColor(color);
		g.fillRect(x, y, 50, 50);	
		rectangle = new Rectangle(x, y, 50, 50);
	}

	public void changeColor(int a, int b, int c)
	{
		red = a;
		green = b;
		blue = c;
	}	
}