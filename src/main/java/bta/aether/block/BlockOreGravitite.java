package bta.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockOreGravitite extends Block {
    public static boolean fallInstantly = false;

    public BlockOreGravitite(String key, int id) {
        super(key, id, Material.stone);
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        world.scheduleBlockUpdate(i, j, k, this.id, this.tickRate());
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        world.scheduleBlockUpdate(x, y, z, this.id, this.tickRate());
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        this.tryToFall(world, x, y, z);
    }

    private void tryToFall(World world, int i, int j, int k) {
        if (canFallBelow(world, i, j + 1, k) && j < 128) {
            byte byte0 = 32;
            if (!fallInstantly && world.areBlocksLoaded(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0)) {
                EntityFallingGravitite entityfallinggravitite = new EntityFallingGravitite(world, (double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), this.id);
                world.entityJoinedWorld(entityfallinggravitite);
            } else {
                world.setBlockWithNotify(i, j, k, 0);

                while(canFallBelow(world, i, j + 1, k) && j < 128) {
                    ++j;
                }

                if (j > 0) {
                    world.setBlockWithNotify(i, j, k, this.id);
                }
            }
        }

    }

    public int tickRate() {
        return 3;
    }

    public static boolean canFallBelow(World world, int i, int j, int k) {
        int blockId = world.getBlockId(i, j, k);
        if (blockId == 0) {
            return true;
        } else if (blockId == Block.fire.id) {
            return true;
        } else {
            return Block.hasTag(blockId, BlockTags.IS_WATER) ? true : Block.hasTag(blockId, BlockTags.IS_LAVA);
        }
    }
}