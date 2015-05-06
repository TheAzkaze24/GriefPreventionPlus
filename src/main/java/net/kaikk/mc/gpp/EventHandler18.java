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

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonRetractEvent;
//import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class EventHandler18 implements Listener {

	// dedo1911: Removed ARMOR_STAND check because there's no Armor Stand in 1.6 yet.
	// Also this removes the needing of PlayerInteractAtEntityEvent.

	//when a player interacts with a specific part of entity...
	/*@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event)
    {
        //treat it the same as interacting with an entity in general
        if(event.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            GriefPreventionPlus.instance.playerEventHandler.onPlayerInteractEntity((PlayerInteractEntityEvent)event);
        }
    }*/

	//blocks theft by pulling blocks out of a claim (again pistons)
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onBlockPistonRetract (BlockPistonRetractEvent event)
	{
		// dedo1911 - 1.8 compatibility.
		List<Block> eventBlocks;
		int pistonLength = 2; // TODO: This is NOT a fixed number. To see if we find a better way to get piston length.
		ArrayList tmpBlockList = new ArrayList();
		for(int i = 0; i < pistonLength; ++i) {
			tmpBlockList.add(event.getBlock().getRelative(event.getDirection(), i + 1));
		}
		eventBlocks = Collections.unmodifiableList(tmpBlockList);

		//pulling up is always safe
		if(event.getDirection() == BlockFace.UP) return;
		
		try
		{
    		//don't track in worlds where claims are not enabled
            if(!GriefPreventionPlus.instance.claimsEnabledForWorld(event.getBlock().getWorld())) return;
    		
    		//if pistons limited to only pulling blocks which are in the same claim the piston is in
    		if(GriefPreventionPlus.instance.config_pistonsInClaimsOnly)
    		{
    		    //if piston not in a land claim, cancel event
    		    Claim pistonClaim = GriefPreventionPlus.instance.dataStore.getClaimAt(event.getBlock().getLocation(), false, null);
    		    if(pistonClaim == null)
    		    {
    		        event.setCancelled(true);
    		        return;
    		    }

				for(Block movedBlock : eventBlocks/*event.getBlocks() /* dedo1911 */)
				{
    		        //if pulled block isn't in the same land claim, cancel the event
        		    if(!pistonClaim.contains(movedBlock.getLocation(), false, false))
        		    {
        		        event.setCancelled(true);
        		        return;
        		    }
    		    }
    		}
    		
    		//otherwise, consider ownership of both piston and block
    		else
    		{
    		    //who owns the piston, if anyone?
                String pistonOwnerName = "_";
                Location pistonLocation = event.getBlock().getLocation();       
                Claim pistonClaim = GriefPreventionPlus.instance.dataStore.getClaimAt(pistonLocation, false, null);
                if(pistonClaim != null) pistonOwnerName = pistonClaim.getOwnerName();
    		    
    		    String movingBlockOwnerName = "_";
        		for(Block movedBlock : eventBlocks/*event.getBlocks() /* dedo1911 */)
        		{
        		    //who owns the moving block, if anyone?
                    Claim movingBlockClaim = GriefPreventionPlus.instance.dataStore.getClaimAt(movedBlock.getLocation(), false, pistonClaim);
            		if(movingBlockClaim != null) movingBlockOwnerName = movingBlockClaim.getOwnerName();
            		
            		//if there are owners for the blocks, they must be the same player
            		//otherwise cancel the event
            		if(!pistonOwnerName.equals(movingBlockOwnerName))
            		{
            			event.setCancelled(true);
            		}
        		}
    		}
		}
		catch(NoSuchMethodError exception)
		{
		    GriefPreventionPlus.addLogEntry("Your server is running an outdated version of 1.8 which has a griefing vulnerability.  Update your server (reruns buildtools.jar to get an updated server JAR file) to ensure playres can't steal claimed blocks using pistons.");
		}
	}
}
