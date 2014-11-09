/**
 * 
 */
package stanford.solr;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeFactory;

/**
 * @author zhangcheng
 *
 */
public class StanfordTokenizer extends Tokenizer {

	private StanfordAdapter adapter;
	
	private CharTermAttribute termAtt;
	/**
	 * @param input
	 */
	public StanfordTokenizer(Reader input, String modelDir) {
		super(input);
		 this.termAtt = addAttribute(CharTermAttribute.class);
		adapter = StanfordAdapter.getInstance(input, modelDir);
	}

	/**
	 * @param factory
	 * @param input
	 */
	public StanfordTokenizer(AttributeFactory factory, Reader input) {
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
