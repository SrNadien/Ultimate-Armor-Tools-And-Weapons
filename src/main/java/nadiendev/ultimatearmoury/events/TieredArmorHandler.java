package nadiendev.ultimatearmoury.events;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.items.ModTier;
import nadiendev.ultimatearmoury.items.armor.TieredArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = UltimateArmouryCore.MOD_ID)
public class TieredArmorHandler {

    private static final int REFRESH_INTERVAL = 10;
    private static final int EFFECT_DURATION = 40;

    private static final ResourceLocation MUSHASHITE_HALF_HEART =
            ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "mushashite_half_heart");

    private static final ResourceLocation NADIENITE_FLIGHT =
            ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "nadienite_creative_flight");

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide() || player.tickCount % REFRESH_INTERVAL != 0) {
            return;
        }

        applyMushashite(player);
        applyJoanfoite(player);
        applyNadienite(player);
    }

    private static void applyMushashite(Player player) {
        if (TieredArmorItem.wearsPair(player, ModTier.MUSHASHITE,
                TieredArmorItem.HELMET, TieredArmorItem.CHESTPLATE)) {
            give(player, MobEffects.LUCK, 0);
        }

        toggleModifier(player.getAttribute(Attributes.MAX_HEALTH), MUSHASHITE_HALF_HEART,
                TieredArmorItem.wearsPair(player, ModTier.MUSHASHITE,
                        TieredArmorItem.LEGGINGS, TieredArmorItem.BOOTS));
    }

    private static void toggleModifier(AttributeInstance attribute, ResourceLocation id, boolean active) {
        if (attribute == null) {
            return;
        }
        boolean applied = attribute.getModifier(id) != null;
        if (active && !applied) {
            attribute.addTransientModifier(new AttributeModifier(id, 1.0D, AttributeModifier.Operation.ADD_VALUE));
        } else if (!active && applied) {
            attribute.removeModifier(id);
        }
    }

    private static void applyJoanfoite(Player player) {
        if (TieredArmorItem.wearsPair(player, ModTier.JOANFOITE,
                TieredArmorItem.HELMET, TieredArmorItem.CHESTPLATE)) {
            give(player, MobEffects.WATER_BREATHING, 0);
        }
        if (TieredArmorItem.wearsPair(player, ModTier.JOANFOITE,
                TieredArmorItem.LEGGINGS, TieredArmorItem.BOOTS)) {
            give(player, MobEffects.MOVEMENT_SPEED, 1);
        }
    }

    private static void applyNadienite(Player player) {
        if (TieredArmorItem.wears(player, ModTier.NADIENITE, TieredArmorItem.HELMET)) {
            give(player, MobEffects.NIGHT_VISION, 2);
        }

        boolean wearsChestplate = TieredArmorItem.wears(player, ModTier.NADIENITE, TieredArmorItem.CHESTPLATE);
        if (wearsChestplate) {
            give(player, MobEffects.DAMAGE_RESISTANCE, 2);
        }
        toggleModifier(player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT), NADIENITE_FLIGHT, wearsChestplate);

        if (TieredArmorItem.wears(player, ModTier.NADIENITE, TieredArmorItem.LEGGINGS)) {
            give(player, MobEffects.JUMP, 2);
        }
        if (TieredArmorItem.wears(player, ModTier.NADIENITE, TieredArmorItem.BOOTS)) {
            give(player, MobEffects.MOVEMENT_SPEED, 2);
        }
    }

    @SubscribeEvent
    public static void onChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity target = event.getNewAboutToBeSetTarget();
        if (!(target instanceof Player player)) {
            return;
        }

        LivingEntity attacker = event.getEntity();

        if (attacker instanceof Creeper && isProtected(player)) {
            event.setCanceled(true);
            return;
        }

        if (attacker instanceof Phantom && TieredArmorItem.wearsFullSet(player, ModTier.NADIENITE)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEndermanTeleport(EntityTeleportEvent.EnderEntity event) {
        if (!(event.getEntityLiving() instanceof EnderMan enderman)) {
            return;
        }
        if (enderman.getTarget() instanceof Player player
                && TieredArmorItem.wearsFullSet(player, ModTier.NADIENITE)) {
            event.setCanceled(true);
        }
    }

    private static boolean isProtected(Player player) {
        return TieredArmorItem.wearsFullSet(player, ModTier.JOANFOITE)
                || TieredArmorItem.wearsFullSet(player, ModTier.NADIENITE);
    }

    private static void give(Player player, Holder<MobEffect> effect, int amplifier) {
        player.addEffect(new MobEffectInstance(effect, EFFECT_DURATION, amplifier, false, false, true));
    }
}
