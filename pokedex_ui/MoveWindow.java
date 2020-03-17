package pokedex_ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import pokedex.Pokedex;
import pokedex.Move;

public class MoveWindow extends JFrame {

	Move aMove ;
	Pokedex aPokedex ;
	MoveSearchWindow anMSW ;
	
	public MoveWindow(Pokedex pPokedex, MoveSearchWindow msw, Move pMove)
	{
		aMove = pMove ;
		anMSW = msw ;
		aPokedex = pPokedex ;
		initialize() ;
	}
	
	private void initialize()
	{
		BorderLayout bl = new BorderLayout() ;
		
		bl.setVgap(5);
		
		setLayout(bl) ;
		
		JPanel panel = new JPanel() ;
		GridLayout gl = new GridLayout(1,2) ;
		panel.setLayout(gl) ;
		
	
		
		JPanel headers = new JPanel() ;
		headers.setLayout(new GridLayout(8,1));
		
		//headers.add(new JLabel("Level")) ;
		headers.add(new JLabel("Name")) ;
		headers.add(new JLabel("Type")) ;
		headers.add(new JLabel("Power")) ;
		headers.add(new JLabel("Category")) ;
		headers.add(new JLabel("Accuracy")) ;
		headers.add(new JLabel("PP")) ;
		headers.add(new JLabel("Description")) ;
		headers.add(new JLabel("Effect Rate")) ;
		
		panel.add(headers) ;
		
		Move currentMove = aMove ;
		JPanel currentMoveLine = new JPanel() ;
		currentMoveLine.setLayout( new GridLayout(8,1) ) ;
		//Level, move name, type, power, category, accuracy, pp, description, effectRate
		//currentMoveLine.add(new JLabel( currentMove.getLevel()+"" ) ) ;
		
		JLabel type = new JLabel( currentMove.getType().getTypeName() ) ;
		
		type.setForeground(currentMove.getType().getColor());
		
		currentMoveLine.add(new JLabel( currentMove.getName() ) ) ;
		currentMoveLine.add(type) ;
		currentMoveLine.add(new JLabel( currentMove.getPower()+"" ) ) ;
		currentMoveLine.add(new JLabel( currentMove.getCategory() ) ) ;
		currentMoveLine.add(new JLabel( currentMove.getAccuracy()+"" ) ) ;
		currentMoveLine.add(new JLabel( currentMove.getPP()+"" ) ) ;
		JTextPane descriptionSquare = new JTextPane( ) ;
		descriptionSquare.setText(currentMove.getDescription()) ;
		descriptionSquare.setEditable(false) ;
		currentMoveLine.add(descriptionSquare ) ;
		currentMoveLine.add(new JLabel( currentMove.getEffectRate()+"" ) ) ;
		
		panel.add(currentMoveLine) ;
		
		add(panel) ;
		
		JPanel buttons = new JPanel() ;
		
		buttons.setLayout(new GridLayout(1,2)) ;
		
		JButton btd = new JButton("Back to Main Dex") ;
		JButton sam = new JButton("Search for Another Move") ;
		
		sam.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(anMSW != null)
				{
					setVisible(false) ;
					anMSW.setVisible(true);
				}
			}
			
		});
		
		btd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(anMSW != null)
				{
					setVisible(false) ;
					anMSW.aDisplayer.setVisible(true);
				}
			}
		});
		
		buttons.add(sam) ;
		buttons.add(btd) ;
		
		add(buttons, BorderLayout.SOUTH) ;
		
		pack() ;
		
		
		
		
		if(anMSW == null)
		{
			setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		}
		else
		{
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE) ;
			
			if(anMSW.aDisplayer!=null)
			{
				try
			
				{
					Image test = ImageIO.read(new File(aPokedex.aBaseFolder+anMSW.aDisplayer.getIconFile())) ;
					setIconImage(test) ;
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}	
		}
		
		setTitle(aMove.getName()+" Move") ;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String baseFolder = "C:/Users/marie/Desktop/Data for Pokédex/Data_for_v2/" ;
		long start = System.currentTimeMillis() ;
		Pokedex px = new Pokedex(baseFolder,baseFolder+"all_moves.txt",baseFolder+"all_abilities.csv", baseFolder+"Type Matchup.csv",baseFolder+"PokéDataToText.txt", baseFolder+"Mega File.csv", baseFolder+"Formes2.csv", baseFolder+"BattleSwappers.csv", baseFolder+"flavor_texts_shield.txt", baseFolder+"evolution_method_and_egg_groups_shield.txt", baseFolder+"egg_group_evol_EVs_shield.txt") ;
		long end = System.currentTimeMillis() ;
		
		System.out.println(((double)(end-start))/1000.0) ;
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				MoveSearchWindow aw = new MoveSearchWindow(px, null) ;
				aw.setVisible(true) ;
				
			}
			
		});
	}

}
