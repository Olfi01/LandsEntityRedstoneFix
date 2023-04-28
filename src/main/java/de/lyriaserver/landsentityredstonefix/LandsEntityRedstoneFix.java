package de.lyriaserver.landsentityredstonefix;

import me.angeschossen.lands.api.flags.types.LandFlag;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Area;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public final class LandsEntityRedstoneFix extends JavaPlugin implements Listener {
    private LandsIntegration lands;
    private LandFlag entitiesCanActivateMechanisms;
    private final ItemStack flagIcon = new ItemStack(Material.ARROW);
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
    public void onLoad() {
        lands = new LandsIntegration(this);
        entitiesCanActivateMechanisms = new LandFlag(this, "entity_mechanisms");
        entitiesCanActivateMechanisms.setDefaultState(false);
        entitiesCanActivateMechanisms.setDescription(List.of(
                "Entities wie Items oder Pfeile können Mechanismen auslösen",
                "Zielscheiben sind ausgenommen!"));
        entitiesCanActivateMechanisms.setDisplayName("Entity-Mechanismen");
        entitiesCanActivateMechanisms.setIcon(flagIcon);
        lands.registerFlag(entitiesCanActivateMechanisms);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
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
        if (area != null && !area.hasFlag(entitiesCanActivateMechanisms)) {
            event.setCancelled(true);
        }
    }
}
