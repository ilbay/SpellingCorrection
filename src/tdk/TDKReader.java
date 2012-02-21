package tdk;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TDKReader{	
	public static void main(String[] args){
		try{
			TDKReader tdkReader=new TDKReader("Maddeler3.txt");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public TDKReader(String dbFilename)throws Exception{
		tdkWordList=new ArrayList<TDKNode>();
		
		loadTDK(dbFilename);
	}
	
	public List<TDKNode> getTDKList(){
		return tdkWordList;
	}
	
	private void loadTDK(String dbFilename)throws Exception{
		FileInputStream fstream=new FileInputStream(dbFilename);
		DataInputStream in=new DataInputStream(fstream);
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		
		String line;
		while((line=reader.readLine())!=null){
			String[] columns=line.split(";");
			//System.out.println(columns[3]+" "+columns[4]);
			tdkWordList.add(new TDKNode(columns[3].replace("\"", ""),columns[4].replace("\"", "")));
		}
		
		reader.close();
		in.close();
		fstream.close();
	}
	
	private List<TDKNode> tdkWordList;
}
