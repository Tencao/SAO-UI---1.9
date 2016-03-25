package com.bluexin.saoui.effects;

import com.bluexin.saoui.GLCore;
import com.bluexin.saoui.effects.DeathParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.profiler.Profiler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * This code was original created by <Vazkii> and has been modified to our needs
 * All credit goes to him
 */
@SideOnly(Side.CLIENT)
public final class RenderDispatcher {

    public static int particleFxCount = 0;

    public static void dispatch() {
        Tessellator tessellator = Tessellator.getInstance();

        Profiler profiler = Minecraft.getMinecraft().mcProfiler;

        //GL11.glPushAttrib(GL11.GL_LIGHTING);
        GLCore.glStart();
        GLCore.glBlend(true);
        GLCore.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

        profiler.startSection("death particle");
        DeathParticles.dispatchQueuedRenders(tessellator);
        profiler.endSection();
        GLCore.blendFunc(GL11.GL_ONE, GL11.GL_ZERO);
        GLCore.glBlend(false);
        GLCore.glEnd();
        //GL11.glPopAttrib();
    }
}
