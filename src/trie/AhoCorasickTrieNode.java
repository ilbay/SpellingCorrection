package trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class AhoCorasickTrieNode{
	public AhoCorasickTrieNode(){
		ruleList=new HashMap<String, Double>();
		edges=new HashMap<Character, AhoCorasickTrieNode>();
		outList=new HashSet<String>();
	}
	
	public AhoCorasickTrieNode(char chr){
		this.chr=chr;
		ruleList=new HashMap<String, Double>();
		edges=new HashMap<Character, AhoCorasickTrieNode>();
		outList=new HashSet<String>();
	}
	
	public void insertToRuleList(String dest,int frequency){
		ruleList.put(dest, -1/(double)frequency);
	}
	
	public void addChild(char c,AhoCorasickTrieNode node){
		edges.put(c, node);
	}
	
	public void setFailureChild(AhoCorasickTrieNode node){
		failureChild=node;
	}
	
	public void setCharacter(char chr){
		this.chr=chr;
	}
	
	public void insertToOutList(String s){
		outList.add(s);
	}
	
	public char getCharacter(){
		return chr;
	}
	
	public AhoCorasickTrieNode getFailureChild(){
		return failureChild;
	}
	
	public Map<String,Double> getList(){
		return ruleList;
	}
	
	public AhoCorasickTrieNode getChild(char c){
		if(edges.containsKey(c)){
			return edges.get(c);
		}
		return null;
	}
	
	public Map<Character,AhoCorasickTrieNode> getEdges(){
		return edges;
	}
	
	public boolean containsChild(char c){
		return edges.containsKey(c);
	}
	
	public boolean isCompleted(){
		//return completed;
		return (outList.size()!=0);
	}
		
	public Set<String> getOutList(){
		return outList;
	}
	
	public String toOut(){
		return outList.toString();
	}
	
	protected char chr;
	protected Map<Character,AhoCorasickTrieNode> edges;
	protected Set<String> outList;
	protected AhoCorasickTrieNode failureChild;
	protected Map<String,Double> ruleList;
}