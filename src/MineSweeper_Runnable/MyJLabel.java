package MineSweeper_Runnable;

import java.awt.Graphics;
import javax.swing.*;

@SuppressWarnings("serial")
public class MyJLabel extends JLabel
{
    ImageIcon imageIcon;
    
    public MyJLabel() {
    	super();
    }
    
    public MyJLabel(ImageIcon icon)
    {
        super();
        this.imageIcon = icon;
    }
    
    public void setImageIcon(ImageIcon imageIcon) {
    	this.imageIcon = imageIcon;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(),0,0,getWidth(),getHeight(),this);
    }
}