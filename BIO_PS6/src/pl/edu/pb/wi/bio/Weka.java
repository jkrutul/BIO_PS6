package pl.edu.pb.wi.bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import pl.edu.pb.wi.bio.models.UserKeyboardScan;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IB1;
import weka.core.Instances;

public class Weka {
	public static Map<String,UserKeyboardScan> data;
	public Weka(Map<String,UserKeyboardScan> data) {
		this.data = data;
	}
	

	public static void buildTestWekaFile(UserKeyboardScan uks) {
		String alph = new String();
		String[] alphabet;
		char c = 'a';
		 
		while (c <= 'z') {
			alph+=c+",";
		  c++;
		}
		alph = alph.toUpperCase();
		alphabet = alph.split(",");
		
		// file
		String f = "@relation testData.arff\n";
		f+="\n";
		for(String a : alphabet) {
			f+="@attribute " + a + " numeric\n";
		}
		
		f+="@attribute class {";
		Set<String> users = data.keySet();
		Iterator<String> iter = users.iterator();
		
		while(iter.hasNext()) {
			f+=iter.next();
			if(iter.hasNext()) {
				f+=",";
			}
		}
		f+="}";
		f+="\n";
		f+="@data\n";
		iter = users.iterator();
	//	while(iter.hasNext()) {
			String username = (String) iter.next();
			//UserKeyboardScan uks = data.get(username);
			Map<Character, Long> ks = uks.getKeyboardScan();

			for (String a : alphabet) {
				Long time = ks.get(a.toCharArray()[0]);
				if (time== null) {
					time = new Long(0);
				}
				f+=time+",";
			}
			
				f+="?\n";
	//	}
		
		try {
			PrintWriter out = new PrintWriter("test.arff");
			out.print(f);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void buildTrainWekaFile() {
		String alph = new String();
		String[] alphabet;
		char c = 'a';
		 
		while (c <= 'z') {
			alph+=c+",";
		  c++;
		}
		alph = alph.toUpperCase();
		alphabet = alph.split(",");
		
		// file
		String f = "@relation weka.arff\n";
		f+="\n";
		for(String a : alphabet) {
			f+="@attribute " + a + " numeric\n";
		}
		
		f+="@attribute class {";
		Set users = data.keySet();
		Iterator iter = users.iterator();
		
		while(iter.hasNext()) {
			f+=iter.next();
			if(iter.hasNext()) {
				f+=",";
			}
		}
		f+="}";
		f+="\n";
		f+="@data\n";
		iter = users.iterator();
		while(iter.hasNext()) {
			String username = (String) iter.next();
			UserKeyboardScan uks = data.get(username);
			Map<Character, Long> ks = uks.getKeyboardScan();

			for (String a : alphabet) {
				Long time = ks.get(a.toCharArray()[0]);
				if (time== null) {
					time = new Long(0);
				}
				f+=time+",";
			}
			
				f+=username+"\n";
		}
		
		try {
			PrintWriter out = new PrintWriter("train.arff");
			out.print(f);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}


	
	public String test() throws Exception {
		String username = null;
		
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader("train.arff"));
		
		Instances train = new Instances(reader);
		train.setClassIndex(train.numAttributes() - 1);
		
		reader = new BufferedReader(new FileReader("test.arff"));
		Instances test = new Instances(reader);
		test.setClassIndex(train.numAttributes()-1);
		
		reader.close();
		
		//NaiveBayes nb = new NaiveBayes();
		weka.classifiers.lazy.IB1 knn = new IB1();

		knn.buildClassifier(train);
		Instances labeled = new Instances(test);
		
		//label instances
		for (int i=0; i<test.numInstances(); i++) {
			double clsLabel = knn.classifyInstance(test.instance(i));
			labeled.instance(i).setClassValue(clsLabel);
		}
		
		//save labeled data 
		BufferedWriter writer = new BufferedWriter(new FileWriter("result.arff"));
		writer.write(labeled.toString());
		writer.close();
		
		
		//read user name 
		BufferedReader br = new BufferedReader(new FileReader("result.arff"));
	    try {
	        String lastLine = br.readLine();
	        String prevLine = null ;
	        while (lastLine != null) {
	        	lastLine = br.readLine();
	        	if(lastLine != null) {
	        		prevLine = lastLine;
	        	}

	        }
	        
	        String lLine[] = prevLine.split(",");
	        username = lLine[lLine.length-1];
	        

	    } finally {
	        br.close();
	    }
	    
	    return username;
	    
		
		
		/*
		Evaluation eval = new Evaluation(train);
		eval.crossValidateModel(knn, train, 10, new Random(1));
		System.out.println(eval.toSummaryString("\nResults\n=======\n", true));
		System.out.println(eval.fMeasure(1)+ " " + eval.precision(1) + " " + eval.recall(1));
		 */
		
		
	}
}
