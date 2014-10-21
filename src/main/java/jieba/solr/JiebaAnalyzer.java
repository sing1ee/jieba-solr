/**
 * 
 */
package jieba.solr;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

/**
 * @author zhangcheng
 *
 */
public class JiebaAnalyzer extends Analyzer {
	
	private String segMode;

	/**
	 * 
	 */
	public JiebaAnalyzer() {
		this(SegMode.SEARCH.name());
	}
	
	public JiebaAnalyzer(String segMode) {
		this.segMode = segMode;
	}

	/**
	 * @param reuseStrategy
	 */
	public JiebaAnalyzer(ReuseStrategy reuseStrategy) {
		super(reuseStrategy);
	}

	@Override
	protected TokenStreamComponents createComponents(String arg0, Reader in) {
		return new TokenStreamComponents(new JiebaTokenizer(in, this.segMode));
	}
}
