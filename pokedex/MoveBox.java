package pokedex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class MoveBox {

	private Hashtable<String,Move> aMoveBox;
	
	public MoveBox(String filename, String typeLoc)
	{
		aMoveBox = new Hashtable<>() ;
		try
		{
			FileReader fr = new FileReader(filename) ;
			BufferedReader br = new BufferedReader(fr) ;
			
			String currentLine = br.readLine() ;
			
			while(currentLine!=null)
			{
				String[] currentSplit = currentLine.split(";") ;
				assert currentSplit.length == 9 : "\""+currentLine+"\" does not give the correct length of 9.";
				TypeGraph G = new TypeGraph(typeLoc) ;
				
				aMoveBox.put(currentSplit[0].toLowerCase(), new Move(currentSplit,G)) ;
				currentLine = br.readLine() ;
			}
			
		}
		catch(IOException e)
		{
			System.out.println("Where are the moves? Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+filename) ;
			System.out.println(e) ;
		}
	}
	
	public Move getMove(String aName) throws MoveNotFoundException
	{
		if(!aMoveBox.containsKey(aName.toLowerCase()))
		{
			throw new MoveNotFoundException("The move \""+aName+"\" cannot be found.") ;
		}
		
		return aMoveBox.get(aName.toLowerCase()) ;
	}
	
	public Hashtable<String,Move> getMoves()
	{
		return (Hashtable<String,Move>) aMoveBox.clone() ;
	}
	
	public class MoveNotFoundException extends Exception{
		public MoveNotFoundException(String errorMessage)
		{
			super(errorMessage) ;
		}
		
		public MoveNotFoundException(String errorMessage, Throwable cause)
		{
			super(errorMessage, cause) ;
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//MoveBox mb = new MoveBox("C:/Users/marie/Desktop/Data for Pokédex/Data_for_v2/all_moves.txt") ;
	}

}
