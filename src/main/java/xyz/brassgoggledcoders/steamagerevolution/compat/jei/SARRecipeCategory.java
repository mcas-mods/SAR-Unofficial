package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MachineRecipe;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMachine;

public class SARRecipeCategory implements IRecipeCategory<MachineRecipe> {

    protected static IGuiHelper helper;

    IHasInventory<InventoryCraftingMachine> machine;
    IDrawableAnimated arrow;

    public SARRecipeCategory(String uid) {
        this.machine = (IHasInventory<InventoryCraftingMachine>) IMachine.referenceMachinesList.get(uid);
        IDrawableStatic arrowDrawable = helper.createDrawable(
                new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/inventory.png"), 176, 83, 24, 17);
        this.arrow = helper.createAnimatedDrawable(arrowDrawable, 20, IDrawableAnimated.StartDirection.LEFT, false);
    }

    public static void setGuiHelper(IGuiHelper helper) {
        SARRecipeCategory.helper = helper;
    }

    @Override
    public String getUid() {
        return SteamAgeRevolution.MODID + ":" + machine.getUID();
    }

    @Override
    public String getTitle() {
        return machine.getLocalizedName();
    }

    @Override
    public String getModName() {
        return SteamAgeRevolution.MODNAME;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/inventory.png"), 7, 4,
                162, 79);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        if(machine instanceof IHasInventory) {
            IHasInventory<?> inventory = (IHasInventory<?>) machine;
            inventory
                    .getInventory().inventoryPieces
                            .forEach(
                                    (name, piece) -> helper
                                            .createDrawable(
                                                    new ResourceLocation(SteamAgeRevolution.MODID,
                                                            "textures/gui/inventory.png"),
                                                    piece.getGUIElement().textureX, piece.getGUIElement().textureY,
                                                    piece.getGUIElement().width, piece.getGUIElement().height)
                                            .draw(minecraft, piece.getX(), piece.getY()));
        }
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MachineRecipe recipeWrapper, IIngredients ingredients) {
        if(machine instanceof IHasInventory) {
            IHasInventory<?> inventory = (IHasInventory<?>) machine;
            int slotIndex = 0;
            for(InventoryPieceFluidTank tank : inventory.getInventory()
                    .getInventoryPiecesOfType(InventoryPieceFluidTank.class)) {
                boolean isInput = IOType.INPUT.equals(tank.getType());
                recipeLayout.getFluidStacks().init(slotIndex, isInput, tank.getX(), tank.getY());
                if(isInput) {
                    recipeLayout.getFluidStacks().set(slotIndex,
                            ingredients.getInputs(VanillaTypes.FLUID).get(slotIndex));
                }
                else {
                    recipeLayout.getFluidStacks().set(slotIndex,
                            ingredients.getOutputs(VanillaTypes.FLUID).get(slotIndex));
                }
                slotIndex++;
            }
            for(InventoryPieceItemHandler handler : inventory.getInventory()
                    .getInventoryPiecesOfType(InventoryPieceItemHandler.class)) {
                boolean isInput = IOType.INPUT.equals(handler.getType());
                recipeLayout.getItemStacks().init(slotIndex, isInput, handler.getX(), handler.getY());
                if(isInput) {
                    recipeLayout.getItemStacks().set(slotIndex,
                            ingredients.getOutputs(VanillaTypes.ITEM).get(slotIndex));
                }
                else {
                    recipeLayout.getItemStacks().set(slotIndex,
                            ingredients.getOutputs(VanillaTypes.ITEM).get(slotIndex));
                }
                slotIndex++;
            }
        }
    }
}
