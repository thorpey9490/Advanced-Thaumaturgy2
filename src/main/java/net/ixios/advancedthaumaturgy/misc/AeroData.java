package net.ixios.advancedthaumaturgy.misc;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

public class AeroData
{
	public EntityPlayer player = null;
	public Vector3 vector = null;
	public Block blockid = null;
	public int blockmeta = 0;
	
	public AeroData(EntityPlayer plr, Vector3 vec, Block id, int meta)
	{
		this.player = plr;
		this.vector = vec;
		this.blockid = id;
		this.blockmeta = meta;
	}
}
