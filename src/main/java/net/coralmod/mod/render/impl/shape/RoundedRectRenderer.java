package net.coralmod.mod.render.impl.shape;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.coralmod.mod.utils.ByteBufferVertexConsumer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2f;
import org.jspecify.annotations.NonNull;

import java.awt.*;

public final class RoundedRectRenderer {

    private RoundedRectRenderer() {
    }

    private static final VertexFormat ROUNDED_RECT_FORMAT = VertexFormat.builder(0)
            .addAttribute("Position", GpuFormat.RGB32_FLOAT)
            .addAttribute("UV0", GpuFormat.RG32_FLOAT)
            .addAttribute("UV1", GpuFormat.RG32_FLOAT)
            .addAttribute("Radius", GpuFormat.R32_FLOAT)
            .addAttribute("Color", GpuFormat.RGBA8_UNORM)
            .build();

    private static final RenderPipeline ROUNDED_RECT_PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.GUI_SNIPPET)
                    .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                    .withVertexBinding(0, ROUNDED_RECT_FORMAT)
                    .withPrimitiveTopology(PrimitiveTopology.QUADS)
                    .withCull(false)
                    .withFragmentShader(Identifier.fromNamespaceAndPath("coralmod", "core/rounded_rect"))
                    .withVertexShader(Identifier.fromNamespaceAndPath("coralmod", "core/rounded_rect"))
                    .withLocation(Identifier.fromNamespaceAndPath("coralmod", "pipeline/2d/quad_rr"))
                    .build()
    );

    public static void render(@NonNull GuiGraphicsExtractor graphics, float x, float y, float width, float height, float radius, Color color) {
        final Matrix3x2f matrices = new Matrix3x2f(graphics.pose());
        final ScreenRectangle scissor = graphics.scissorStack.peek();

        final ScreenRectangle bounds = new ScreenRectangle(
                (int) x,
                (int) y,
                (int) Math.ceil(width),
                (int) Math.ceil(height)
        );

        graphics.guiRenderState.addGuiElement(new GuiElementRenderState() {
            @Override
            public void buildVertices(@NonNull VertexConsumer consumer) {
                final int r = color.getRed();
                final int g = color.getGreen();
                final int b = color.getBlue();
                final int a = color.getAlpha();

                final ByteBufferVertexConsumer vertexConsumer = new ByteBufferVertexConsumer((BufferBuilder) consumer);

                vertexConsumer.addVertexWith2DPose(matrices, x, y + height)
                        .setUv(0, 0)
                        .setUv(width, height);
                vertexConsumer.putRadius(radius);
                vertexConsumer.setColor(r, g, b, a);

                vertexConsumer.addVertexWith2DPose(matrices, x + width, y + height)
                        .setUv(width, 0)
                        .setUv(width, height);
                vertexConsumer.putRadius(radius);
                vertexConsumer.setColor(r, g, b, a);

                vertexConsumer.addVertexWith2DPose(matrices, x + width, y)
                        .setUv(width, height)
                        .setUv(width, height);
                vertexConsumer.putRadius(radius);
                vertexConsumer.setColor(r, g, b, a);

                vertexConsumer.addVertexWith2DPose(matrices, x, y)
                        .setUv(0, height)
                        .setUv(width, height);
                vertexConsumer.putRadius(radius);
                vertexConsumer.setColor(r, g, b, a);
            }

            @Override
            public @NonNull RenderPipeline pipeline() {
                return ROUNDED_RECT_PIPELINE;
            }

            @Override
            public @NonNull TextureSetup textureSetup() {
                return TextureSetup.noTexture();
            }

            @Override
            public ScreenRectangle scissorArea() {
                return scissor;
            }

            @Override
            public ScreenRectangle bounds() {
                return bounds;
            }
        });
    }
}
