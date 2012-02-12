package trie;

import java.util.List;
import java.util.ArrayList;

public class Node {
	public Node(){
		children=new ArrayList<Node>();
	}
	
	public Node(char chr){
		this.chr=chr;
		children=new ArrayList<Node>();
	}
	
	public Node(char chr,Node parent){
		this.chr=chr;
		this.parent=parent;
		children=new ArrayList<Node>();
	}

	public Node(char chr,Node parent,boolean completed){
		this.chr=chr;
		this.parent=parent;
		this.completed=completed;
		children=new ArrayList<Node>();
	}
	
	public void setChar(char chr){
		this.chr=chr;
	}
	
	public void setParent(Node parent){
		this.parent=parent;
	}
	
	public void setCompleted(boolean completed){
		this.completed=completed;
	}

	public void insertChild(Node node){
		node.setParent(this);
		int index=0;
		for(Node c:children){
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
	
	public Node getParent(){
		return parent;
	}
	
	public boolean isCompleted(){
		return completed;
	}
	
	public List<Node> getChildren(){
		return children;
	}
	
	public Node getChildren(int i)throws RuntimeException{
		if(i>=children.size())
			throw new RuntimeException("Index out of bounds.");
		return children.get(i);
	}
	
	@Override
	public boolean equals(Object o){
		Node n=(Node)o;
		if(this.chr==n.chr)
			return true;
		return false;
	}
	
	protected char chr;
	protected Node parent;
	protected List<Node> children;
	protected boolean completed=false;
}