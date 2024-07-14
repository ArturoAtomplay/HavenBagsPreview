package me.github.arturoatomplay.havenbagspreview;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HavenBagsPreview implements ModInitializer {
	public static final String MOD_ID = "havenbagspreview";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Bags");
	}
}
