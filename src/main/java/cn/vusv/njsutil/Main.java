package cn.vusv.njsutil;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;

public class Main extends PluginBase implements Listener {
    PluginLogger log;

    @Override
    public void onLoad() {
        log = new PluginLogger(this);
        log.info("年JS工具包加载中");
    }

    @Override
    public void onEnable() {
        log.info("年JS工具包加载成功!");
        this.getServer().getPluginManager().registerEvents(new Main(), this);
    }

    @Override
    public void onDisable() {

    }

//    @EventHandler(priority = EventPriority.NORMAL)
//    public void onBlockPlace(BlockPlaceEvent event) {
//    }
//    @EventHandler(priority = EventPriority.NORMAL)
//   public void onBlockBreak(BlockBreakEvent event) {
//    }

}
