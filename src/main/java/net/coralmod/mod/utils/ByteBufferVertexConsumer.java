package net.coralmod.mod.utils;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.coralmod.mod.mixin.accessors.BufferBuilderAccessor;
import org.jspecify.annotations.NonNull;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteBufferVertexConsumer implements VertexConsumer {

    private final BufferBuilder bufferBuilder;
    private ByteBuffer buffer;

    public ByteBufferVertexConsumer(BufferBuilder bufferBuilder) {
        this.bufferBuilder = bufferBuilder;
        beginVertex();
    }

    private void beginVertex() {
        final BufferBuilderAccessor accessor = (BufferBuilderAccessor) bufferBuilder;

        buffer = MemoryUtil.memByteBuffer(accessor.invokeBeginVertex(), accessor.getVertexSize()).order(ByteOrder.nativeOrder());
    }

    private void ensureSpace() {
        if (!buffer.hasRemaining()) {
            beginVertex();
        }
    }

    public void putRadius(float radius) {
        ensureSpace();
        buffer.putFloat(radius);
    }

    @Override
    public @NonNull VertexConsumer addVertex(float x, float y, float z) {
        ensureSpace();
        buffer.putFloat(x);
        buffer.putFloat(y);
        buffer.putFloat(z);
        return this;
    }

    @Override
    public @NonNull VertexConsumer setUv(float u, float v) {
        ensureSpace();
        buffer.putFloat(u);
        buffer.putFloat(v);
        return this;
    }

    @Override
    public @NonNull VertexConsumer setColor(int r, int g, int b, int a) {
        ensureSpace();
        buffer.put((byte) r);
        buffer.put((byte) g);
        buffer.put((byte) b);
        buffer.put((byte) a);
        return this;
    }

    @Override
    public @NonNull VertexConsumer setColor(int argb) {
        return setColor(
                (argb >> 16) & 0xFF,
                (argb >> 8) & 0xFF,
                argb & 0xFF,
                (argb >> 24) & 0xFF
        );
    }

    @Override
    public @NonNull VertexConsumer setUv1(int u, int v) {
        return this;
    }

    @Override
    public @NonNull VertexConsumer setUv2(int u, int v) {
        return this;
    }

    @Override
    public @NonNull VertexConsumer setNormal(float x, float y, float z) {
        return this;
    }

    @Override
    public @NonNull VertexConsumer setLineWidth(float width) {
        return this;
    }
}