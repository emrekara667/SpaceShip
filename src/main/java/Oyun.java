
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Emre
 */

class Ates{
    private int x;
    private  int y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}


public class Oyun extends JPanel implements KeyListener,ActionListener{
    
    private long passingTime = 0;
    private int firesFired = 0;
    
    private BufferedImage image;
    
    ArrayList<Ates> atesList = new ArrayList<>();
    
    private int fireDir = 1;
    
    private int ballX = 0; //Where the ball start
    
    private int ballDirX = 2; // number to be increased by by action performed 
    
    private int spaceShipX = 0;
    
    private int spaceShipDirX = 20; //number to be increased 
    
    Timer timer = new Timer(5, this);

    public Oyun() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("spaceShip.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setBackground(Color.black);
        passingTime = System.currentTimeMillis();
        
        timer.start();
    }
    
    public boolean isChecked(){
        
        for(Ates temp: atesList){
            if(new Rectangle(temp.getX(), temp.getY(), 5, 10).intersects(new Rectangle(ballX, 0, 20, 20)))
                return true;
        }
        return false; 
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        g.setColor(Color.red);
        g.fillOval(ballX, 0, 20, 20);
        g.drawImage(image, spaceShipX, 490, image.getWidth()/10, image.getHeight() / 10, this);
        
        g.setColor(Color.BLUE);
        
        
       for(Ates temp: atesList){
           if(temp.getY() < 0 ){
               atesList.remove(temp);
           }
       }
       
       for(Ates temp : atesList){
            g.fillRect(temp.getX(), temp.getY(), 5, 10);   
       }
       
       if(isChecked()){
           timer.stop();
           long finalTime = System.currentTimeMillis();
           String outString= "Won the game\n"
                   + "Lasting Time : " + (finalTime - passingTime) / 1000
                   + " sc.\n"
                   + "Fired fire : "
                   + firesFired;
           JOptionPane.showMessageDialog(this, outString);
           
           System.exit(0);
       }
        
        //g.setColor(Color.red);
        //g.drawRect(0, 72, 50, 50);
//        g.fillOval(0, 0, 50, 50);
    }

    @Override
    public void repaint() {
        super.repaint(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    
    
    
    
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
        //passingTime += 5;
        
        for(Ates temp: atesList){
                 temp.setY(temp.getY() - fireDir);
        }
        ballX += ballDirX;
        if(ballX >= 760){
            ballDirX = -ballDirX;
        }
        if(ballX <= 0){
            ballDirX = -ballDirX;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
       throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
      int press = e.getKeyCode();
       // System.out.println(e.getKeyChar());
        
        if(press == KeyEvent.VK_D){
            if(spaceShipX <= 0){
                spaceShipX = 0;
            }
            else{
                spaceShipX -= spaceShipDirX;
            }
        }
        else if(press == KeyEvent.VK_A){
            if(spaceShipX >= 740){
                spaceShipX = 740;
            }
            else{
                spaceShipX += spaceShipDirX;
            }
        }
        else if(press == KeyEvent.VK_CONTROL){
            atesList.add(new Ates(spaceShipX + 18, 480));
            firesFired++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
     throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
