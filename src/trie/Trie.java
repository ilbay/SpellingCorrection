package trie;

import java.util.List;

public class Trie {
	public Trie(){
		root=new Node();
	}
	
	public void addString(String str)throws RuntimeException{
		if(str.isEmpty())
			throw new RuntimeException("String is empty.");

		Node currentNode=root;
		for(char c:str.toCharArray()){
			Node childNode=getNodeFromNodeList(currentNode.getChildren(),c);
			if(childNode==null){
				Node newNode=new Node(c);
				newNode.setParent(currentNode);
				newNode.setCompleted(false);
				currentNode.insertChild(newNode);
				currentNode=newNode;
			}else{
				currentNode=childNode;
			}
		}
		currentNode.setCompleted(true);
	}
	
	protected Node getNodeFromNodeList(List<Node> nodeList,char c){
		for(Node n:nodeList){
			if(n.getChar()==c){
				return n;
			}
		}
		return null;
	}
	
	protected Node root;
}