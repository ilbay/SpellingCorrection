package rule;

import java.util.List;
import java.util.ArrayList;

public class RuleGenerator {

	public static void main(String[] args){
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
	
	public static List<Rule> generateRule(final String s1,final String s2) throws Exception
	{
		String copyS1=new String(s1);
		String copyS2=new String(s2);
		
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
		
		//align string
		int i=s1.length(),j=s2.length(),counterForExtraStringOfs1=0,counterForExtraStringOfs2=0;
		int prev=d[s1.length()][s2.length()];
		while(i>=1 && j>=1){
			if(d[i-1][j]==(prev-1)){ //deletion
				//ruleList.add(new Rule(String.valueOf(s1.charAt(i-1)),"#"));
				copyS2=copyS2.substring(0, i-1+counterForExtraStringOfs2)+"#"+copyS2.substring(i-1+counterForExtraStringOfs2);
				counterForExtraStringOfs2++;
				prev--;
				i--;
			}else if(d[i][j-1]==(prev-1)){ //insertion
				//ruleList.add(new Rule("#",String.valueOf(s2.charAt(j-1))));
				copyS1=copyS1.substring(0, j-1+counterForExtraStringOfs1)+"#"+copyS1.substring(j-1+counterForExtraStringOfs1);
				counterForExtraStringOfs1++;
				prev--;
				j--;
			}else if(d[i-1][j-1]==(prev-1)){ //substitution
				//ruleList.add(new Rule(String.valueOf(s1.charAt(i-1)),String.valueOf(s2.charAt(j-1))));
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

		if(copyS1.length()!=copyS2.length())
			throw new Exception("Strings' lengths are not matched!");
		
		copyS1="^"+copyS1+"$";
		copyS2="^"+copyS2+"$";
		
		//create rule list including expanded rules
		for(int x=0;x<copyS1.length();x++){
			if(copyS1.charAt(x)!=copyS2.charAt(x)){
				ruleList.add(new Rule(String.valueOf(copyS1.charAt(x)),String.valueOf(copyS2.charAt(x))));
				int N=2;
				for(int y=N;y>0;y--){
					int a=0,b=y;
					while(Math.abs(a)<=b){
						if(x+a>=0 && x+b<copyS1.length())
							ruleList.add(new Rule(copyS1.substring(x+a, x+b+1).replace("#", ""),copyS2.substring(x+a, x+b+1).replace("#", "")));
						if(Math.abs(a)!=b && x-b>=0 && x-a<copyS1.length()){
							ruleList.add(new Rule(copyS1.substring(x-b,x-a+1).replace("#",""),copyS2.substring(x-b, x-a+1).replace("#","")));
						}
						a--;
						b--;
					}
				}
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