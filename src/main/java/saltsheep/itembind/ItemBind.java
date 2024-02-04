package saltsheep.itembind;

import org.apache.logging.log4j.Logger;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ItemBind.MODID, name = ItemBind.NAME, version = ItemBind.VERSION, useMetadata = true, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.12.2]")
public class ItemBind
{
    public static final String MODID = "itembind";
    public static final String NAME = "ItemBind";
    public static final String VERSION = "1.0";
    public static ItemBind instance;

    private static Logger logger;

    public ItemBind() {
    	instance = this;
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
    }
    
    @EventHandler
    public static void onServerStarting(FMLServerStartingEvent event){
	}
    
    public static Logger getLogger() {
    	return logger;
    }
    
    public static MinecraftServer getMCServer() {
    	return FMLCommonHandler.instance().getMinecraftServerInstance();
    }
    
    public static void printError(Throwable error) {
    	String messages = "";
    	for(StackTraceElement stackTrace : error.getStackTrace()) {
    		messages = messages+stackTrace.toString()+"\n";
		}
    	ItemBind.getLogger().error("警告！在咸羊我的mod里出现了一些错误，信息如下：\n"+messages+"出现错误类型:"+error.getClass());
    }
    
    public static void info(String str) {
    	logger.info(str);
    }
    
    public static void info(Object obj) {
    	if(obj == null)
    		logger.info("null has such obj.");
    	else
    		logger.info(obj.toString());
    }
    
    public static void info(Object[] objs) {
    	if(objs == null)
    		logger.info("null has such objs.");
    	else {
    		StringBuilder builder = new StringBuilder();
    		for(Object obj : objs)
    			builder.append(obj.toString());
    		logger.info(builder.toString());
    	}
    }
    
}
