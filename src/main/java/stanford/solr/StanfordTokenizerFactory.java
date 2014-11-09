package stanford.solr;

import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

public class StanfordTokenizerFactory extends TokenizerFactory {

	private String modelDir;

	public StanfordTokenizerFactory(Map<String, String> args) {
		super(args);
		assureMatchVersion();
		if (args.containsKey("modelDir")) {
			modelDir = args.get("modelDir");
		} else {
			System.out.println("we need model dir for fnlp");
			System.err.println("we need model dir for fnlp");
			System.exit(1);
		}
	}

	@Override
	public Tokenizer create(AttributeFactory arg0, Reader in) {
		return new StanfordTokenizer(in, modelDir);
	}

	public String getModelDir() {
		return modelDir;
	}

	public void setModelDir(String modelDir) {
		this.modelDir = modelDir;
	}
}
