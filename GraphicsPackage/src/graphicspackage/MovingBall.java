
package graphicspackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.JPanel;

public class MovingBall extends JPanel implements Runnable,ActionListener,KeyListener{
	
	private Timer t;
	private int delay=5;
	private boolean play=false;
	private int scoreDown=0,scoreUp=0,bathit=1;
	private int totalbricks=10;
	private int playerX=310,playerY=310;
	private int ballposX=120;
	private int ballposY=450;
	private int balldirX=-1;
	private int balldirY=-1;
	private BufferedImage image;
	public MapGenerator map;
	public boolean batdown=false,batup=false;
	
	public MovingBall() {
		
		try {
			image=ImageIO.read(getClass().getResourceAsStream("wall.jpg"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		map=new MapGenerator(2,5);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		t=new Timer(delay, this);
		t.start();
	}
	
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		//Graphics2D g2=(Graphics2D) g;
		
		//Background
		g2.drawImage(image,0,0,image.getWidth(),image.getHeight(),null);
		//g2.setColor(Color.BLACK);
		//g2.fillRect(1,1,692,592);
		
		//draw map
		map.draw((Graphics2D)g2);
		
		//Border
		g2.setColor(Color.RED);
		g2.fillRect(0,0,692,3);
		g2.fillRect(0,0,3,592);
		g2.fillRect(691,0,3,592);
		
		//score
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("serif", Font.BOLD, 25));
		g2.drawString(""+scoreUp+"/"+scoreDown, 590, 30);
		
		//Ball
		g2.setColor(Color.RED);
		//Ellipse2D circle2=new Ellipse2D.Double(ballposX,ballposY,20,20);
		g2.fillOval(ballposX,ballposY,20,20);
		
		//Paddle
		g2.setColor(Color.BLUE);
		
		Rectangle2D rect2=new Rectangle2D.Double(playerY,10,100,8);
		g2.fillRect(playerY,10,100,8);
		
		
		Rectangle2D rect1=new Rectangle2D.Double(playerX,550,100,8);
		g2.fillRect(playerX,550,100,8);
		
		if(totalbricks==0) {
			play=false;
			balldirX=0;
			balldirY=0;
			g2.setColor(Color.RED);
			g2.setFont(new Font("serif", Font.BOLD, 30));
			g2.drawString("YOU WON", 270, 300);
			
			g2.setFont(new Font("serif", Font.BOLD, 25));
			g2.drawString("Press Enter To Restart", 230, 350);
		}
		
		if(ballposY>570 || ballposY<-16 ||scoreUp>=5 || scoreDown>=5) {
			play=false;
			balldirX=0;
			balldirY=0;
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("serif", Font.BOLD, 30));
			g2.drawString("GAME OVER,SCORES : "+scoreUp+"/"+scoreDown, 170, 300);
                        
                        g2.drawString("Upper player scored:"+scoreUp,230,350);
                          g2.drawString("lower player scored:"+scoreDown,230,400);
			
			g2.setFont(new Font("serif", Font.BOLD, 25));
			//g2.drawString("Press Enter To Restart", 230, 350);
		}

		//g.dispose();
		g2.dispose();

	}
	
	public void actionPerformed(ActionEvent e) {
	
		t.start();
		
		if(play) {
			
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				balldirY=-balldirY;
				bathit=1;
			}
			
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerY, 10, 100, 8))) {
				balldirY=-balldirY;
				bathit=2;
			}
			
			A : for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
					int brickX=j*map.brickWidth+180;
					int brickY=i*map.brickHeight+150;
					int brickWidth=map.brickWidth;
					int brickHeight=map.brickHeight;
					
					Rectangle rect=new Rectangle(brickX, brickY, brickWidth, brickHeight);
					Rectangle ballrect=new Rectangle(ballposX, ballposY, 20, 20);
					Rectangle brickrect=rect;
					
					if(ballrect.intersects(brickrect)) {
						map.brickcrash(0, i, j);
						totalbricks--;
						if(bathit==1)
							scoreDown+=1;
						if(bathit==2) scoreUp+=1;
						
						if(ballposX+19<=brickrect.x || ballposX+1>=brickrect.x + brickrect.width) {
							balldirX=-balldirX;
						}
						else {
							balldirY=-balldirY;
						}
						break A;
					}
				}
			}
			}
			
			ballposX+=balldirX;
			ballposY+=balldirY;
			if(ballposX<=0) balldirX=-balldirX;
			//if(ballposY<0) balldirY=-balldirY;
			if(ballposX>=670) balldirX=-balldirX;
		}
		
		repaint();
		
	}
		
		
		
		@Override
		public void run() {
			
			batdown=true;
			batup=true;
			
		}

		
		@Override
		public void keyPressed(KeyEvent e) {
			
			
			if(e.getKeyCode()== KeyEvent.VK_RIGHT && batdown) {
				if(playerX>=600)
					playerX=600;
				else
					moveRight();
			}
			if(e.getKeyCode( )== KeyEvent.VK_LEFT && batdown) {
				if(playerX<10)
					playerX=10;
				else
					moveLeft();
			}
			
			if(e.getKeyCode()== KeyEvent.VK_D && batup) {
				if(playerY>=600)
					playerY=600;
				else
					moveRightY();
			}
			if(e.getKeyCode( )== KeyEvent.VK_A && batup) {
				if(playerY<10)
					playerY=10;
				else
					moveLeftY();
			}
			
		
		if(e.getKeyChar()==KeyEvent.VK_ENTER) {
			if(!play) {
			play=true;
			ballposX=120;
			ballposY=350;
			balldirX=-1;
			balldirY=-1;
			playerX=310;
			playerY=210;
			scoreUp=0;
			scoreDown=0;
			totalbricks=10;
			map=new MapGenerator(2, 5);
			
			repaint();
			}
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void moveRight(){
		play=true;
		playerX+=40;
	}
	public void moveLeft(){
		play=true;
		playerX -=40;	
	}
	
	public void moveRightY(){
		play=true;
		playerY+=40;
	}
	public void moveLeftY(){
		play=true;
		playerY-=40;
	}

	

}