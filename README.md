jieba-solr
==========


jieba-analysis adapter for solr


###Require
Lucene 4.9.0


###DEMO

####结巴分词

```xml

    <fieldType name="text_jieba" class="solr.TextField" positionIncrementGap="100">
      <analyzer type="index">
        <tokenizer class="jieba.solr.JiebaTokenizerFactory"  segMode="SEARCH"/>
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" />
        <filter class="solr.LowerCaseFilterFactory"/>
        <filter class="solr.SnowballPorterFilterFactory" language="English"/>
      </analyzer>
      <analyzer type="query">
        <tokenizer class="jieba.solr.JiebaTokenizerFactory"  segMode="SEARCH"/>
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" />
        <filter class="solr.SynonymFilterFactory" synonyms="synonyms.txt" ignoreCase="true" expand="true"/>
        <filter class="solr.LowerCaseFilterFactory"/>
        <filter class="solr.SnowballPorterFilterFactory" language="English"/>
      </analyzer>
    </fieldType>

```

####斯坦福中文分词

```xml

    <fieldType name="text_stanford" class="solr.TextField" positionIncrementGap="100">
      <analyzer type="index">
        <tokenizer class="stanford.solr.StanfordTokenizerFactory"  modelDir="/Users/zhangcheng/Downloads/softwares/stanford-segmenter-2014-06-16/data" />
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" />
        <filter class="solr.LowerCaseFilterFactory"/>
        <filter class="solr.SnowballPorterFilterFactory" language="English"/>
      </analyzer>
      <analyzer type="query">
        <tokenizer class="stanford.solr.StanfordTokenizerFactory"  modelDir="/Users/zhangcheng/Downloads/softwares/stanford-segmenter-2014-06-16/data" />
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" />
        <filter class="solr.LowerCaseFilterFactory"/>
        <filter class="solr.SnowballPorterFilterFactory" language="English"/>
      </analyzer>
    </fieldType>

```

####复旦中文分词

```xml

    <fieldType name="text_fnlp" class="solr.TextField" positionIncrementGap="100">
      <analyzer type="index">
        <tokenizer class="fnlp.solr.FnlpTokenizerFactory"  modelDir="/Users/zhangcheng/Documents/fnlp_models/fnlp_modes" useStop="false"/>
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" />
        <filter class="solr.LowerCaseFilterFactory"/>
        <filter class="solr.SnowballPorterFilterFactory" language="English"/>
      </analyzer>
      <analyzer type="query">
        <tokenizer class="fnlp.solr.FnlpTokenizerFactory"  modelDir="/Users/zhangcheng/Documents/fnlp_models/fnlp_modes"/>
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" />
        <!--<filter class="solr.SynonymFilterFactory" synonyms="synonyms.txt" ignoreCase="true" expand="true"/>-->
        <filter class="solr.LowerCaseFilterFactory"/>
        <filter class="solr.SnowballPorterFilterFactory" language="English"/>
      </analyzer>
    </fieldType>

```

###Thanks
[结巴分词](https://github.com/fxsjy/jieba) 非常给力，感谢@fxsjy 给我们带来了如此给力的工具。
