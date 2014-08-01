package automate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class autoPage {
	 public autoPage()
	    {
	    	final JFrame parent = new JFrame("TianBot");
	        parent.setSize(400, 800);
	        parent.setVisible(true);
	        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //parent.pack();
	        
	        JPanel buttonPane = new JPanel();
	        buttonPane.setSize(300, 60);
	        buttonPane.setLocation(0,120);
	        
	        JButton button = new JButton();
	        button.setText("Translate");
	        button.setSize(100,20);
	        parent.add(button);
	        //parent.setVisible(true);

	        
	        buttonPane.add(button);
	        
	        
	        final JTextField storageLoc = new JTextField();
	        storageLoc.setSize(350, 20);
	        storageLoc.setLocation(0, 30);
	        storageLoc.setToolTipText("Example: http://delranis.ss5.sharpschool.com/");
	        parent.add(storageLoc);
	        new GhostText(storageLoc, "storageLoc");
	        
	        final JTextField host = new JTextField();
	        host.setSize(350, 20);
	        host.setLocation(0, 60);
	        host.setToolTipText("Example: sw-content-container5");
	        parent.add(host);
	        new GhostText(host, "host");
	        
	        final JTextField username = new JTextField();
	        username.setSize(350, 20);
	        username.setLocation(0, 90);
	        username.setToolTipText("Example: sw-content-container5");
	        parent.add(username);
	        new GhostText(username, "username");
	        
	        final JTextField password = new JTextField();
	        password.setSize(350, 20);
	        password.setLocation(0, 120);
	        password.setToolTipText("Example: sw-content-container5");
	        parent.add(password);
	        new GhostText(password, "password");
	        
	        final JTextField pageURL = new JTextField();
	        pageURL.setSize(350, 20);
	        pageURL.setLocation(0, 150);
	        pageURL.setToolTipText("Example: sw-content-container5");
	        parent.add(pageURL);
	        new GhostText(pageURL, "pageURL");
	        
	        final JTextArea htmlcontent = new JTextArea();
	        final JScrollPane sp = new JScrollPane(htmlcontent);
	        sp.setSize(350, 350);
	        sp.setLocation(0, 180);
	        sp.setToolTipText("Example: 3013453");
	        parent.add(sp);
	    //    new GhostText(htmlcontent, "Put HTML Content Here");
	        
	        
	       parent.add(buttonPane);
	       parent.repaint();
	       
	       button.addActionListener(new java.awt.event.ActionListener() {

	    	    @Override
	    	    public void actionPerformed(java.awt.event.ActionEvent evt) {
	    	    HtmlParser hp = new HtmlParser();
	    	    htmlcontent.setText(hp.Parse(htmlcontent.getText(), storageLoc.getText(), host.getText(), username.getText(), password.getText(), pageURL.getText()));

	       }

	       });
	    }
	 public static void main(String[] args)
		{
			try
			{
				new autoPage();
			}
			catch(Exception e)
			{
				JOptionPane.showInternalMessageDialog(null, "ERROR:"+e.getMessage());
			}
		}
}
