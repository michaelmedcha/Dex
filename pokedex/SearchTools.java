package pokedex;

import java.util.ArrayList;
import java.util.Comparator;

public class SearchTools {

	
	public static String[] oneTwoSubstrings(String s)
	{
		if(s.length() == 0)
		{
			String[] out = {""} ;
			return out ;
		}
		String[] result = new String[2*s.length()-1] ; // 1234567
		char[] sAsChars = s.toLowerCase().toCharArray() ;
		int j = 0 ;
		for(int i = 0 ; i<s.length()-1 ; i++ )
		{
			result[j] = sAsChars[i]+"" ;
			result[j+1] = sAsChars[i]+""+sAsChars[i+1] ;
			j+=2 ;
		}
		
		result[j] = sAsChars[s.length()-1]+"" ;
		
		return result ;
	}
	
	public static ArrayList<String> oneTwoUnion(String s1, String s2)
	{
		String[] r1 = oneTwoSubstrings(s1) ;
		String[] r2 = oneTwoSubstrings(s2) ;
		
		ArrayList<String> result = new ArrayList<>() ;
		
		for(String s: r1)
		{
			result.add(s) ;
		}
		
		for(String s: r2)
		{
			if(!result.contains(s))
			{
				result.add(s) ;
			}
		}
		
		return result ;
	}
	
	public static ArrayList<String> oneTwoIntersection(String s1, String s2)
	{
		String[] r1 = oneTwoSubstrings(s1) ;
		String[] r2 = oneTwoSubstrings(s2) ;
		
		ArrayList<String> l1 = new ArrayList<>() ;
		ArrayList<String> l2 = new ArrayList<>() ;
		ArrayList<String> result = new ArrayList<>() ;
		
		for(String s : r1)
		{
			l1.add(s) ;
		}
		
		for(String s : r2)
		{
			l2.add(s) ;
		}
		
		for(String s : l1)
		{
			if(l2.contains(s))
			{
				result.add(s) ;
			}
		}
		
		return result ;
	}
	
	public static double jaccardIndex(String s1, String s2)
	{
		ArrayList<String> union = oneTwoUnion(s1,s2) ;
		ArrayList<String> intersection = oneTwoIntersection(s1,s2) ;
		
		return ((double) intersection.size())/((double) union.size()) ;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "integrability" ;
		String r = "integrability" ;
		System.out.println((jaccardIndex(s,r))) ;
		System.out.println((jaccardIndex(s,"lityintegrabi"))) ;
	}

}
