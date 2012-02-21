package trie;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class VocabularyTrieNode {
	public VocabularyTrieNode(){
		edges=new HashMap<Character, VocabularyTrieNode>();
	}
	
	public VocabularyTrieNode(char chr){
		this.chr=chr;
		edges=new HashMap<Character, VocabularyTrieNode>();
	}
	
	public VocabularyTrieNode(char chr,VocabularyTrieNode parent){
		this.chr=chr;
		this.parent=parent;
		edges=new HashMap<Character, VocabularyTrieNode>();
	}

	public VocabularyTrieNode(char chr,VocabularyTrieNode parent,boolean completed){
		this.chr=chr;
		this.parent=parent;
		this.completed=completed;
		edges=new HashMap<Character, VocabularyTrieNode>();
	}
	
	public void setChar(char chr){
		this.chr=chr;
	}
	
	public void setParent(VocabularyTrieNode parent){
		this.parent=parent;
	}
	
	public void setCompleted(boolean completed){
		this.completed=completed;
	}

	public void insertChild(VocabularyTrieNode node){
		/*		node.setParent(this);
		int index=0;
		for(VocabularyTrieNode c:children){
			if(c.getChar()<node.getChar()){
				index++;
			}else{
				break;
			}
		}
		children.add(index, node);
		 */
		node.setParent(this);
		this.edges.put(node.getChar(), node);
	}
	
	public char getChar(){
		return chr;
	}
	
	public VocabularyTrieNode getParent(){
		return parent;
	}
	
	public boolean isCompleted(){
		return completed;
	}
	
	public Map<Character,VocabularyTrieNode> getEdges(){
		return edges;
	}
	
	public VocabularyTrieNode getChildren(char c)throws RuntimeException{
	/*	if(i>=children.size())
			throw new RuntimeException("Index out of bounds.");
		return children.get(i);
	*/
		if(!edges.containsKey(c))
			throw new RuntimeException("Wrong character specified!");
		return edges.get(c);
	}
	
	@Override
	public boolean equals(Object o){
		VocabularyTrieNode n=(VocabularyTrieNode)o;
		if(this.chr==n.chr)
			return true;
		return false;
	}
	
	protected char chr;
	protected VocabularyTrieNode parent;
	protected Map<Character,VocabularyTrieNode> edges;
	protected boolean completed=false;
}