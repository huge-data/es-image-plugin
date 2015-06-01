package info.hb.es.index.query.image;

import info.hb.es.ElasticsearchImageProcessException;

import java.io.IOException;

import net.semanticmetadata.lire.imageanalysis.LireFeature;

import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.BytesRef;

/**
 * 对每副图像计算得分：
 * score = (1 / distance) * boost
 */
public abstract class AbstractImageScorer extends Scorer {

	private final String luceneFieldName;
	private final LireFeature lireFeature;
	private final IndexReader reader;
	private final float boost;
	private BinaryDocValues binaryDocValues;

	protected AbstractImageScorer(Weight weight, String luceneFieldName, LireFeature lireFeature, IndexReader reader,
			float boost) {
		super(weight);
		this.luceneFieldName = luceneFieldName;
		this.lireFeature = lireFeature;
		this.reader = reader;
		this.boost = boost;
	}

	@Override
	public float score() throws IOException {
		assert docID() != NO_MORE_DOCS;

		if (binaryDocValues == null) {
			AtomicReader atomicReader = (AtomicReader) reader;
			binaryDocValues = atomicReader.getBinaryDocValues(luceneFieldName);
		}

		try {
			BytesRef bytesRef = binaryDocValues.get(docID());
			LireFeature docFeature = lireFeature.getClass().newInstance();
			docFeature.setByteArrayRepresentation(bytesRef.bytes);

			float distance = lireFeature.getDistance(docFeature);
			float score;
			// 距离小于1的认为是相似图像
			if (Float.compare(distance, 1.0f) <= 0) {
				score = 2f - distance;
			} else {
				score = 1 / distance;
			}
			return score * boost;
		} catch (Exception e) {
			throw new ElasticsearchImageProcessException("Failed to calculate score", e);
		}
	}

	@Override
	public int freq() {
		return 1;
	}

}
