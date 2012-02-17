package main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rule.*;
import trie.*;
import java.util.Map;
import java.util.HashMap;

public class SpellingCorrection{
	
	public static void main(String[] args){
		SpellingCorrection spellingCorrection=new SpellingCorrection("deneme_training_data.txt");
	}
	
	public SpellingCorrection(String filename){
		trainingWordSet=new HashSet<SpellingCorrection.WordPair>();
		ahoCorasickTrie=new AhoCorasickTrie();
		ruleList=new HashMap<Rule, Integer>();
		try{
			this.loadTrainingData("deneme_training_data.txt");
			this.generateRules();
			this.constructAhoCorasickTrie();
			List<AhoCorasickTrieNode> rules=ahoCorasickTrie.findRules("^nicosooft$");
			for(AhoCorasickTrieNode node:rules){
				Set<String> keyset=node.getList().keySet();
				System.out.println(node.toOut());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void loadTrainingData(String filename)throws Exception{
		FileInputStream fstream=new FileInputStream(filename);
		DataInputStream in=new DataInputStream(fstream);
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		String line;
		while((line=reader.readLine())!=null){
			if(line=="") break;
			String[] wordPair=line.split("\t");
			trainingWordSet.add(new WordPair(wordPair[0], wordPair[1]));
		}
		
		reader.close();
		in.close();
		fstream.close();
	}
	
	public void generateRules(){
		try{
			for(WordPair wp:trainingWordSet){
				List<Rule> rules=RuleGenerator.generateRule(wp.getMisspelledWord(), wp.getCorrectWord());
				for(Rule r:rules){
					if(ruleList.containsKey(r)){
						Integer i=ruleList.get(r);
						ruleList.put(r, i+1);
					}else{
						ruleList.put(r, 1);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void constructAhoCorasickTrie(){
		Set<Rule> ruleSet=ruleList.keySet();
		for(Rule r:ruleSet){
			if(r!=null)
				ahoCorasickTrie.insertRule(r, ruleList.get(r));
		}
		ahoCorasickTrie.completeTrie();
	}
	
	private Set<WordPair> trainingWordSet;
	private Map<Rule,Integer> ruleList;
	private AhoCorasickTrie ahoCorasickTrie;
	
	private class WordPair{
		public WordPair(String misspelledWord,String correctWord){
			this.misspelledWord=misspelledWord;
			this.correctWord=correctWord;
		}
		
		@Override
		public boolean equals(Object o){
			WordPair w=(WordPair)o;
			if(this.misspelledWord.equals(w.misspelledWord) && this.correctWord.equals(w.correctWord))
				return true;
			else
				return false;
		}
		
		@Override
		public int hashCode(){
			return misspelledWord.hashCode()*correctWord.hashCode();
		}
		
		public String getMisspelledWord(){
			return misspelledWord;
		}
		
		public String getCorrectWord(){
			return correctWord;
		}
		
		private String misspelledWord;
		private String correctWord;
	}
}