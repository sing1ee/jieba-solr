package jieba.solr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

public class JiebaTokenizer extends Tokenizer {

	// Jieba分词器实现
	private JiebaSegmenter jiebaTagger;

	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;
	private final TypeAttribute typeAtt;
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
	    List<SegToken> list = jiebaTagger.process(bdr.toString().trim(), segMode);
	    tokens = list.iterator();
	}

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
		if(tokens.hasNext()){
			SegToken token = tokens.next();
			termAtt.append(token.word.getToken());
			termAtt.setLength(token.word.length());
			offsetAtt.setOffset(token.startOffset, token.endOffset);
			endPosition = token.endOffset;
			typeAtt.setType(token.word.getTokenType());			
			return true;
		}
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
