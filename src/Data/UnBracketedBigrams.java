package Data;



public class UnBracketedBigrams implements Comparable<UnBracketedBigrams> {

	public String word1 = new String();
	public String word2 = new String();
	
	public String toString(){
		return String.format("[%s %s]", word1, word2);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof UnBracketedBigrams))
		      return false;
		    if (other == this)
		      return true;
		UnBracketedBigrams otherBigram = (UnBracketedBigrams) other;
		return (word1.equals(otherBigram.word1) && word2.equals(otherBigram.word2));
	}

	public boolean equals2(Object other) {
		if (!(other instanceof UnBracketedBigrams))
		      return false;
		    if (other == this)
		      return true;
		UnBracketedBigrams otherBigram = (UnBracketedBigrams) other;
		return (word1.equals(otherBigram.word1) && word2.equals(otherBigram.word2));
	}

	@Override
	public int compareTo(UnBracketedBigrams otherBigram) {
		if (word1.equals(otherBigram.word1) && word2.equals(otherBigram.word2))
		{
			return 1;
		}
		else return 0;
	}
	
	public int hashCode() {
	    int hashCode = 0;

	    hashCode = hashCode * 37 + this.word1.hashCode();
	    hashCode = hashCode * 37 + this.word2.hashCode();
	 
	    return hashCode;
	}
	
}
