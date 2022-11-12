package lemon_juice.galvanized.item.custom;

import lemon_juice.galvanized.item.util.DataTags;
import lemon_juice.galvanized.item.util.UtilShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;

public class BoreItem extends DiggerItem {
    final int radius; //Radius 1 is 3x3 (1 Up, 1 Down, 1 Left, 1 Right, and Corners); 2 is 5x5; 3 is 7x7, etc.

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    public BoreItem(Tier tier, Properties properties, int radius) {
        super(5.0F, -3.0F, tier, DataTags.WITH_BORE, properties);
        this.radius = radius;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        Level world = player.level;
        HitResult ray = getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);

        //Get Y-Offset
        int yOff = 0;
        if (radius == 2 && player.isCrouching()) {
            yOff = 1;
        }

        if (ray != null && ray.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) ray;
            Direction sideHit = blockHitResult.getDirection();
            List<BlockPos> shape;

            if (sideHit == Direction.UP || sideHit == Direction.DOWN) { //If UP/DOWN
                shape = UtilShape.squareHorizontalHollow(pos, radius);
                if (radius == 2) {
                    shape.addAll(UtilShape.squareHorizontalHollow(pos, radius - 1));
                }
            } else if (sideHit == Direction.EAST || sideHit == Direction.WEST) { //If EAST/WEST
                int y = 1 + radius + yOff;
                int z = radius;
                shape = UtilShape.squareVerticalZ(pos, y, z);
            } else { //Must be NORTH/SOUTH
                int x = radius;
                int y = 1 + radius - yOff;
                shape = UtilShape.squareVerticalX(pos, x, y);
            }

            for (BlockPos posCurrent : shape) {
                BlockState currentBlockState = world.getBlockState(posCurrent);
                if (currentBlockState.isAir()) {
                    continue;
                }

                if (currentBlockState.getDestroySpeed(world, posCurrent) >= 0
                        && player.mayUseItemAt(posCurrent, sideHit, stack)
                        && ForgeEventFactory.doPlayerHarvestCheck(player, currentBlockState, true)
                        && this.getDestroySpeed(stack, currentBlockState) > 1
                        && (currentBlockState.canHarvestBlock(world, pos, player)
                        || currentBlockState.is(this.getTier().getTag()))) {
                    stack.mineBlock(world, currentBlockState, posCurrent, player);
                    Block blockCurrent = currentBlockState.getBlock();
                    if (world.isClientSide) {
                        world.levelEvent(2001, posCurrent, Block.getId(currentBlockState));
                        if (blockCurrent.onDestroyedByPlayer(currentBlockState, world, posCurrent, player, true, currentBlockState.getFluidState())) {
                            blockCurrent.destroy(world, posCurrent, currentBlockState);
                        }
                    } else if (player instanceof ServerPlayer) {
                        ServerPlayer mp = (ServerPlayer) player;
                        int xpGivenOnDrop = ForgeHooks.onBlockBreakEvent(world, ((ServerPlayer) player).gameMode.getGameModeForPlayer(), (ServerPlayer) player, posCurrent);
                        if (blockCurrent.onDestroyedByPlayer(currentBlockState, world, posCurrent, player, true, currentBlockState.getFluidState()) && world instanceof ServerLevel) {
                            BlockEntity tile = world.getBlockEntity(posCurrent);
                            blockCurrent.destroy(world, posCurrent, currentBlockState);
                            blockCurrent.playerDestroy(world, player, posCurrent, currentBlockState, tile, stack);
                            blockCurrent.popExperience((ServerLevel) world, posCurrent, xpGivenOnDrop);
                        }
                        mp.connection.send(new ClientboundBlockUpdatePacket(world, posCurrent));
                    }
                }
            }
        }

        return super.onBlockStartBreak(stack, pos, player);
    }
}
