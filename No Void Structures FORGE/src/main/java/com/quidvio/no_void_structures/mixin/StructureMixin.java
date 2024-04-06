package com.quidvio.no_void_structures.mixin;

import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Structure.class)
public class StructureMixin {

    /**
     * Stops most Structures from generating in the void.
     *
     * Only most because some legacy structures are weird. See the other Mixins.
     *
     * If the y-level of generation is 8 blocks from the bottom or less. (-56 by default).
     * Stops void generation by returning "false" for the isValidBiome check (this).
     * Otherwise, uses the current return value.
     *
     * @param generationStub default usage, also used to get the gen height
     * @param generationContext default usage, also used to get the void height
     * @param cir the current return value for the isValidBiome check
     */
    @Inject(method = "Lnet/minecraft/world/level/levelgen/structure/Structure;isValidBiome(Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub;Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;)Z", at = @At("RETURN"), cancellable = true)
    private static void no_void_structures_stopGenericStructureVoidGen_S(Structure.GenerationStub generationStub, Structure.GenerationContext generationContext, CallbackInfoReturnable<Boolean> cir) {
        int yPos = generationStub.position().getY();
        if (yPos <= generationContext.chunkGenerator().getMinY() + 8) {
            cir.setReturnValue(false);
        }
        cir.setReturnValue(cir.getReturnValue());
    }

}
