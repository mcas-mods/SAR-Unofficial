package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;

public abstract class InventoryPiece<INV extends InventoryBasic> {
	String name;
	final int elementX;
	final int elementY;
	public int textureX;
	public int width;
	public int textureY;
	public int height;
	// Offsets texture relative to start position of inventory piece, needed for
	// tanks and slots.
	public int offset;

	private boolean shouldRender = true;

	public INV enclosingInv;

	// Name MUST be unique within the inventory TODO Enforcement
	// TODO Create a 'GUIPiece' subclass that holds texture and position info?
	public InventoryPiece(int xPos, int yPos, int textureX, int textureY, int xSize, int ySize, int offset) {
		this.elementX = xPos;
		this.elementY = yPos;
		this.textureX = textureX;
		this.textureY = textureY;
		this.width = xSize;
		this.height = ySize;
		this.offset = offset;
	}

	// Set by the InventoryBuilder
	public InventoryPiece<INV> setParent(INV amBuilding) {
		this.enclosingInv = amBuilding;
		return this;
	}

	// Set by the InventoryBuilder
	public InventoryPiece<INV> setName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	// GUI Related methods below this line

	public int getX() {
		return elementX;
	}

	public int getY() {
		return elementY;
	}

	@Deprecated
	public void drawScreenCallback(GuiInventory gui, int mouseX, int mouseY, float partialTicks) {
		// NO-OP
	}

	@Deprecated
	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		// NO-OP
	}

	public boolean shouldRender() {
		return shouldRender;
	}

	public void setShouldRender(boolean shouldRender) {
		this.shouldRender = shouldRender;
	}

	public String getTooltip() {
		return null;
	}

	public void mouseClickedCallback(int mouseButton) {
		// NO-OP
	}
}