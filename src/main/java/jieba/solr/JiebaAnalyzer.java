/**
 * 
 */
package jieba.solr;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;

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
