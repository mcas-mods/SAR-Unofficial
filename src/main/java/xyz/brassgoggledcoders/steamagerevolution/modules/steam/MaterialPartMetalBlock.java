package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import org.apache.commons.lang3.StringUtils;

import com.teamacronymcoders.base.materialsystem.MaterialUser;
import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.materialsystem.parts.Part;

public class MaterialPartMetalBlock extends MaterialPart {

	public MaterialPartMetalBlock(MaterialUser materialUser, Material material, Part part) {
		super(materialUser, material, part);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getOreDictString() {
		return "block" + StringUtils.capitalize(this.getMaterial().getOreDictSuffix());
	}
}
