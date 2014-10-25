package Data;


public class UnBracketedBigramsFreq extends UnBracketedBigrams {

	public int frequency;
	public String toString(){
		return String.format("[%s %s %d]", word1, word2,frequency);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof UnBracketedBigramsFreq))
		      return false;
		    if (other == this)
		      return true;
		    UnBracketedBigramsFreq otherBigram = (UnBracketedBigramsFreq) other;
		return (word1.equals(otherBigram.word1) && word2.equals(otherBigram.word2));
	}	


}
