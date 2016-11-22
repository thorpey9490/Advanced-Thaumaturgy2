package net.ixios.advancedthaumaturgy.gui;

import net.ixios.advancedthaumaturgy.tileentities.TileWandbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWandbench extends Container
{
	private TileWandbench bench;

	public ContainerWandbench(EntityPlayer player, TileWandbench wandbench)
	{
		this.bench = wandbench;

		int left = 8, block = 18, yHotbar = 142, yPlayerInv = 84;
		// hotbar
		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(player.inventory, i, left + i * block, yHotbar));
		}
		// player inventory
		for (int row = 0; row < 3; row++)
		{
			for (int col = 0; col < 9; col++)
			{
				addSlotToContainer(new Slot(player.inventory, 9 + col + row * 9, left + col * block, yPlayerInv + row * block));
			}
		}
		
		// Output
		addSlotToContainer(new SlotWandOutput(bench, 0, 113, 31));
		
		// wand input
		addSlotToContainer(new Slot(bench, 1, 21, 31));

		// crystal upgrade input
		addSlotToContainer(new Slot(bench, 2, 42, 13));
		// rod input
		addSlotToContainer(new Slot(bench, 3, 42, 31));
		// caps input
		addSlotToContainer(new Slot(bench, 4, 42, 49));
		

		// vis supply wand slot
		addSlotToContainer(new Slot(bench, 5, 150, 31));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return bench.isUseableByPlayer(entityplayer);
	}

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p, int i)
    {
    	return null;
    }
	
}