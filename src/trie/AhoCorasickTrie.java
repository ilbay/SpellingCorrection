package trie;

import java.util.Map;
import rule.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;


/**
 * For more information about Aho-Corasick Trie:
 * 		http://www.cs.uku.fi/~kilpelai/BSA05/lectures/slides04.pdf
 * 		http://www.codeproject.com/Articles/12383/Aho-Corasick-string-matching-in-C
 * 
 * @author ilbay
 *
 */

public class AhoCorasickTrie{
	public AhoCorasickTrie(){
		root=new AhoCorasickTrieNode();
	}
	
	public void insertRule(Rule rule,int frequency){
		String source=rule.getSource();
		String dest=rule.getDest();
		
		AhoCorasickTrieNode currentNode=root;
		for(char c:source.toCharArray()){
			if(currentNode.containsChild(c)){
				currentNode=currentNode.getChild(c);
			}else{
				AhoCorasickTrieNode newNode=new AhoCorasickTrieNode(c);
				currentNode.addChild(c, newNode);
				currentNode=newNode;
			}
		}
		currentNode.insertToRuleList(dest, frequency);
		currentNode.setRuleSource(source);
		currentNode.insertToOutList(currentNode);
	}
	
	public void completeTrie(){
		Queue<AhoCorasickTrieNode> queue=new LinkedList<AhoCorasickTrieNode>();
		Map<Character,AhoCorasickTrieNode> edgesOfRoot=root.getEdges();
		Set<Character> keysetOfRootEdges=edgesOfRoot.keySet();
		for(Character c:keysetOfRootEdges){
			AhoCorasickTrieNode q=root.getChild(c);
			q.setFailureChild(root);
			queue.add(q);
		}
		
		while(!queue.isEmpty()){
			AhoCorasickTrieNode r=(AhoCorasickTrieNode)queue.poll();
			Set<Character> keysetofNodeEdges=r.getEdges().keySet();
			for(Character c:keysetofNodeEdges){
				AhoCorasickTrieNode u=r.getChild(c);
				AhoCorasickTrieNode v=r.getFailureChild();
				queue.add(u);
				while(v!=null && v!=root && !v.containsChild(c)){
					v=v.getFailureChild();
				}
				if(v!=null){
					if(v==root && !v.containsChild(c)){
						u.setFailureChild(root);
						u.getOutList().addAll(root.getOutList());
					}else{
						u.setFailureChild(v.getChild(c));
						u.getOutList().addAll(v.getChild(c).getOutList());
					}
				}else{
					u.setFailureChild(root);
				}
			}
		}
	}
		
	public List<AhoCorasickTrieNode> findRules(String word){
		List<AhoCorasickTrieNode> ruleList=new ArrayList<AhoCorasickTrieNode>();
		Set<AhoCorasickTrieNode> finalRuleSet=new HashSet<AhoCorasickTrieNode>();
		AhoCorasickTrieNode currentNode=root;
		for(char c:word.toCharArray()){
			while(currentNode!=root && !currentNode.containsChild(c))
				currentNode=currentNode.getFailureChild();
			if(currentNode==root && !currentNode.containsChild(c))
				currentNode=root;
			else
				currentNode=currentNode.getChild(c);
			if(currentNode.isCompleted())
				ruleList.add(currentNode);
		}
		
		for(AhoCorasickTrieNode node:ruleList){
			for(AhoCorasickTrieNode nodeInOutList:node.getOutList())
				finalRuleSet.add(nodeInOutList);
		}
		
		return new ArrayList<AhoCorasickTrieNode>(finalRuleSet);
	}
	
	//These two methods below are written to test whether AC trie works properly
	public void traverseTrie(){
		traverseTrie(root);
	}
	
	public void traverseTrie(AhoCorasickTrieNode node){
		if(node==null) return;
		if(node.isCompleted())
			System.out.println(node.toOut());
		
		Set<Character> keySet=node.getEdges().keySet();
		for(Character c:keySet)
			traverseTrie(node.getChild(c));
	}
	
	protected AhoCorasickTrieNode root;
}