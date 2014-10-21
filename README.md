jieba-solr
==========


jieba-analysis adapter for solr


###DEMO
```xml

<fieldType name="text_jieba" class="solr.TextField" positionIncrementGap="100">
  <analyzer type="index">
    <tokenizer class="jieba.solr.JiebaTokenizerFactory"  segMode="SEARCH"/>
    <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" />
    <filter class="solr.LowerCaseFilterFactory"/>
    <filter class="solr.NGramFilterFactory" minGramSize="1" maxGramSize="20"/>
  </analyzer>
  <analyzer type="query">
    <tokenizer class="jieba.solr.JiebaTokenizerFactory"  segMode="SEARCH"/>
    <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" />
    <filter class="solr.SynonymFilterFactory" synonyms="synonyms.txt" ignoreCase="true" expand="true"/>
    <filter class="solr.LowerCaseFilterFactory"/>
    <filter class="solr.NGramFilterFactory" minGramSize="1" maxGramSize="10"/>
  </analyzer>
</fieldType>

```


