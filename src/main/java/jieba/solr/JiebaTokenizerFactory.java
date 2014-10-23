package jieba.solr;

import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

public class JiebaTokenizerFactory extends TokenizerFactory {
	
	private String segMode;
	
	public JiebaTokenizerFactory(Map<String, String> args) {
		super(args);
        assureMatchVersion();
        if (null == args.get("segMode")) {
        	segMode = SegMode.SEARCH.name();
        } else {
        	segMode = args.get("segMode");
        }
	}

	@Override
	public Tokenizer create(AttributeFactory arg0, Reader in) {
		return new JiebaTokenizer(in, segMode);
	}

	public String getSegMode() {
		return segMode;
	}

	public void setSegMode(String segMode) {
		this.segMode = segMode;
	}

}
