package MineSweeper_Runnable;

import java.awt.Graphics;
import javax.swing.*;

@SuppressWarnings("serial")
public class MyJButton extends JButton
{
    ImageIcon imageIcon;
    public MyJButton() {
    	super();
    }
    public MyJButton(ImageIcon imageIcon)
    {
        super();
        this.imageIcon = imageIcon;
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