package com.mrbysco.measurements.datagen;

import com.mrbysco.measurements.registration.MeasurementRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class MeasurementsDataGen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		var pack = generator.createPack();

		pack.addProvider(MeasurementsRecipeProvider::new);
	}

	public static class MeasurementsRecipeProvider extends FabricRecipeProvider {
		public MeasurementsRecipeProvider(FabricDataOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(packOutput, lookupProvider);
		}

		@Override
		public void buildRecipes(RecipeOutput output) {
			ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, MeasurementRegistry.TAPE_MEASURE_ITEM.get())
					.pattern(" G ")
					.pattern("GIY")
					.pattern(" GY")
					.define('I', ConventionalItemTags.IRON_INGOTS)
					.define('Y', Items.YELLOW_WOOL)
					.define('G', Items.GRAY_WOOL)
					.unlockedBy("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS))
					.unlockedBy("has_yellow_wool", has(Items.YELLOW_WOOL))
					.unlockedBy("has_gray_wool", has(Items.GRAY_WOOL))
					.save(output);
		}
	}
}
