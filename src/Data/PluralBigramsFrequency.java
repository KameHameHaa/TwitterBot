package Data;


public class PluralBigramsFrequency extends UnBracketedBigramsFreq {

	String word2Plural = new String();

	public String toString(){
		return String.format("[%s %s %s %d]", word1, word2,word2Plural,frequency);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof PluralBigramsFrequency))
		      return false;
		    if (other == this)
		      return true;
		    PluralBigramsFrequency otherBigram = (PluralBigramsFrequency) other;
		return (word1.equals(otherBigram.word1) && word2.equals(otherBigram.word2));
	}	

}
