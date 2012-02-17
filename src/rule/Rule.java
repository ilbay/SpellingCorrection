package rule;

public class Rule {
	
	public Rule(){	
	}
	
	public Rule(String source,String dest){
		this.source=source;
		this.dest=dest;
	}
	
	@Override
	public boolean equals(Object r){
		Rule rule=(Rule)r;
		if(rule.source.equals(this.source) && rule.dest.equals(this.dest))
			return true;
		return false;
	}
	
	@Override
	public int hashCode(){
		return source.hashCode()*dest.hashCode();
	}
	
	public void setSource(String source){
		this.source=source;
	}
	
	public void setDest(String dest){
		this.dest=dest;
	}
	
	public String getRule(){
		return source+"=>"+dest;
	}
	
	public String getDest(){
		return dest;
	}
	
	public String getSource(){
		return source;
	}
	
	protected String source;
	protected String dest;
}