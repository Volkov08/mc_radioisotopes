package net.volkov.radioisotopes.item.custom;

import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class ModShovelItem extends ShovelItem {
    public ModShovelItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
