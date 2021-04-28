package com.miketheshadow.worldsaver;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.miketheshadow.worldsaver.WorldSaver.LOGGER;

public class ChunkLoadHandler {

    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

    @SubscribeEvent()
    public static void onChunkLoadEvent(ChunkEvent.Load event) {

        String worldName = event.getWorld().getWorldInfo().getWorldName();
        if(!worldName.equals("AincradFloor1_Final")) return;
        Chunk chunk = event.getChunk();
        if(chunk.isLoaded()) {
            service.schedule(() -> {
                for(int x = 0; x < 16; x ++) {
                    for(int y = 0; y < 255; y ++) {
                        for(int z = 0; z < 16; z ++) {
                            int finalX = (chunk.x * 16) + x;
                            int finalZ = (chunk.z * 16) + z;
                            IBlockState state = chunk.getBlockState(finalX,y, + finalZ);
                            Block block = state.getBlock();

                            if(block.getRegistryName() == null || block.getRegistryName().toString().contains("minecraft:")) {
                                continue;
                            }
                            String material = block.getRegistryName().toString();
                            HashMap<String,String> dataMap = new HashMap<>();

                            if(block.hasTileEntity(state)) {
                                TileEntity entity = event.getWorld().getTileEntity(new BlockPos(finalX,y,finalZ));
                                if(entity != null) {

                                    NBTTagCompound compound = new NBTTagCompound();
                                    compound = entity.writeToNBT(compound);
                                    dataMap.put("NBT",compound.toString());
                                } else {
                                    LOGGER.info("Unable to find tile entity!");
                                }

                            }

                            for(IProperty<?> key : state.getPropertyKeys()) {
                                dataMap.put(key.getName(),state.getProperties().get(key).toString());
                            }
                            saveBlock(finalX, y, finalZ, material,dataMap);
                        }
                    }
                }
            },1,TimeUnit.SECONDS);
        }
    }

    public static void saveBlock(int x,int y,int z,String material,HashMap<String,String> dataMap) {
        CustomBlock customBlock = new CustomBlock(dataMap);

        customBlock.material = material;

        customBlock.x = x;
        customBlock.y = y;
        customBlock.z = z;

        customBlock.save();
    }

}
