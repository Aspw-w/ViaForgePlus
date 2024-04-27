package net.aspw.viaforgeplus.injection.forge.mixins.block;

import net.aspw.viaforgeplus.network.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(World.class)
public abstract class MixinWorld implements IBlockAccess {

    /**
     * @author As_pw
     * @reason Destroy Sounds Fix
     */
    @Overwrite
    public boolean destroyBlock(BlockPos pos, boolean dropBlock) {
        IBlockState iblockstate = MinecraftInstance.mc.theWorld.getBlockState(pos);
        Block block = iblockstate.getBlock();

        MinecraftInstance.mc.theWorld.playAuxSFX(2001, pos, Block.getStateId(iblockstate));

        if (block.getMaterial() == Material.air) {
            return false;
        } else {
            if (dropBlock) {
                block.dropBlockAsItem(MinecraftInstance.mc.theWorld, pos, iblockstate, 0);
            }

            return MinecraftInstance.mc.theWorld.setBlockState(pos, Blocks.air.getDefaultState(), 3);
        }
    }
}