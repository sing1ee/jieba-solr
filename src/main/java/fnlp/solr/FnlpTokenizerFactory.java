package fnlp.solr;

import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

public class FnlpTokenizerFactory extends TokenizerFactory {

	private String modelDir;
	private boolean useStop = true;

	public FnlpTokenizerFactory(Map<String, String> args) {
		super(args);
		assureMatchVersion();
		if (args.containsKey("modelDir")) {
			modelDir = args.get("modelDir");
		} else {
			System.out.println("we need model dir for fnlp");
			System.err.println("we need model dir for fnlp");
			System.exit(1);
		}
		if (args.containsKey("useStop")) {
			useStop = Boolean.parseBoolean(args.get("useStop"));
		}
	}

	@Override
	public Tokenizer create(AttributeFactory arg0, Reader in) {
		return new FnlpTokenizer(in, modelDir, this.useStop);
	}

	public String getModelDir() {
		return modelDir;
	}

	public void setModelDir(String modelDir) {
		this.modelDir = modelDir;
	}
}
