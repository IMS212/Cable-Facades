package com.portingdeadmods.cable_facades.utils;

import com.portingdeadmods.cable_facades.data.CableFacadeSavedData;
import com.portingdeadmods.cable_facades.events.ClientFacadeManager;
import com.portingdeadmods.cable_facades.networking.s2c.AddFacadePayload;
import com.portingdeadmods.cable_facades.networking.s2c.RemoveFacadePayload;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class FacadeUtils {
    public static boolean hasFacade(BlockGetter level, BlockPos pos) {
        return getFacade(level, pos) != null;
    }

    @Nullable
    public static BlockState getFacade(BlockGetter level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            return CableFacadeSavedData.get(serverLevel).getFacade(pos);
        }
        return ClientFacadeManager.FACADED_BLOCKS.get(pos);
    }

    public static void addFacade(Level level, BlockPos pos, BlockState blockState) {
        if (level instanceof ServerLevel serverLevel) {
            CableFacadeSavedData.get(serverLevel).addFacade(pos, blockState);
            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, new ChunkPos(pos), new AddFacadePayload(pos, blockState));
        }
    }

    public static void removeFacade(Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            CableFacadeSavedData.get(serverLevel).removeFacade(pos);
            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, new ChunkPos(pos), new RemoveFacadePayload(pos));
        }
    }

    public static void updateBlocks(Level level, BlockPos pos) {
        if(level.isInWorldBounds(pos)){
            BlockState state = level.getBlockState(pos);
            level.sendBlockUpdated(pos, state, state, 3);
            level.updateNeighborsAt(pos, state.getBlock());

            level.getLightEngine().checkBlock(pos);
        }
    }
}
