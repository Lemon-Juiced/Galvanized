package lemon_juice.scalar_utils_galvanized.item.util;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class UtilShape {

    public static List<BlockPos> squareHorizontalFull(final BlockPos pos, int radius){
        List<BlockPos> shape = new ArrayList<BlockPos>();

        int xMin = pos.getX() - radius;
        int xMax = pos.getX() + radius;
        int zMin = pos.getZ() - radius;
        int zMax = pos.getZ() + radius;
        int y = pos.getY();

        //Get the whole square
        for (int x = xMin; x < xMax; x++) {
            for (int z = zMin; z < zMax; z++) {
                shape.add(new BlockPos(x, y, z));
            }
        }

        return shape;
    }

    public static List<BlockPos> squareVerticalX(final BlockPos pos, int xRadius, int yRadius){
        List<BlockPos> shape = new ArrayList<BlockPos>();

        int xMin = pos.getX() - xRadius;
        int xMax = pos.getX() + xRadius;
        int yMin = pos.getY() - yRadius;
        int yMax = pos.getY() + yRadius;
        int z = pos.getZ();

        //Get the whole square
        for(int y = yMin + 1; y < yMax; y++) {
            for(int x = xMin; x <= xMax; x++) {
                shape.add(new BlockPos(x, y, z));
                shape.add(new BlockPos(x, y, z));
            }
        }

        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin + 1; y < yMax; y++) {
                shape.add(new BlockPos(x, y, z));
                shape.add(new BlockPos(x, y, z));
            }
        }

        return shape;
    }

    public static List<BlockPos> squareVerticalZ(final BlockPos pos, int yRadius, int zRadius){
        List<BlockPos> shape = new ArrayList<BlockPos>();

        int x = pos.getX();
        int zMin = pos.getZ() - zRadius;
        int zMax = pos.getZ() + zRadius;
        int yMin = pos.getY() - yRadius;
        int yMax = pos.getY() + yRadius;

        for (int y = yMin + 1; y < yMax; y++) {
            for (int z = zMin; z <= zMax ; z++) {
                shape.add(new BlockPos(x, y, z));
                shape.add(new BlockPos(x, y, z));
            }
        }

        for (int z = zMin; z < zMax; z++) {
            for (int y = yMin + 1; y < yMax; y++) {
                shape.add(new BlockPos(x, y, z));
                shape.add(new BlockPos(x, y, z));
            }
        }

        return shape;
    }

    public static List<BlockPos> squareHorizontalHollow(final BlockPos pos, int radius) {
        return rectHollow(pos, radius, radius);
    }

    public static List<BlockPos> rect(final BlockPos pos, final BlockPos target){
        List<BlockPos> shape = new ArrayList<BlockPos>();
        if(pos == null || target == null){
            return shape;
        }

        int xMin = Math.min(pos.getX(), target.getX());
        int yMin = Math.min(pos.getY(), target.getY());
        int zMin = Math.min(pos.getZ(), target.getZ());
        int xMax = Math.max(pos.getX(), target.getX());
        int yMax = Math.max(pos.getY(), target.getY());
        int zMax = Math.max(pos.getZ(), target.getZ());

        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax ; z++) {
                for (int y = yMin; y < yMax; y++) {
                    shape.add(new BlockPos(x, y, z));
                }
            }
        }

        return shape;
    }

    public static List<BlockPos> rectHollow(final BlockPos pos, int radiusX, int radiusZ) {
        List<BlockPos> shape = new ArrayList<BlockPos>();

        int xMin = pos.getX() - radiusX;
        int xMax = pos.getX() + radiusX;
        int zMin = pos.getZ() - radiusZ;
        int zMax = pos.getZ() + radiusZ;
        int y = pos.getY();

        for (int x = xMin; x <= xMax; x++) {
            shape.add(new BlockPos(x, y, zMin));
            shape.add(new BlockPos(x, y, zMax));
        }

        for (int z = zMin + 1; z < zMax; z++) {
            shape.add(new BlockPos(xMin, y, z));
            shape.add(new BlockPos(xMax, y, z));
        }

        return shape;
    }
}
