package automate;


import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.dom4j.DocumentException;


public class BotGUI {
    public BotGUI()
    {
    	//sets title of the frame and initializes
    	final JFrame parent = new JFrame("TianBot");
        parent.setSize(400, 200);
        parent.setVisible(true);
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //parent.pack();
        
        //this is the start button's panel that prevents the button from covering the whole frame
        JPanel buttonPane = new JPanel();
        buttonPane.setSize(300, 60);
        buttonPane.setLocation(0,120);
        
        //initializes the button
        JButton button = new JButton();
        button.setText("Start");
        button.setSize(100,20);
        parent.add(button);
        //parent.setVisible(true);

        
        buttonPane.add(button);
        
        
        //adds the textfields for all of the inputs in the gui
        final JTextField news = new JTextField();
        news.setSize(350, 20);
        news.setLocation(0, 30);
        news.setToolTipText("Example: http://delranis.ss5.sharpschool.com/");
        parent.add(news);
        new GhostText(news, "new site");
        
        final JTextField contentID = new JTextField();
        contentID.setSize(350, 20);
        contentID.setLocation(0, 60);
        contentID.setToolTipText("Example: sw-content-container5");
        parent.add(contentID);
        new GhostText(contentID, "contentID");
        
        final JTextField storage = new JTextField();
        storage.setSize(350, 20);
        storage.setLocation(0, 90);
        storage.setToolTipText("Example: 3013453");
        parent.add(storage);
        new GhostText(storage, "server number");
        
       parent.add(buttonPane);
       parent.repaint();
       
       button.addActionListener(new java.awt.event.ActionListener() {

    	    //action listener activates this
    	    public void actionPerformed(java.awt.event.ActionEvent evt) {
    	    try {
				new Setup(news.getText(), contentID.getText(), storage.getText());
			} catch (IOException | DocumentException | InterruptedException e) {
				e.printStackTrace();
			}

       }

       });
    }
}
