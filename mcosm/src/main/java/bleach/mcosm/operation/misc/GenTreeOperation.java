package bleach.mcosm.operation.misc;

import java.util.Random;

import bleach.mcosm.operation.Operation;
import bleach.mcosm.operation.OperationThread;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenBirchTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class GenTreeOperation extends Operation {
	
	public GenTreeOperation(BlockPos pos) {
		this(pos, BlockPlanks.EnumType.OAK);
	}
	
	public GenTreeOperation(BlockPos pos, BlockPlanks.EnumType type) {
		this.thread = new OperationThread<Void>() {
			
			public void run() {
				World world = Minecraft.getMinecraft().getIntegratedServer().getWorld(Minecraft.getMinecraft().player.dimension);
				
				Random rand = new Random();
				WorldGenAbstractTree gen = null;
				
				switch (type) {
					case OAK:
						gen = new WorldGenBigTree(true);
						break;
					case BIRCH:
						gen = new WorldGenBirchTree(true, true);
						break;
					case SPRUCE:
						gen = new WorldGenTaiga2(true);
						break;
					case ACACIA:
						gen = new WorldGenSavannaTree(true);
						break;
					case DARK_OAK:
						gen = new WorldGenCanopyTree(true);
						break;
					case JUNGLE:
						IBlockState iblockstate = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
		                IBlockState iblockstate1 = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE)
		                		.withProperty(BlockLeaves.CHECK_DECAY, false);
						gen = new WorldGenTrees(true, 4 + rand.nextInt(7), iblockstate, iblockstate1, false);
						break;
				}
				
				gen.generate(world, rand, pos);
				setProgress(1);
			}
		};
	}
	
	protected boolean useMainThread() {
		return true;
	}

}
