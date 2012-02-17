package trie;

import java.util.List;

public class VocabularyTrie {
	public VocabularyTrie(){
		root=new VocabularyTrieNode();
	}
	
	public void addString(String str)throws RuntimeException{
		if(str.isEmpty())
			throw new RuntimeException("String is empty.");

		VocabularyTrieNode currentNode=root;
		for(char c:str.toCharArray()){
			VocabularyTrieNode childNode=getNodeFromNodeList(currentNode.getChildren(),c);
			if(childNode==null){
				VocabularyTrieNode newNode=new VocabularyTrieNode(c);
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
	
	protected VocabularyTrieNode getNodeFromNodeList(List<VocabularyTrieNode> nodeList,char c){
		for(VocabularyTrieNode n:nodeList){
			if(n.getChar()==c){
				return n;
			}
		}
		return null;
	}
	
	protected VocabularyTrieNode root;
}