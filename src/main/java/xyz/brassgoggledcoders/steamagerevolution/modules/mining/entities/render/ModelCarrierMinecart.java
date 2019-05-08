package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

/**
 * ModelMinecart - FreneticScribbler
 * Created using Tabula 7.0.1
 */
public class ModelCarrierMinecart extends ModelBase {
    public ModelRenderer back;
    public ModelRenderer front;
    public ModelRenderer bottom;
    public ModelRenderer right;
    public ModelRenderer left;
    public ModelRenderer pillar1;
    public ModelRenderer pillar2;
    public ModelRenderer pillar3;
    public ModelRenderer pillar4;
    public ModelRenderer side1;
    public ModelRenderer side2;
    public ModelRenderer backdiag;
    public ModelRenderer backinner;
    public ModelRenderer frontdiag;
    public ModelRenderer frontinner;
    public ModelRenderer fill;

    public ModelCarrierMinecart() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.right = new ModelRenderer(this, 0, 12);
        this.right.setRotationPoint(0.0F, -3.0F, -7.0F);
        this.right.addBox(-8.0F, -3.0F, -1.0F, 16, 3, 2, 0.0F);
        this.setRotateAngle(right, 0.0F, 3.141592653589793F, 0.0F);
        this.pillar1 = new ModelRenderer(this, 38, 0);
        this.pillar1.setRotationPoint(-10.0F, 3.0F, 6.0F);
        this.pillar1.addBox(0.0F, -6.0F, 0.0F, 2, 6, 2, 0.0F);
        this.side2 = new ModelRenderer(this, 2, 4);
        this.side2.setRotationPoint(0.0F, -1.0F, -4.0F);
        this.side2.addBox(-9.0F, -6.0F, -1.0F, 18, 11, 2, 0.0F);
        this.setRotateAngle(side2, -2.443460952792061F, 0.0F, 0.0F);
        this.pillar3 = new ModelRenderer(this, 52, 0);
        this.pillar3.setRotationPoint(-10.0F, 3.0F, -8.0F);
        this.pillar3.addBox(0.0F, -6.0F, 0.0F, 2, 6, 2, 0.0F);
        this.left = new ModelRenderer(this, 56, 26);
        this.left.setRotationPoint(0.0F, -3.0F, 7.0F);
        this.left.addBox(-8.0F, -3.0F, -1.0F, 16, 3, 2, 0.0F);
        this.pillar4 = new ModelRenderer(this, 44, 8);
        this.pillar4.setRotationPoint(8.0F, 3.0F, -8.0F);
        this.pillar4.addBox(0.0F, -6.0F, 0.0F, 2, 6, 2, 0.0F);
        this.backdiag = new ModelRenderer(this, 46, 10);
        this.backdiag.setRotationPoint(-8.9F, -5.5F, 0.0F);
        this.backdiag.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
        this.setRotateAngle(backdiag, -0.7853981633974483F, 0.0F, 0.0F);
        this.back = new ModelRenderer(this, 0, 0);
        this.back.setRotationPoint(-9.0F, -3.0F, 0.0F);
        this.back.addBox(-8.0F, -3.0F, -1.0F, 16, 3, 2, 0.0F);
        this.setRotateAngle(back, 0.0F, 4.71238898038469F, 0.0F);
        this.frontinner = new ModelRenderer(this, 0, 22);
        this.frontinner.setRotationPoint(8.1F, 0.0F, 0.0F);
        this.frontinner.addBox(-5.0F, -3.0F, -1.0F, 10, 3, 1, 0.0F);
        this.setRotateAngle(frontinner, 0.0F, 4.71238898038469F, 0.0F);
        this.pillar2 = new ModelRenderer(this, 44, 0);
        this.pillar2.setRotationPoint(8.0F, 3.0F, 6.0F);
        this.pillar2.addBox(0.0F, -6.0F, 0.0F, 2, 6, 2, 0.0F);
        this.frontdiag = new ModelRenderer(this, 96, 18);
        this.frontdiag.setRotationPoint(7.9F, -5.5F, 0.0F);
        this.frontdiag.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
        this.setRotateAngle(frontdiag, -0.7853981633974483F, 0.0F, 0.0F);
        this.backinner = new ModelRenderer(this, 0, 23);
        this.backinner.setRotationPoint(-9.1F, 0.0F, 0.0F);
        this.backinner.addBox(-5.0F, -3.0F, -1.0F, 10, 3, 1, 0.0F);
        this.setRotateAngle(backinner, 0.0F, 4.71238898038469F, 0.0F);
        this.bottom = new ModelRenderer(this, 72, 0);
        this.bottom.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.bottom.addBox(-10.0F, -8.0F, -1.0F, 20, 16, 2, 0.0F);
        this.setRotateAngle(bottom, 1.5707963267948966F, 0.0F, 0.0F);
        this.side1 = new ModelRenderer(this, 72, 5);
        this.side1.setRotationPoint(0.0F, -1.0F, 4.0F);
        this.side1.addBox(-9.0F, -6.0F, -1.0F, 18, 11, 2, 0.0F);
        this.setRotateAngle(side1, 2.443460952792061F, 0.0F, 0.0F);
        this.front = new ModelRenderer(this, 0, 5);
        this.front.setRotationPoint(9.0F, -3.0F, 0.0F);
        this.front.addBox(-8.0F, -3.0F, -1.0F, 16, 3, 2, 0.0F);
        this.setRotateAngle(front, 0.0F, 1.5707963267948966F, 0.0F);
        this.fill = new ModelRenderer(this, 0, 27);
        this.fill.setRotationPoint(0.0F, -3.5F, 0.0F);
        this.fill.addBox(-8.0F, -6.0F, -1.0F, 16, 12, 2, 0.0F);
        this.setRotateAngle(fill, 1.5707963267948966F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.right.render(f5);
        this.pillar1.render(f5);
        this.side2.render(f5);
        this.pillar3.render(f5);
        this.left.render(f5);
        this.pillar4.render(f5);
        this.backdiag.render(f5);
        this.back.render(f5);
        this.frontinner.render(f5);
        this.pillar2.render(f5);
        this.frontdiag.render(f5);
        this.backinner.render(f5);
        this.bottom.render(f5);
        this.side1.render(f5);
        this.front.render(f5);
    }
    
    public void renderContents(float scale) {
    	this.fill.render(scale);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
