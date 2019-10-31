import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MenuFrame extends JFrame{
	private MenuPanel menuPanel;
	  
    public MenuFrame() {
	   super("FuriousPlant     by 41group");
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	   menuPanel=new MenuPanel();
	   menuPanel.addMouseListener(menuPanel);
	   menuPanel.addMouseMotionListener(menuPanel);
       getContentPane().add(menuPanel, BorderLayout.CENTER);
       
       setSize(925,650);   
	   setLocationRelativeTo(null);
	}
    

}
