package trie;

import java.util.List;
import java.util.ArrayList;

public class VocabularyTrieNode {
	public VocabularyTrieNode(){
		children=new ArrayList<VocabularyTrieNode>();
	}
	
	public VocabularyTrieNode(char chr){
		this.chr=chr;
		children=new ArrayList<VocabularyTrieNode>();
	}
	
	public VocabularyTrieNode(char chr,VocabularyTrieNode parent){
		this.chr=chr;
		this.parent=parent;
		children=new ArrayList<VocabularyTrieNode>();
	}

	public VocabularyTrieNode(char chr,VocabularyTrieNode parent,boolean completed){
		this.chr=chr;
		this.parent=parent;
		this.completed=completed;
		children=new ArrayList<VocabularyTrieNode>();
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
		node.setParent(this);
		int index=0;
		for(VocabularyTrieNode c:children){
			if(c.getChar()<node.getChar()){
				index++;
			}else{
				break;
			}
		}
		children.add(index, node);
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
	
	public List<VocabularyTrieNode> getChildren(){
		return children;
	}
	
	public VocabularyTrieNode getChildren(int i)throws RuntimeException{
		if(i>=children.size())
			throw new RuntimeException("Index out of bounds.");
		return children.get(i);
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
	protected List<VocabularyTrieNode> children;
	protected boolean completed=false;
}