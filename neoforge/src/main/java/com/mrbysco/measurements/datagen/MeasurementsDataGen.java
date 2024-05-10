package com.mrbysco.measurements.datagen;

import com.mrbysco.measurements.registration.MeasurementRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class MeasurementsDataGen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new MeasurementsRecipeProvider(packOutput, lookupProvider));
		}
	}

	public static class MeasurementsRecipeProvider extends RecipeProvider {
		public MeasurementsRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(packOutput, lookupProvider);
		}

		@Override
		protected void buildRecipes(RecipeOutput output) {
			ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, MeasurementRegistry.TAPE_MEASURE_ITEM.get())
					.pattern(" G ")
					.pattern("GIY")
					.pattern(" GY")
					.define('I', Tags.Items.INGOTS_IRON)
					.define('Y', Items.YELLOW_WOOL)
					.define('G', Items.GRAY_WOOL)
					.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
					.unlockedBy("has_yellow_wool", has(Items.YELLOW_WOOL))
					.unlockedBy("has_gray_wool", has(Items.GRAY_WOOL))
					.save(output);
		}
	}
}
