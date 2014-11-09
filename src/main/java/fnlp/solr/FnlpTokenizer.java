/**
 * 
 */
package fnlp.solr;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeFactory;

/**
 * @author zhangcheng
 *
 */
public class FnlpTokenizer extends Tokenizer {

	private FnlpAdapter adapter;
	
	private CharTermAttribute termAtt;
	/**
	 * @param input
	 */
	public FnlpTokenizer(Reader input, String modelDir, boolean useStop) {
		super(input);
		 this.termAtt = addAttribute(CharTermAttribute.class);
		adapter = FnlpAdapter.getInstance(input, modelDir, useStop);
	}

	/**
	 * @param factory
	 * @param input
	 */
	public FnlpTokenizer(AttributeFactory factory, Reader input) {
		super(factory, input);
	}

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
		if(adapter.hasNext()){
			String token = adapter.next();
			termAtt.append(token);
			termAtt.setLength(token.length());
			return true;
		}
		return false;
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		adapter.reset(this.input);
	}
}
