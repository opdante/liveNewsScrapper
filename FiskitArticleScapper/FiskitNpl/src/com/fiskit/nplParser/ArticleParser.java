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

/**
 * This class uses an NLP parser to extract sentences from live news articles.
 * and creates a list of sentences in csv format.
 * @author Thabang Ngazimbi
 *
 */
public class ArticleParser {
	
	public static void SentenceDetect(String urlstr) throws InvalidFormatException,
	IOException {
		List<String> paragraph = Fetcher.pullAndExtract(urlstr);

		// We start with a model that will be learned from training data
		InputStream istream = new FileInputStream("E:\\Chrome Downloads\\en-sent.bin");
		SentenceModel model = new SentenceModel(istream);
		
		//use SentenceDetectorME to split text into raw sentence
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
		
		CSVWriter writer = new CSVWriter(new FileWriter("E:\\output1.csv"), ',');

		for(String p: paragraph){
			String[] sentences = sdetector.sentDetect(p);
			
			writer.writeNext(sentences);
		}
		
		writer.close();
		istream.close();
		
		System.out.println("Scrape complete....");
	}

	
	public static void main(String[] args) throws InvalidFormatException, IOException{
		
		SentenceDetect(
				"http://www.independent.co.uk/news/world/europe/greece-wants-germany-to-repay-279bn-it-was-forced-to-loan-the-nazi-authorities-during-wwii-10159738.html");
		
	}
}


