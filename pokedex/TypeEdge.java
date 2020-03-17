package pokedex;

import java.util.Arrays;
import java.util.Comparator;

public class TypeEdge implements Comparator<TypeEdge> {

	public final Type aType ;
	public final double aWeight ;
	
	public TypeEdge(Type pType, double pWeight)
	{
		String[] TYPES = Species.TYPES ;
		Arrays.sort(TYPES) ;
		
		assert Arrays.binarySearch(TYPES, pType.getTypeName()) >= 0 ;
		assert pWeight>=0 && pWeight<=2 ;
		
		aType = pType ;
		aWeight = pWeight ;
	}
	
	public String toString()
	{
		return aType.getTypeName()+" "+aWeight ;
	}
	
	public int compare(TypeEdge e1, TypeEdge e2)
	{
		return e1.aType.getTypeName().compareTo(e2.aType.getTypeName())  ;
	}
	
	public Type getType()
	{
		return aType ;
	}
	public double getWeight()
	{
		return aWeight ;
	}
}

