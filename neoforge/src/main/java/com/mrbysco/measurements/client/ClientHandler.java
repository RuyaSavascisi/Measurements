package com.mrbysco.measurements.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderBuffers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.joml.Matrix4f;

public class ClientHandler {
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent.Post event) {
		if (event.getEntity().level().isClientSide) {
			ClientClass.onPlayerTick(event.getEntity());
		}
	}

	@SubscribeEvent
	public void onRenderWorldLast(RenderLevelStageEvent event) {
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
			final Minecraft minecraft = Minecraft.getInstance();
			LocalPlayer player = minecraft.player;
			Matrix4f projectionMatrix = event.getProjectionMatrix();
			PoseStack poseStack = event.getPoseStack();
			RenderBuffers renderBuffers = minecraft.renderBuffers();
			Camera camera = minecraft.gameRenderer.getMainCamera();

			ClientClass.onRenderWorldLast(player, projectionMatrix, poseStack, renderBuffers, camera);
		}
	}
}
