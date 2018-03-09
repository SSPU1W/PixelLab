import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class Screen extends JPanel implements ActionListener, MouseListener
{
	private Square[][] pixelArt;
	private int red;
	private int green;
	private int blue;
	private int Xx = 50; 
	private int Yy = 50;
	Color[] colors = new Color[12];
	private Square[] palette;
	private Color selectedColor;
	private JButton clearButton;
	private JButton saveButton;
    private Font font2;
	Scanner kb = new Scanner(System.in);
    
    

	public Screen()
	{
		
		palette = new Square[12];
		for (int p = 0; p < 12; p++)
		{
			palette[p] = new Square();
		}
		colors[0] = new Color(255,0,0);
		colors[1] = new Color(0,255,0);
		colors[2] = new Color(0,0,255);
		colors[3] = new Color(0,255,255);
		colors[4] = new Color(0,128,0);
        colors[5] = new Color(121,72,31);  
		colors[6] = new Color(151,72,93); 
		colors[7] = new Color(32,181,100);
		colors[8] = new Color(40,100,211);
		colors[9] = new Color(50,210,82);
		colors[10] = new Color(192,102,105);
		colors[11] = new Color(255,255,255);
		
        font2 = new Font("Helvetica", Font.BOLD, 30);  
		addMouseListener(this);
		this.setLayout(null);
		clearButton = new JButton("Clear");
		clearButton.setFont(new Font("Futura", Font.BOLD, 40));
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.RED);
        clearButton.setBounds(1000,700,175,50);
        clearButton.addActionListener(this);
        this.add(clearButton);
		saveButton = new JButton("Save As A PNG");
		saveButton.setFont(new Font("Futura", Font.BOLD, 20));
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.RED);
        saveButton.setBounds(950,750,300,50);
        saveButton.addActionListener(this);
        this.add(saveButton);
		reset();
	}

	private void reset()
	{
		System.out.println("Enter in the Row Value (Max 25)");
		int x = kb.nextInt();
		System.out.println("Enter in the Col Value (Max 25)");
		int y = kb.nextInt();
		if(x <= 18 && y <= 18)
		{
			pixelArt = new Square[x][y];
			for(int r = 0; r<pixelArt.length; r++)
			{
				for(int c = 0; c<pixelArt[r].length; c++)
				{
					pixelArt[r][c] = new Square();
					
				}
			}
		}
		else
		{
			Xx = 40;
			Yy  = 40;
			pixelArt = new Square[x][y];
			for(int r = 0; r<pixelArt.length; r++)
			{
				for(int c = 0; c<pixelArt[r].length; c++)
				{
					pixelArt[r][c] = new Square();
					
				}
			}
		}
	}

	public Dimension getPreferredSize()
    {		
        
        return new Dimension(1920,1080);
    }
 
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
       
        g.setColor(Color.gray);
        g.fillRect(0,0,1920,1080);
		drawPixels(g);
		drawPalette(g);
        g.setFont(font2); 
        g.setColor(Color.BLACK);
        g.drawString("Eraser Tool", 1200, 650);
		
    }

    private void drawPalette(Graphics g)
    {
		for (int p = 0; p < 12; p++)
		{
			int r = colors[p].getRed();
			int gg = colors[p].getGreen();
			int b = colors[p].getBlue();
			palette[p].changeColor(r, gg, b);			
			palette[p].drawMe(1000, 50 + p * 50, g);
		}
    }

    private void drawPixels(Graphics g) 
    {
 		for(int r = 0; r<pixelArt.length; r++)
		{
			for(int c = 0; c<pixelArt[r].length; c++)
			{
				pixelArt[r][c].drawMe(Xx*(r+1), Yy*(c+1), g);
			}
		}
 	}


    public void mouseReleased(MouseEvent e) {}
 
    public void mouseEntered(MouseEvent e) {}
 
    public void mouseExited(MouseEvent e) {}
 
    public void mouseClicked(MouseEvent e){}

    public void mousePressed(MouseEvent e) 
    {
    	int x = e.getX();
    	int y = e.getY();

    	int idx = getColor(x, y);
    	if (idx >= 0)
    	{
    		selectedColor = colors[idx];
    	} 
    	else 
    	{
	    	Square selectedSquare = getSquare(x,y);
	    	if (selectedSquare != null) 
	    	{
				int r = selectedColor.getRed();
				int g = selectedColor.getGreen();
				int b = selectedColor.getBlue();
		    	selectedSquare.changeColor(r, g, b);
				repaint();	
	    	}    		
    	}
    }

    private int getColor(int x, int y)
    {
    	for (int p = 0; p < 6; p++)
    	{
    		if (palette[p].contains(x, y))
    		{
    			return p;
    		}
    	}

    	return -1;
    }

    private Square getSquare(int x, int y)
    {
 		for(int r = 0; r<pixelArt.length; r++)
		{
			for(int c = 0; c<pixelArt[r].length; c++)
			{
	    		if (pixelArt[r][c].contains(x, y))
    			{
    				return pixelArt[r][c];
    			}
			}
		}
		return null;
    }
 
 
    public void actionPerformed(ActionEvent e) 
    {
        if( e.getSource() == clearButton )
        {
        	reset();
			repaint();
        }
        if (e.getSource() == saveButton) 
        {
        	saveImage();
        }
    } 
    
    private void saveImage()
    {
    	File file = new File("picture.png");
    	BufferedImage bi = new BufferedImage(450, 
    				450, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        this.paint(g);
        g.dispose();
        try
        {
            ImageIO.write(bi,"png",file);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
     
}