/*
    GriefPreventionPlus Server Plugin for Minecraft
    Copyright (C) 2015 Antonino Kai Pocorobba
    (forked from GriefPrevention by Ryan Hamshire)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.kaikk.mc.gpp;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class MC18Helper {

    // dedo1911: Those functions here should ALWAYS return FALSE. Because we'll never be on 1.8 with this.

	static boolean isArmorStatue(Entity entity) {
		return false;
		//return (entity.getType() == EntityType.ARMOR_STAND);
	}
	
	static boolean isArmorStatue(EntityType entity) {
        return false;
        //return (entity == EntityType.ARMOR_STAND);
	}
	
	static boolean isArmorStatue(Material material) {
        return false;
        //return (material == Material.ARMOR_STAND);
	}
	
	static boolean isDoor(Material material) {
        return false;
        //return (material == Material.ACACIA_DOOR   ||
       //material == Material.BIRCH_DOOR    ||
       //material == Material.JUNGLE_DOOR   ||
       //material == Material.SPRUCE_DOOR   ||
       //material == Material.DARK_OAK_DOOR);
	}
	
	static boolean isFence(Material material) {
        return false;
        //return (material == Material.ACACIA_FENCE_GATE   ||
        //       material == Material.BIRCH_FENCE_GATE    ||
        //       material == Material.JUNGLE_FENCE_GATE   ||
        //       material == Material.SPRUCE_FENCE_GATE   ||
        //       material == Material.DARK_OAK_FENCE_GATE);
	}
	
	static boolean isInvDS(Material material) {
        return false;
        //return material == Material.DAYLIGHT_DETECTOR_INVERTED;
	}
}
