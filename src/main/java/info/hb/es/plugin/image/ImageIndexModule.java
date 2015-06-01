package info.hb.es.plugin.image;

import info.hb.es.index.mapper.image.RegisterImageType;
import info.hb.es.index.query.image.RegisterImageQueryParser;

import org.elasticsearch.common.inject.AbstractModule;

public class ImageIndexModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(RegisterImageType.class).asEagerSingleton();
		bind(RegisterImageQueryParser.class).asEagerSingleton();
	}

}
