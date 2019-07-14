
package graphicspackage;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
		
		JFrame frame=new JFrame();
		MovingBall m=new MovingBall();
		Thread playerdouwn=new Thread(m);
		Thread playerup=new Thread(m);
		
		try {
			playerdouwn.start();
			playerup.start();
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		
		frame.add(m);
		frame.setVisible(true);
		frame.setBounds(300,50,700,600);
		frame.setTitle("Moving Ball");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	
	}
}
