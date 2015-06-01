
## ES图片搜索插件

The Image Plugin is an Content Based Image Retrieval Plugin for Elasticsearch using [LIRE (Lucene Image Retrieval)](https://code.google.com/p/lire/). It allows users to index images and search for similar images.

It adds an `image` field type and an `image` query

In order to install the plugin, simply run: `bin/plugin -install info.hb/elasticsearch-image/1.2.0`.

|     Image Plugin          |  elasticsearch    | Release date |
|---------------------------|-------------------|:------------:|
| 1.3.0-SNAPSHOT (master)   | 1.1.0             |              |
| 1.2.0                     | 1.0.1             | 2014-03-20   |
| 1.1.0                     | 1.0.1             | 2014-03-13   |
| 1.0.0                     | 1.0.1             | 2014-03-05   |


## 示例

#### 创建Mapping
```sh
curl -XPUT 'localhost:9200/test/test/_mapping' -d '{
    "test": {
        "properties": {
            "my_img": {
                "type": "image",
                "feature": {
                    "CEDD": {
                        "hash": "BIT_SAMPLING"
                    },
                    "JCD": {
                        "hash": ["BIT_SAMPLING", "LSH"]
                    },
                    "FCTH": {}
                },
                "metadata": {
                    "jpeg.image_width": {
                        "type": "string",
                        "store": "yes"
                    },
                    "jpeg.image_height": {
                        "type": "string",
                        "store": "yes"
                    }
                }
            }
        }
    }
}'
```
`type` should be `image`. **Mandatory**

`feature` is a map of features for index. **Mandatory, at least one is required**

`hash` can be set if you want to search on hash. **Optional**

`metadata` is a map of metadata for index, only those metadata will be indexed. See [Metadata](#metadata). **Optional**


#### 索引图片

```sh
curl -XPOST 'localhost:9200/test/test' -d '{
    "my_img": "... base64 encoded image ..."
}'
```

#### 搜素图片

```sh
curl -XPOST 'localhost:9200/test/test/_search' -d '{
    "query": {
        "image": {
            "my_img": {
                "feature": "CEDD",
                "image": "... base64 encoded image to search ...",
                "hash": "BIT_SAMPLING",
                "boost": 2.1,
                "limit": 100
            }
        }
    }
}'
```
`feature` should be one of the features in the mapping.  **Mandatory**

`image` base64 of image to search.  **Optional if search using existing image**

`hash` should be same to the hash set in mapping.  **Optional**

`limit` limit the number of results returned (per shard) for scoring. **Optional, only works when `hash` is specified**

`boost` score boost  **Optional**


#### 搜索已经存在的图片

```sh
curl -XPOST 'localhost:9200/test/test/_search' -d '{
    "query": {
        "image": {
            "my_img": {
                "feature": "CEDD",
                "index": "test",
                "type": "test",
                "id": "image1",
                "path": "my_image",
                "hash": "BIT_SAMPLING"
            }
        }
    }
}'
```
`index` the index to fetch image from. Default to current index.  **Optional**

`type` the type to fetch image from.  **Mandatory**

`id` the id of the document to fetch image from.  **Mandatory**

`path` the field specified as path to fetch image from.  **Mandatory**

`routing` a custom routing value to be used when retrieving the external image doc.  **Optional**


### Metadata说明

Metadata are extracted using [metadata-extractor](https://code.google.com/p/metadata-extractor/). See [SampleOutput](https://code.google.com/p/metadata-extractor/wiki/SampleOutput) for some examples of metadata.

The field name in index will be `directory.tag_name`, all lower case and space becomes underscore(`_`). e.g. if the *Directory* is `JPEG` and *Tag Name* is `Image Height`, the field name will be `jpeg.image_height`



### 支持的图片格式

Images are processed by Java ImageIO, supported formats can be found [here](http://docs.oracle.com/javase/7/docs/api/javax/imageio/package-summary.html)

Additional formats can be supported by ImageIO plugins, for example [TwelveMonkeys](https://github.com/haraldk/TwelveMonkeys)


### 支持的特性

[`AUTO_COLOR_CORRELOGRAM`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/AutoColorCorrelogram.java),  [`BINARY_PATTERNS_PYRAMID`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/BinaryPatternsPyramid.java), [`CEDD`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/CEDD.java), [`SIMPLE_COLOR_HISTOGRAM`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/SimpleColorHistogram.java), [`COLOR_LAYOUT`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/ColorLayout.java), [`EDGE_HISTOGRAM`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/EdgeHistogram.java), [`FCTH`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/FCTH.java), [`GABOR`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/Gabor.java), [`JCD`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/JCD.java), [`JOINT_HISTOGRAM`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/joint/JointHistogram.java), [`JPEG_COEFFICIENT_HISTOGRAM`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/JpegCoefficientHistogram.java), [`LOCAL_BINARY_PATTERNS`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/LocalBinaryPatterns.java), [`LUMINANCE_LAYOUT`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/LuminanceLayout.java), [`OPPONENT_HISTOGRAM`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/OpponentHistogram.java), [`PHOG`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/PHOG.java), [`ROTATION_INVARIANT_LOCAL_BINARY_PATTERNS`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/RotationInvariantLocalBinaryPatterns.java), [`SCALABLE_COLOR`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/ScalableColor.java), [`TAMURA`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/imageanalysis/Tamura.java)


### 支持的Hash模式

[`BIT_SAMPLING`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/indexing/hashing/BitSampling.java), [`LSH`](https://code.google.com/p/lire/source/browse/trunk/src/main/java/net/semanticmetadata/lire/indexing/hashing/LocalitySensitiveHashing.java)

Hash will increase search speed with large data sets

See [Large image data sets with LIRE ?some new numbers](http://www.semanticmetadata.net/2013/03/20/large-image-data-sets-with-lire-some-new-numbers/) 


### 配置

|     Setting          |  Description    | Default |
|----------------------|-----------------|:-------:|
| index.image.use_thread_pool | use multiple thread when multiple features are required | True |
| index.image.ignore_metadata_error| ignore errors happened during extract metadata from image | True |

## 变更

- Use multi-thread when multiple features are required to improve index speed
- Allow index metadata
- Allow query by existing image in index
- Added `limit` in `image` query
- Added plugin version in es-plugin.properties
- initial release