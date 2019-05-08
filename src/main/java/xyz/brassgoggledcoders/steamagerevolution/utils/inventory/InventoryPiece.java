package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import org.apache.commons.lang3.tuple.Pair;

import scala.actors.threadpool.Arrays;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class InventoryPiece {
	final int xPos[];
	final int yPos[];

	public InventoryPiece(int[] xPos, int[] yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public int getX(int i) {
		return xPos[i];
	}

	public int getY(int i) {
		return yPos[i];
	}

	public static class InventoryPieceProgressBar extends InventoryPiece {

		public InventoryPieceProgressBar(int xPos, int yPos) {
			super(new int[] { xPos }, new int[] { yPos });
		}

	}

	public static class InventoryPieceFluid extends InventoryPiece {
		FluidTankSmart handler;

		public InventoryPieceFluid(FluidTankSmart handler, int[] xPositions, int[] yPositions) {
			super(xPositions, yPositions);
			this.handler = handler;
		}

		public InventoryPieceFluid(FluidTankSmart handler, int xPos, int yPos) {
			this(handler, new int[] { xPos }, new int[] { yPos });
		}

		public FluidTankSmart getHandler() {
			return handler;
		}

		@Deprecated
		public void setHandler(FluidTankSmart tank) {
			handler = tank;
		}

		public void setTankType(TankType type) {
			handler.setTankType(type);
		}
	}

	public static class InventoryPieceItem extends InventoryPiece {
		final ItemStackHandlerExtractSpecific handler;

		public InventoryPieceItem(ItemStackHandlerExtractSpecific handler, int[] xPos, int[] yPos) {
			super(xPos, yPos);
			this.handler = handler;
		}

		public InventoryPieceItem(ItemStackHandlerExtractSpecific handler, int xPos, int yPos) {
			this(handler, new int[] { xPos }, new int[] { yPos });
		}
		
		public InventoryPieceItem(ItemStackHandlerExtractSpecific handler, Pair<int[], int[]> posPair) {
			this(handler, posPair.getLeft(), posPair.getRight());
		}

		public ItemStackHandlerExtractSpecific getHandler() {
			return handler;
		}
	}
}