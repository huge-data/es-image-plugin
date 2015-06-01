package info.hb.es.index.query.image;

import java.util.Map;

import org.elasticsearch.common.collect.MapMaker;

public class ImageScoreCache {

	private Map<String, Float> scoreCache = new MapMaker().makeMap();

	public Float getScore(String key) {
		if (!scoreCache.containsKey(key)) {
			return null;
		}
		return scoreCache.get(key);
	}

	public void setScore(String key, Float score) {
		scoreCache.put(key, score);
	}

}
