package Team_Model;
/*
 * This class is used to create the frames to display the processing 
 * of 4 functions going on and a main frame that contains these 4 frames.
 */
 
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;
import java.awt.*;
 
public class GUIDisplay extends JFrame
  {
    JDesktopPane desktop;
    public JTextArea ta1,ta2,ta3,ta4;
    public GUIDisplay() {
        super("Team Model");
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0,
                  screenSize.width  - inset,
                  screenSize.height - inset);
 
        //Set up the GUI.
        desktop = new JDesktopPane(); 
        createFrame();
        setContentPane(desktop);
    }
 
   //Create 4 internal frames.
    void createFrame() {

    	InternalFrames frame1 = new InternalFrames(50,75,550,200);
    	InternalFrames frame2 = new InternalFrames(50,400,550,200);
    	InternalFrames frame3 = new InternalFrames(650,75,550,200);
    	InternalFrames frame4 = new InternalFrames(650,400,550,200);
        
        frame1.setVisible(true);
        frame2.setVisible(true);
        frame3.setVisible(true);
        frame4.setVisible(true);
        
        //Add frames to the main frame
        desktop.add(frame1);
        desktop.add(frame2);
        desktop.add(frame3);
        desktop.add(frame4);
        
        ta1 = new JTextArea();
		ta2 = new JTextArea();
		ta3 = new JTextArea();
		ta4 = new JTextArea();
		
		JScrollPane scroll1 = new JScrollPane(ta1);
		JScrollPane scroll2 = new JScrollPane(ta2);
		JScrollPane scroll3 = new JScrollPane(ta3);
		JScrollPane scroll4 = new JScrollPane(ta4);
		
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		DefaultCaret caret1 = (DefaultCaret)ta1.getCaret();
		DefaultCaret caret2 = (DefaultCaret)ta2.getCaret();
		DefaultCaret caret3 = (DefaultCaret)ta3.getCaret();
		DefaultCaret caret4 = (DefaultCaret)ta4.getCaret();
		
		caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); 
		caret3.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); 
		caret4.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); 
		
		frame1.getContentPane().add(scroll1);
		frame2.getContentPane().add(scroll2);
		frame3.getContentPane().add(scroll3);
		frame4.getContentPane().add(scroll4);
		
    }
 
 
  
}
