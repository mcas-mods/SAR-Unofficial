package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class LootFunctionOredict extends LootFunction
{
    private final String name;

    public LootFunctionOredict(LootCondition[] conditionsIn, String oredictNameIn)
    {
        super(conditionsIn);
        this.name = oredictNameIn;
    }

    public ItemStack apply(ItemStack stack, Random rand, LootContext context)
    {
    	//We don't care about the stack
        return OreDictUtils.getPreferredItemStack(name);
    }

    public static class Serializer extends LootFunction.Serializer<LootFunctionOredict>
        {
            public Serializer()
            {
                super(new ResourceLocation(SteamAgeRevolution.MODID, "oredict"), LootFunctionOredict.class);
            }

            public void serialize(JsonObject object, LootFunctionOredict functionClazz, JsonSerializationContext serializationContext)
            {
                object.addProperty("oredict", functionClazz.name);
            }

            public LootFunctionOredict deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
            {
            	return new LootFunctionOredict(conditionsIn, object.get("oredict").getAsString());
            }
        }
}