package automate;

import javax.swing.JOptionPane;

public class Run {

	public static void main(String[] args)
	{
		try
		{
			new BotGUI();
		}
		catch(Exception e)
		{
			JOptionPane.showInternalMessageDialog(null, "ERROR:"+e.getMessage());
		}
	}
}
