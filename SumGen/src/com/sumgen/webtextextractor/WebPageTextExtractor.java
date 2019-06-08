package com.sumgen.webtextextractor;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebPageTextExtractor {
	
	String textFromWebpage;



public String extractText(String url) throws Exception
{
	
	try{
	Document doc = Jsoup.parse(new URL(url), 6000);

	Elements body = doc.select("body");

	textFromWebpage=body.text();

	}catch(Exception e){
		
		e.printStackTrace();
	}
	//System.out.println(textFromWebpage);

	return textFromWebpage;
}

}
