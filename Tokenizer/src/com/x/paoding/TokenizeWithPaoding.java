package com.x.paoding;

import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

public class TokenizeWithPaoding {
	public static void main(String[] args) {
		String line = "中华民族共和国";
		PaodingAnalyzer analyzer = new PaodingAnalyzer();
		StringReader sr = new StringReader(line);
		TokenStream ts = analyzer.tokenStream("", sr);
		try {
			while(ts.incrementToken()){
			CharTermAttribute ta = ts.getAttribute(CharTermAttribute.class);
			System.out.println(ta.toString());
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
