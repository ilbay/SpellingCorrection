package rule;

import java.util.List;
import java.util.ArrayList;

public class RuleGenerator {

	public static void main(String[] args){
		System.out.println("Distance: "+RuleGenerator.distance("kitten", "sitting"));
		System.out.println("Distance: "+RuleGenerator.distance("microsoft", "nicosooft"));
		System.out.println("Distance: "+RuleGenerator.distance("saturday", "sunday"));
		
		try{
			List<Rule> ruleList=RuleGenerator.generateRule("kitten", "sitting");
			for(int i=0;i<ruleList.size();i++){
				System.out.println(ruleList.get(i).getRule());
			}
			
			System.out.println("*************************");
			
			List<Rule >ruleList2=RuleGenerator.generateRule("nicosooft", "microsoft");
			for(int i=0;i<ruleList2.size();i++){
				System.out.println(ruleList2.get(i).getRule());
			}
			
			System.out.println("***************************");
			
			List<Rule> ruleList3=RuleGenerator.generateRule("saturday", "sunday");
			for(int i=0;i<ruleList3.size();i++){
				System.out.println(ruleList3.get(i).getRule());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static List<Rule> generateRule(String s1,String s2) throws Exception
	{
		List<Rule> ruleList=new ArrayList<Rule>();
		int[][] d=new int[s1.length()+1][s2.length()+1];

		for(int i=0;i<=s1.length();i++)
			d[i][0]=i;
		
		for(int i=0;i<=s2.length();i++)
			d[0][i]=i;
		
		for(int j=1;j<=s2.length();j++){
			for(int i=1;i<=s1.length();i++){
				if(s1.charAt(i-1)==s2.charAt(j-1)){
					d[i][j]=d[i-1][j-1];
				}else{
					d[i][j]=Math.min(d[i-1][j]+1, Math.min(d[i][j-1]+1, d[i-1][j-1]+1));					
				}
			}
		}
		
		int i=s1.length(),j=s2.length();
		int prev=d[s1.length()][s2.length()];
		while(i>=1 && j>=1){
			if(d[i-1][j]==(prev-1)){ //deletion
				ruleList.add(new Rule(String.valueOf(s1.charAt(i-1)),"#"));
				prev--;
				i--;
			}else if(d[i][j-1]==(prev-1)){ //insertion
				ruleList.add(new Rule("#",String.valueOf(s2.charAt(j-1))));
				prev--;
				j--;
			}else if(d[i-1][j-1]==(prev-1)){ //substitution
				ruleList.add(new Rule(String.valueOf(s1.charAt(i-1)),String.valueOf(s2.charAt(j-1))));
				i--;
				j--;
				prev--;
			}else if(d[i-1][j-1]==prev){
				i--;
				j--;
			}else if(d[i][j-1]==prev){
				j--;
			}else if(prev==0){
				break;
			}else{
				throw new Exception("An unexpected case!");
			}
		}
		
		return ruleList;
	}
	
	public static int distance(String s1,String s2){		
		int[][] d=new int[s1.length()+1][s2.length()+1];
		
		for(int i=0;i<=s1.length();i++)
			d[i][0]=i;
		
		for(int i=0;i<=s2.length();i++)
			d[0][i]=i;
		
		for(int j=1;j<=s2.length();j++){
			for(int i=1;i<=s1.length();i++){
				if(s1.charAt(i-1)==s2.charAt(j-1)){
					d[i][j]=d[i-1][j-1];
				}else{
					d[i][j]=Math.min(d[i-1][j]+1, Math.min(d[i][j-1]+1, d[i-1][j-1]+1));
				}
			}
		}
		
		return d[s1.length()][s2.length()];
	}
	
}