package trie;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

public class VocabularyTrie {
	public VocabularyTrie(){
		root=new VocabularyTrieNode();
	}
	
	public void addString(String str)throws RuntimeException{
		if(str.isEmpty())
			throw new RuntimeException("String is empty.");

		VocabularyTrieNode currentNode=root;
/*		for(char c:str.toCharArray()){
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
*/
		for(char c:str.toCharArray()){
			if(currentNode.getEdges().containsKey(c)){
				currentNode=currentNode.getChildren(c);
			}else{
				VocabularyTrieNode newNode=new VocabularyTrieNode(c);
				newNode.setParent(currentNode);
				newNode.setCompleted(false);
				currentNode.insertChild(newNode);
				currentNode=newNode;
			}
		}
		currentNode.setCompleted(true);
	}
	
	public String[] searchString(String word,List<AhoCorasickTrieNode> ruleList){
		String[] finalCandidateList=new String[NUMBER_OF_CANDIDATES];
		Map<String,Double> tempCandidateList=new HashMap<String, Double>();
		Queue<DecisionPoint> queue=new LinkedList<DecisionPoint>();
		
		queue.add(new DecisionPoint(word,0,root));
		while(!queue.isEmpty()){
			DecisionPoint point=queue.poll();
			
			if(point.getWord().length()==point.getCurrentCharacterPoint()){ //yolun sonu
				if(tempCandidateList.size()<NUMBER_OF_CANDIDATES)
					tempCandidateList.put(point.getWord(),point.getWeight());
				else{
					if(!isSmallest(tempCandidateList, point.getWeight())){
						//Listedeki minimum ağırlığa sahip stringi bul ve sil
						Iterator it=tempCandidateList.entrySet().iterator();
						double min=point.getWeight();
						String minWeightString=null;
						while(it.hasNext()){
							Map.Entry<String, Double> pairs=(Map.Entry<String, Double>)it.next();
							if(pairs.getValue()<min){
								min=pairs.getValue();
								minWeightString=pairs.getKey();
							}
						}
						if(minWeightString==null)
							throw new RuntimeException("String is Null!");
						tempCandidateList.remove(minWeightString);
						tempCandidateList.put(point.getWord(), point.getWeight());
					}
				}
				continue;
			}
			
			char c=point.getWord().charAt(point.getCurrentCharacterPoint());
			VocabularyTrieNode currentNode=point.getNode();
			if(currentNode.getEdges().containsKey(c)){
				queue.add(new DecisionPoint(point.getWord(), point.getCurrentCharacterPoint()+1, currentNode.getChildren(c)));
			}
			
			String leftPartofWord=point.getWord().substring(point.getCurrentCharacterPoint());
			for(AhoCorasickTrieNode node:ruleList){
				if(leftPartofWord.startsWith(node.getRuleSource())){
					//kuralı uygula
					Map<String,Double> nodeRuleMap=node.getList();
					Iterator it=nodeRuleMap.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry<String, Double> pairs=(Map.Entry<String,Double>)it.next();
						String newWord=point.getWord().substring(0,point.getCurrentCharacterPoint())+pairs.getKey()+point.getWord().substring(point.getCurrentCharacterPoint()+node.getRuleSource().length());
						if(!isSmallest(tempCandidateList, point.getWeight()+pairs.getValue()) && currentNode.getEdges().containsKey(newWord.charAt(point.getCurrentCharacterPoint()))){
							queue.add(new DecisionPoint(newWord,point.getCurrentCharacterPoint()+1,currentNode.getChildren(newWord.charAt(point.getCurrentCharacterPoint())),point.getWeight()+pairs.getValue()));
						}
					}
				}
			}
		}
		
		Iterator it=tempCandidateList.entrySet().iterator();
		int counter=0;
		while(it.hasNext()){
			Map.Entry<String, Double> pairs=(Map.Entry<String, Double>)it.next();
			finalCandidateList[counter]=pairs.getKey();
			counter++;
		}
		
		return finalCandidateList;
	}
	
	protected boolean isSmallest(Map<String,Double> candidateList,double value){
		if(candidateList.size()<NUMBER_OF_CANDIDATES)
			return false;
		Iterator it=candidateList.entrySet().iterator();
		double min=value;
		while(it.hasNext()){
			Map.Entry<String,Double> pairs=(Map.Entry<String,Double>)it.next();
			if(pairs.getValue()<min)
				return false;
		}
		return true;
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
	protected final static int NUMBER_OF_CANDIDATES=2;
	
	private class DecisionPoint{
		public DecisionPoint(){
			
		}
		
		public DecisionPoint(String word,VocabularyTrieNode node){
			this.word=word;
			this.node=node;
		}
		
		public DecisionPoint(String word,int currentCharacterPoint, VocabularyTrieNode node){
			this.word=word;
			this.currentCharacterPoint=currentCharacterPoint;
			this.node=node;
		}
		
		public DecisionPoint(String word,int currentCharacterPoint, VocabularyTrieNode node,double weight){
			this.word=word;
			this.currentCharacterPoint=currentCharacterPoint;
			this.node=node;
			this.weight=weight;
		}
		
		public void setCurrentCharacterPoint(int currentCharacterPoint){
			this.currentCharacterPoint=currentCharacterPoint;
		}
		
		public void setWord(String word){
			this.word=word;
		}
		
		public void setWeight(double weight){
			this.weight=weight;
		}
		
		public void setNode(VocabularyTrieNode node){
			this.node=node;
		}
		
		public String getWord(){
			return word;
		}
		
		public int getCurrentCharacterPoint(){
			return currentCharacterPoint;
		}
		
		public VocabularyTrieNode getNode(){
			return node;
		}
		
		public double getWeight(){
			return weight;
		}
		
		private String word;
		private VocabularyTrieNode node;
		private int currentCharacterPoint;
		private double weight;
	}
}