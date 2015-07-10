package com.fiskit.nplParser;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

import com.fiskkit.Fetcher;
import com.opencsv.CSVWriter;

public class ArticleParser {
	
	public static void SentenceDetect(String urlstr) throws InvalidFormatException,
	IOException {
		List<String> paragraph = Fetcher.pullAndExtract(urlstr);

		// always start with a model, a model is learned from training data
		InputStream is = new FileInputStream("E:\\Chrome Downloads\\en-sent.bin");
		SentenceModel model = new SentenceModel(is);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
		
		CSVWriter writer = new CSVWriter(new FileWriter("E:\\output.csv"), '\n');
		
		int x = 1;
		for(String p: paragraph){
			String[] sentences = sdetector.sentDetect(p);
			
			writer.writeNext(sentences);
			
			System.out.println("Paragraph " + x);
			for(int i = 0; i < sentences.length; i++ ){
				System.out.println(sentences[i]);
			}
			System.out.println("");
			x++;
		}
		
		writer.close();
		is.close();
	}

	
	public static void main(String[] args) throws InvalidFormatException, IOException{
		
		SentenceDetect("http://www.weeklystandard.com/blogs/intel-chief-blasts-obama_802242.html");
		
	}
}


