package jieba.solr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

public class JiebaTokenizer extends Tokenizer {

	// Jieba分词器实现
	private JiebaSegmenter jiebaTagger;

	// 词元文本属性
	private final CharTermAttribute termAtt;
	// 词元位移属性
	private final OffsetAttribute offsetAtt;
	// 词元分类属性（该属性分类参考org.wltea.analyzer.core.Lexeme中的分类常量）
	private final TypeAttribute typeAtt;
	// 记录最后一个词元的结束位置
	private int endPosition;
	// Jieba SegMode: INDEX or SEARCH
	private final SegMode segMode;
	// Iterator of SegToken
	private final Iterator<SegToken> tokens;

	protected JiebaTokenizer(Reader input, String segModeName) {
		super(input);
	    this.offsetAtt = addAttribute(OffsetAttribute.class);
	    this.termAtt = addAttribute(CharTermAttribute.class);
	    this.typeAtt = addAttribute(TypeAttribute.class);
	    this.jiebaTagger = new JiebaSegmenter();
	    if (null == segModeName) {
	    	segMode = SegMode.SEARCH;
	    } else {
	    	segMode = SegMode.valueOf(segModeName);
	    }
	    
	    StringBuilder bdr = new StringBuilder();
	    
	    try {
			BufferedReader reader = new BufferedReader(input);
			String line = null;
			while (null != (line = reader.readLine())) {
				bdr.append(line).append(" ");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    tokens = jiebaTagger.process(bdr.toString(), segMode).iterator();
	}

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
		SegToken token = tokens.next();
		if(token != null){
			//将Lexeme转成Attributes
			//设置词元文本
			termAtt.append(token.word.getToken());
			//设置词元长度
			termAtt.setLength(token.word.length());
			//设置词元位移
			offsetAtt.setOffset(token.startOffset, token.endOffset);
			//记录分词的最后位置
			endPosition = token.endOffset;
			//记录词元分类
			typeAtt.setType(token.word.getTokenType());			
			//返会true告知还有下个词元
			return true;
		}
		//返会false告知词元输出完毕
		return false;
	}

	@Override
	public void end() throws IOException {
		int finalOffset = correctOffset(this.endPosition);
		offsetAtt.setOffset(finalOffset, finalOffset);
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		jiebaTagger = new JiebaSegmenter();
	}
	
	
}
