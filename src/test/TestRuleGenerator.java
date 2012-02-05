package test;

import rule.*;
import org.junit.*;
import java.util.List;
import java.util.Arrays;

public class TestRuleGenerator{
	@Test
	public void testDistance(){
		Assert.assertEquals(RuleGenerator.distance("microsoft", "nicosooft"), 3);
		Assert.assertEquals(RuleGenerator.distance("google", "google"),0);
		Assert.assertEquals(RuleGenerator.distance("saturday", "sunday"), 3);
		Assert.assertEquals(RuleGenerator.distance("kitten", "sittin"), 2);
	}
	
	@Test
	public void testGenerateRule(){
		try{
			Assert.assertEquals(RuleGenerator.generateRule("nicosooft", "microsoft"), Arrays.asList(new Rule("o","#"),new Rule("#","r"),new Rule("n","m")));
			
			Assert.assertEquals(RuleGenerator.generateRule("kitten","sitting"), Arrays.asList(new Rule("#","g"),new Rule("e","i"),new Rule("k","s")));
			
			Assert.assertEquals(RuleGenerator.generateRule("google", "google"),Arrays.asList());
			
			Assert.assertEquals(RuleGenerator.generateRule("saturday", "sunday"),Arrays.asList(new Rule("r","n"),new Rule("t","#"),new Rule("a","#")));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}