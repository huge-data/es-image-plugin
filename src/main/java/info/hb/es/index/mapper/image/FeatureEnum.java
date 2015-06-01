package info.hb.es.index.mapper.image;

import net.semanticmetadata.lire.imageanalysis.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.BinaryPatternsPyramid;
import net.semanticmetadata.lire.imageanalysis.CEDD;
import net.semanticmetadata.lire.imageanalysis.ColorLayout;
import net.semanticmetadata.lire.imageanalysis.EdgeHistogram;
import net.semanticmetadata.lire.imageanalysis.FCTH;
import net.semanticmetadata.lire.imageanalysis.Gabor;
import net.semanticmetadata.lire.imageanalysis.JCD;
import net.semanticmetadata.lire.imageanalysis.JpegCoefficientHistogram;
import net.semanticmetadata.lire.imageanalysis.LireFeature;
import net.semanticmetadata.lire.imageanalysis.LocalBinaryPatterns;
import net.semanticmetadata.lire.imageanalysis.LuminanceLayout;
import net.semanticmetadata.lire.imageanalysis.OpponentHistogram;
import net.semanticmetadata.lire.imageanalysis.PHOG;
import net.semanticmetadata.lire.imageanalysis.RotationInvariantLocalBinaryPatterns;
import net.semanticmetadata.lire.imageanalysis.ScalableColor;
import net.semanticmetadata.lire.imageanalysis.SimpleColorHistogram;
import net.semanticmetadata.lire.imageanalysis.Tamura;
import net.semanticmetadata.lire.imageanalysis.joint.JointHistogram;

public enum FeatureEnum {

	AUTO_COLOR_CORRELOGRAM(AutoColorCorrelogram.class), //
	BINARY_PATTERNS_PYRAMID(BinaryPatternsPyramid.class), //
	CEDD(CEDD.class), //
	SIMPLE_COLOR_HISTOGRAM(SimpleColorHistogram.class), //
	COLOR_LAYOUT(ColorLayout.class), //
	EDGE_HISTOGRAM(EdgeHistogram.class), //
	FCTH(FCTH.class), GABOR(Gabor.class), //
	JCD(JCD.class), //
	JOINT_HISTOGRAM(JointHistogram.class), //
	JPEG_COEFFICIENT_HISTOGRAM(JpegCoefficientHistogram.class), //
	LOCAL_BINARY_PATTERNS(LocalBinaryPatterns.class), //
	LUMINANCE_LAYOUT(LuminanceLayout.class), //
	OPPONENT_HISTOGRAM(OpponentHistogram.class), //
	PHOG(PHOG.class), //
	ROTATION_INVARIANT_LOCAL_BINARY_PATTERNS(RotationInvariantLocalBinaryPatterns.class), //
	SCALABLE_COLOR(ScalableColor.class), //
	TAMURA(Tamura.class);

	private Class<? extends LireFeature> featureClass;

	FeatureEnum(Class<? extends LireFeature> featureClass) {
		this.featureClass = featureClass;
	}

	public Class<? extends LireFeature> getFeatureClass() {
		return featureClass;
	}

	public static FeatureEnum getByName(String name) {
		return valueOf(name.toUpperCase());
	}

}
