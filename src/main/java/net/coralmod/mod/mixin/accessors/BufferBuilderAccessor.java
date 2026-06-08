package net.coralmod.mod.mixin.accessors;

import com.mojang.blaze3d.vertex.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BufferBuilder.class)
public interface BufferBuilderAccessor {

    @Invoker("beginVertex")
    long invokeBeginVertex();

    @Accessor("vertexSize")
    int getVertexSize();
}
