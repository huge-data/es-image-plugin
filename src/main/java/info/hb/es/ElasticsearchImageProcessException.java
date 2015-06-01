package info.hb.es;

import org.elasticsearch.ElasticsearchException;

public class ElasticsearchImageProcessException extends ElasticsearchException {

	private static final long serialVersionUID = -2137042158402309013L;

	public ElasticsearchImageProcessException(String msg) {
		super(msg);
	}

	public ElasticsearchImageProcessException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
