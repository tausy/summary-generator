package com.sumgen.controller;

import com.sumgen.Ranker.LexRank;
import com.sumgen.Ranker.Parser;
import com.sumgen.Ranker.Sentence;
import com.sumgen.webtextextractor.WebPageTextExtractor;

public class Controller {
    private static Sentence[] parsedSentences;
    private static Sentence[] summary;
    private String summarizedContent="";

    public String getSummary(String url, int summaryLength) {
    	
	    WebPageTextExtractor wpTextExtractor = new WebPageTextExtractor();
		String summarizableText="";;
		try {
			//"http://www.paulgraham.com/growth.html"
			summarizableText = wpTextExtractor.extractText(url);
			System.out.println(summarizableText);
			Parser p = new Parser(summarizableText);
			parsedSentences = p.getParsedSentences();
			
			
			System.out.println(parsedSentences);
			summary = new LexRank(parsedSentences, summaryLength).getSummary();
			
			for(Sentence str: summary)
				summarizedContent += str.getOriginal();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return summarizedContent;
    }
}