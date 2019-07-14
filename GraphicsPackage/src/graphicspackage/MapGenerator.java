
package graphicspackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.omg.CORBA.PUBLIC_MEMBER;

public class MapGenerator {
    int map[][];
	 int brickWidth,brickHeight,row,colm;
	 
	public MapGenerator(int row, int colm) {
		 map=new int[row][colm];
		 for(int i=0;i<map.length;i++) {
			 for(int j=0;j<map[0].length;j++) {
				 map[i][j]=1;
			 }
		 }
		 brickWidth=50;
		 brickHeight=50;
	 }
	 public void draw(Graphics2D g2) {
		 for(int i=0;i<map.length;i++) {
			 for(int j=0;j<map[0].length;j++) {
				if(map[i][j]>0) {
					if(j%2==1) g2.setColor(Color.YELLOW);
					else g2.setColor(Color.RED);
					g2.fillRect(j*brickWidth+180, i*brickHeight+150, brickWidth, brickHeight);
					
					g2.setStroke(new BasicStroke(3));
					g2.setColor(Color.BLACK);
					g2.drawRect(j*brickWidth+180, i*brickHeight+150, brickWidth, brickHeight);
				}
			 }
		 }
	 }
	 
	 public void brickcrash(int value,int row, int colm) {
		 map[row][colm]=value;
	 }
	 }

