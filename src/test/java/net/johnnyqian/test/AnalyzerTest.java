package net.johnnyqian.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.HMMChineseTokenizer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnny Qian on 8/17/2016.
 */

public class AnalyzerTest {
    private String str = "马云和阿里巴巴都很牛。居然之家与欧特克之间有着战略合作。长春市长春药店。乒乓球拍卖啦。" +
            "薄熙来到重庆。周杰轮周杰伦，范伟骑范玮琪。" +
            "Autodesk builds software that helps people imagine, design, and create a better world.";
    private Reader input = new StringReader(str);

    @Test
    public void testStandardTokenizer() {
        Tokenizer tokenizer = new StandardTokenizer();
        tokenizer.setReader(input);

        testTokenizer(tokenizer);
    }

    @Test
    public void testCJKAnalyzer() {
        Analyzer analyzer = new CJKAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("", input);

        testTokenizer(tokenStream);
    }

    @Test
    public void testHMMChineseTokenizer(){
        Tokenizer tokenizer = new HMMChineseTokenizer();
        tokenizer.setReader(input);

        testTokenizer(tokenizer);
    }

    @Test
    public void testSmartChineseAnalyzer(){
        Analyzer analyzer = new SmartChineseAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("", input);

        testTokenizer(tokenStream);
    }

    private void testTokenizer(TokenStream tokenStream) {
        try {
            List<String> termList = new ArrayList<>();

            tokenStream.reset();
            while(tokenStream.incrementToken()){
                CharTermAttribute termAtt = tokenStream.getAttribute(CharTermAttribute.class);
                termList.add(termAtt.toString());
            }

            tokenStream.close();

            System.out.println(String.join(" | ", termList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
