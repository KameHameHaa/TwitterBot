package Data;

import java.awt.Color;

public class ColorCodeURL  implements Comparable<Object> {
	public Color value;
	public String URL;

	public String toString(){
		return String.format("[%s %s]", value.toString(), URL);
	}
	
	@Override
	public int compareTo(Object o) {
		ColorCodeURL t = (ColorCodeURL) o;
		if(t.equals(o))
			return 1;
		else 
			return 0;
	}
	
	public int compareTo(Color s) {
		if(value.equals(s))
		return 1;
		else return 0;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ColorCodeURL))
		      return false;
		    if (other == this)
		      return true;
		    ColorCodeURL otherColor = (ColorCodeURL) other;
		return (value.equals(otherColor.value) );
	}	
	
	
}
