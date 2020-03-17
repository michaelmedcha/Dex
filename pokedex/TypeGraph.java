package pokedex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

public class TypeGraph {
	private Type[] anArrayOfTypes = new Type[18] ;
	
	public TypeGraph()
	{
		loadTypes() ;
		Arrays.sort(anArrayOfTypes) ;
	}
	
	public TypeGraph(String typeFilename)
	{
		loadTypes(typeFilename) ;
		Arrays.sort(anArrayOfTypes);
	}
	
	private void loadTypes()
	{
		try
		{
			FileReader fr = new FileReader("C:/Users/marie/Desktop/Data for Pokédex/Type Matchup.csv") ;
			BufferedReader br = new BufferedReader(fr) ;
			
			String currentLine = br.readLine() ;
			currentLine = br.readLine() ;
			
			String[] currentInfo = currentLine.split(";") ;
			
			for(int i = 2; i<20; i++)
			{
				anArrayOfTypes[i-2] = new Type(currentInfo[i]) ;
			}
			
			currentLine = br.readLine() ;
			
			while(currentLine != null)
			{
				currentInfo = currentLine.split(";") ;
				
				
				
				Type currentType = findType(currentInfo[1]) ;
				
				
				for(int i = 2 ; i<20; i++)
				{
					currentInfo[i] = currentInfo[i].substring(0, 1) ;
					if(! currentInfo[i].equals("½"))
					{
						currentInfo[i] = currentInfo[i].substring(0, 1) ;
						currentType.addEdge( findType( anArrayOfTypes[i-2].getTypeName() ),  Double.parseDouble(currentInfo[i]) );
				
					} 
					else
					{
						currentType.addEdge( findType( anArrayOfTypes[i-2].getTypeName() ),  0.5 );
					}
				}
				currentLine = br.readLine() ;
			}
			
		}
		catch(IOException e)
		{
			System.out.println("Where are the type matchups for absolute path? Current folder is "+System.getProperty("user.dir")) ;
		}
	}
	
	private void loadTypes(String typeFilename)
	{
		try
		{
			//System.setProperty("file.encoding", "Windows-1252") ;
			
			FileReader fr = new FileReader(typeFilename) ;
			
			//Charset.defaultCharset() ;
			
			//FileInputStream fis = new FileInputStream(typeFilename) ;
			//InputStreamReader isr = new InputStreamReader(fis, Charset.defaultCharset() ) ;
			
			
			
			BufferedReader br = new BufferedReader(fr) ;
			
			String currentLine = br.readLine() ;
			currentLine = br.readLine() ;
			
			String[] currentInfo = currentLine.split(";") ;
			
			for(int i = 2; i<20; i++)
			{
				anArrayOfTypes[i-2] = new Type(currentInfo[i]) ;
			}
			
			currentLine = br.readLine() ;
			
			while(currentLine != null)
			{
				currentInfo = currentLine.split(";") ;
				
				
				
				Type currentType = findType(currentInfo[1]) ;
				
				
				for(int i = 2 ; i<20; i++)
				{
					currentInfo[i] = currentInfo[i].substring(0, 1) ;
					if(! currentInfo[i].contains("5"))
					{
						currentInfo[i] = currentInfo[i].substring(0, 1) ;
						currentType.addEdge( findType( anArrayOfTypes[i-2].getTypeName() ),  Double.parseDouble(currentInfo[i]) );
				
					} 
					else
					{
						currentType.addEdge( findType( anArrayOfTypes[i-2].getTypeName() ),  0.5 );
					}
				}
				currentLine = br.readLine() ;
			}
			
		}
		catch(IOException e)
		{
			System.out.println("Where are the type matchups?  Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+typeFilename) ;
		}
	}
	
	public Type findType(String pType)
	{
		for(Type aType : anArrayOfTypes)
		{
			if(aType.getTypeName().equalsIgnoreCase(pType))
			{
				return aType ;
			}
		}
		return null ;
	}
	
	public Type[] getTypes()
	{
		return anArrayOfTypes ;
	}
	
	public static void main(String[] args)
	{
		String fn = "C:/Users/marie/Desktop/Data for Pokédex/Data_for_v2/type_matchup.csv" ;
		
		try
		{
			FileReader fr = new FileReader(fn) ;
			BufferedReader br = new BufferedReader(fr) ;
			FileWriter fw = new FileWriter("C:/Users/marie/Desktop/Data for Pokédex/Data_for_v2/type_matchup_mod.csv") ;
			BufferedWriter bw = new BufferedWriter(fw) ;
			
			String currentLine = br.readLine() ;
			
			while(currentLine != null)
			{
				currentLine = currentLine.replaceAll("×", "").replaceAll("½", "5") ;
				bw.write(currentLine);
				bw.newLine();
				currentLine = br.readLine() ;
			}
			
			bw.close();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
}

