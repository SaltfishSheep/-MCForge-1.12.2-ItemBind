package saltsheep.itembind;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import saltsheep.util.ReflectHelper;

@EventBusSubscriber
public class EventHandler {

	@SubscribeEvent
	public static void onThrowItem(ItemTossEvent event) {
		if(event.getPlayer()==null||!event.getPlayer().isServerWorld())
			return;
		if(event.getPlayer().isCreative())
			return;
		ItemStack throwItem = event.getEntityItem().getItem();
		if(isBind(throwItem)) {
			boolean flag = event.getPlayer().inventory.addItemStackToInventory(throwItem);
			if(!flag){
				EntityItem entityItem = event.getPlayer().entityDropItem(throwItem, 1.3f);	    		
				entityItem.setOwner(event.getPlayer().getName());
				entityItem.setNoPickupDelay();
				entityItem.setEntityInvulnerable(true);		
			}
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void onContainer(PlayerTickEvent event) {
		if(event.player==null||event.side==Side.CLIENT||event.player.isCreative()||event.player.openContainer==null)
			return;
		List<Slot> slots = event.player.openContainer.inventorySlots;
		for(Slot slot : slots) {
			//*对于物品位于玩家背包的处理
			if(hasItemStackRigid(event.player.inventory, slot.getStack()))
				continue;
			if(isBind(slot.getStack())) {
				boolean flag = event.player.addItemStackToInventory(slot.getStack());
				if(!flag){
					EntityItem entityItem = event.player.entityDropItem(slot.getStack(), 1.3f);	    		
					entityItem.setOwner(event.player.getName());
					entityItem.setNoPickupDelay();
					entityItem.setEntityInvulnerable(true);		
				}
				slot.putStack(ItemStack.EMPTY);
			}
		}
	}
	
	public static boolean isBind(ItemStack item) {
		if(item == null||item.isEmpty()||!item.hasTagCompound()||!item.getTagCompound().hasKey("display")||!item.getTagCompound().getCompoundTag("display").hasKey("Lore"))
			return false;
		NBTTagList lores = item.getTagCompound().getCompoundTag("display").getTagList("Lore", 8);
		boolean isBind = false;
		for(int i=0;i<lores.tagCount();i++)
			if(lores.getStringTagAt(i).contains("绑定")) {
				isBind = true;
				break;
			}
		return isBind;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean hasItemStackRigid(InventoryPlayer inventory,ItemStack itemStackIn){
		 first:for (List<ItemStack> list : (List<List<ItemStack>>)ReflectHelper.getMCFieldByClass(InventoryPlayer.class, inventory, "allInventories", "field_184440_g")){
			 Iterator<ItemStack> iterator = list.iterator();
			 while (true){
				 if (!iterator.hasNext()){
					 continue first;
				 }
				 ItemStack itemstack = (ItemStack)iterator.next();
				 if (itemstack!=null && !itemstack.isEmpty() && itemstack==itemStackIn)
					 break;
			 }
			 return true;
		 }
	 return false;
	 }
	
}
