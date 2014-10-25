package Data;

import java.awt.Color;

public class ColorValue implements Comparable<Object> {
	public String stereotype ; // Name of the color like Acid, Algae etc
	public String name	;  //Name of the Color as green, brown, blue, white, orange, grey, gray, yellow, purple, red, black, pink]
	public Color value; 	//RGB value
	public Color getValue()
	{
		return value;
		
	}
	public String toString(){
		return String.format("[%s %s %s]", stereotype, name, value.toString());
	}
	
	@Override
	public int compareTo(Object o) {
		ColorValue t = (ColorValue) o;
		if(t.equals(o))
			return 1;
		else 
			return 0;
	}
	
	public int compareTo(String s) {
		if(name.equals(s))
		return 1;
		else return 0;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ColorValue))
		      return false;
		    if (other == this)
		      return true;
		    ColorValue otherColor = (ColorValue) other;
		return (name.equals(otherColor.name) );
	}	

	
}
