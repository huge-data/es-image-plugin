package info.hb.es.plugin.image;

import static org.elasticsearch.common.collect.Lists.newArrayList;

import java.util.Collection;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;

public class ImagePlugin extends AbstractPlugin {

	@Override
	public String name() {
		return "image";
	}

	@Override
	public String description() {
		return "Elasticsearch Image Plugin";
	}

	@Override
	public Collection<Class<? extends Module>> indexModules() {
		Collection<Class<? extends Module>> modules = newArrayList();
		modules.add(ImageIndexModule.class);
		return modules;
	}

}
