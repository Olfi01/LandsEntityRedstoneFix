package de.lyriaserver.landsentityredstonefix;

import me.angeschossen.lands.api.flags.Flags;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Area;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.UUID;

public final class LandsEntityRedstoneFix extends JavaPlugin implements Listener {
    private LandsIntegration lands;
    private final UUID notchUid = UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5");
    private final Set<Material> checkBlocks = Set.of(
            Material.ACACIA_PRESSURE_PLATE,
            Material.BIRCH_PRESSURE_PLATE,
            Material.CRIMSON_PRESSURE_PLATE,
            Material.JUNGLE_PRESSURE_PLATE,
            Material.OAK_PRESSURE_PLATE,
            Material.MANGROVE_PRESSURE_PLATE,
            Material.SPRUCE_PRESSURE_PLATE,
            Material.STONE_PRESSURE_PLATE,
            Material.WARPED_PRESSURE_PLATE,
            Material.POLISHED_BLACKSTONE_PRESSURE_PLATE,
            Material.DARK_OAK_PRESSURE_PLATE,
            Material.HEAVY_WEIGHTED_PRESSURE_PLATE,
            Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Material.ACACIA_BUTTON,
            Material.BIRCH_BUTTON,
            Material.CRIMSON_BUTTON,
            Material.JUNGLE_BUTTON,
            Material.OAK_BUTTON,
            Material.MANGROVE_BUTTON,
            Material.SPRUCE_BUTTON,
            Material.WARPED_BUTTON,
            Material.DARK_OAK_BUTTON
    );
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        lands = new LandsIntegration(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityInteract(EntityInteractEvent event) {
        Block block = event.getBlock();
        if (!checkBlocks.contains(block.getType()) || event.getEntityType() == EntityType.PLAYER) return;
        Area area = lands.getAreaByLoc(block.getLocation());
        if (area != null && !area.hasFlag(notchUid, Flags.INTERACT_MECHANISM)) {
            event.setCancelled(true);
        }
    }
}
