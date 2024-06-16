package com.mrbysco.measurements;

import com.mrbysco.measurements.client.ClientHandler;
import com.mrbysco.measurements.client.LoginHandler;
import com.mrbysco.measurements.config.MeasurementConfigNeoForge;
import com.mrbysco.measurements.registration.MeasurementRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(Constants.MOD_ID)
public class MeasurementsNeoForge {

	public MeasurementsNeoForge(IEventBus eventBus, Dist dist, ModContainer container) {
		CommonClass.init();

		eventBus.addListener(this::addTabContents);

		if (dist.isClient()) {
			container.registerConfig(ModConfig.Type.CLIENT, MeasurementConfigNeoForge.clientSpec);
			eventBus.register(MeasurementConfigNeoForge.class);

			NeoForge.EVENT_BUS.register(new ClientHandler());
			NeoForge.EVENT_BUS.register(new LoginHandler());
		}
	}

	@SubscribeEvent
	public void addTabContents(final BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(MeasurementRegistry.TAPE_MEASURE_ITEM.get());
		}
	}
}