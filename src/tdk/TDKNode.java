package tdk;

public class TDKNode {
	public TDKNode(){
		
	}
	
	public TDKNode(String word,String suffix){
		this.word=word;
		this.suffix=suffix;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	protected String word;
	protected String suffix;
}
