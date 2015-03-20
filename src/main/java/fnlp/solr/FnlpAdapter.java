/**
 * 
 */
package fnlp.solr;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.fnlp.ml.types.Dictionary;
import org.fnlp.nlp.cn.tag.CWSTagger;
import org.fnlp.nlp.corpus.StopWords;

/**
 * @author zhangcheng
 *
 */
public class FnlpAdapter implements Iterator<String> {
	

	private static FnlpAdapter adapter;
	private CWSTagger tagger;
	private StopWords  sw;
	private Iterator<String> tokens;
	/**
	 * 
	 */
	private FnlpAdapter(Reader input, String modelDir, boolean useStop) {
		try {
			tagger = new CWSTagger(modelDir + "/seg.m", new Dictionary(modelDir + "/dict.txt"));
//			tagger.setEnFilter(true);
			if (useStop)
				sw= new StopWords(modelDir + "/stopwords");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static FnlpAdapter getInstance(Reader input, String modelDir, boolean useStop) {
		if (null == adapter) {
			adapter = new FnlpAdapter(input, modelDir, useStop);
		}
		return adapter;
	}
	
	public void reset(Reader input) {
		String raw = null;
		try {
			StringBuilder bdr = new StringBuilder();
			char[] buf = new char[1024];
			int size = 0;
			while ((size = input.read(buf, 0, buf.length)) != -1) {
				String tempstr = new String(buf, 0, size);
				bdr.append(tempstr);
			}
			raw = bdr.toString().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> list = tagger.tag2List(raw);
		LinkedList<String> filt = new LinkedList<String>();
		for (String s : list) {
			if (null != sw && sw.isStopWord(s)) continue;
			filt.addLast(s);
		}
		list.clear();
		list = null;
		tokens = filt.iterator();
	}
	
	@Override
	public boolean hasNext() {
		return tokens.hasNext();
	}

	@Override
	public String next() {
		return tokens.next();
	}

}
