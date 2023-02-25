package net.mehvahdjukaar.sleep_tight.configs;

import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;
import net.mehvahdjukaar.sleep_tight.SleepTight;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.Registry;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.alchemy.Potion;

import java.util.List;
import java.util.function.Supplier;

public class CommonConfigs {
    public static final Supplier<Integer> SLEEP_INTERVAL;

    public static final Supplier<Boolean> FIX_BED_POSITION;
    public static final Supplier<Integer> HOME_BED_REQUIRED_NIGHTS;

    public static final Supplier<Integer> NIGHTMARES_CONSECUTIVE_NIGHTS;
    public static final Supplier<Double> NIGHTMARE_CHANCE_INCREMENT_PER_NIGHT;
    public static final Supplier<Double> NIGHTMARE_SLEEP_TIME_MULTIPLIER;
    public static final Supplier<Integer> NIGHTMARE_INSOMNIA_DURATION;

    public static final Supplier<SimpleWeightedRandomList<EntityType<?>>> ENCOUNTER_WHITELIST;
    public static final Supplier<Integer> ENCOUNTER_RADIUS;
    public static final Supplier<Integer> ENCOUNTER_MIN_RADIUS;
    public static final Supplier<Integer> ENCOUNTER_HEIGHT;
    public static final Supplier<Integer> ENCOUNTER_TRIES;
    public static final Supplier<Integer> ENCOUNTER_MAX_COUNT;
    public static final Supplier<Double> ENCOUNTER_SLEEP_TIME_MULTIPLIER;
    public static final Supplier<Integer> ENCOUNTER_INSOMNIA_DURATION;

    public static final Supplier<Integer> BED_COOLDOWN;
    public static final Supplier<Integer> HAMMOCK_COOLDOWN;

    public static final Supplier<BedStatus> BED_BENEFITS;
    public static final Supplier<EffectIntensity> HEALING;
    public static final Supplier<EffectIntensity> EFFECT_CLEARING;
    public static final Supplier<PotionClearing> EFFECT_CLEARING_TYPE;
    public static final Supplier<List<MobEffect>> WAKE_UP_EFFECTS;

    public static final Supplier<Boolean> REQUIREMENT_BED;
    public static final Supplier<Boolean> REQUIREMENT_HAMMOCK;
    public static final Supplier<Boolean> REQUIREMENT_NIGHT_BAG;
    public static final Supplier<Boolean> NEED_FULL_HUNGER;
    public static final Supplier<Integer> XP_COST;

    public static final Supplier<Boolean> PENALTIES_BED;
    public static final Supplier<Boolean> PENALTIES_HAMMOCK;
    public static final Supplier<Boolean> PENALTIES_NIGHT_BAG;
    public static final Supplier<HungerMode> CONSUME_HUNGER_MODE;
    public static final Supplier<Double> CONSUMED_HUNGER;

    public enum EffectIntensity {
        NONE, TIME_BASED, MAX
    }

    public enum PotionClearing {
        ALL, BENEFICIAL, HARMFUL
    }

    public enum BedStatus {
        NONE, ALWAYS, HOME_BED
    }
    public enum HungerMode {
        CONSTANT, TIME_BASED, DIFFICULTY_BASED, TIME_DIFFICULTY_BASED
    }


    static {
        ConfigBuilder builder = ConfigBuilder.create(SleepTight.MOD_ID, ConfigType.COMMON);

        builder.push("hammock");
        FIX_BED_POSITION = builder.comment("Fixes multiplayer players being positioned 2 pixels above a bed")
                .define("fix_bed_position", true);
        HAMMOCK_COOLDOWN = builder.comment("Time before you can sleep/rest again after you've slept in a hammock")
                .define("sleep_cooldown", 6000, 0, 1000000);
        builder.pop();



        builder.push("sleep_benefits");
        BED_BENEFITS = builder.comment("Which type of beds will apply benefits on wake up")
                        .define("active_for", BedStatus.HOME_BED);
        HEALING = builder.comment("Healing applied on wake up")
                        .define("healing", EffectIntensity.MAX);
        EFFECT_CLEARING = builder.comment("")
                .define("effect_clearing", EffectIntensity.MAX);
        EFFECT_CLEARING_TYPE = builder.comment("")
                .define("effect_clearing_types", PotionClearing.ALL);
        WAKE_UP_EFFECTS = builder.comment("Effects to apply when player wakes up")
                        .defineObject("wake_up_effects",()-> List.of(MobEffects.MOVEMENT_SPEED),
                                Registry.MOB_EFFECT.byNameCodec().listOf());

        builder.pop();

        builder.push("sleep_penalties");
        PENALTIES_BED = builder.define("apply_to_beds", true);
        PENALTIES_HAMMOCK = builder.define("apply_to_hammock", true);
        PENALTIES_NIGHT_BAG = builder.define("apply_to_night_bags", true);
        CONSUME_HUNGER_MODE = builder.comment("Method to calculate hunger loss. Can be based off time slept, difficulty or constant")
                        .define("consumed_hunger_mode", HungerMode.CONSTANT);
        CONSUMED_HUNGER = builder.comment("Base hunger decrement value. Depends on other config. Set to 0 to disable")
                        .define("base_value", 20, 0f, 40);
        builder.pop();

        builder.push("sleep_requirements");
        REQUIREMENT_BED = builder.define("apply_to_beds", true);
        REQUIREMENT_HAMMOCK = builder.define("apply_to_hammock", true);
        REQUIREMENT_NIGHT_BAG = builder.define("apply_to_night_bags", true);

        NEED_FULL_HUNGER = builder.comment("Requires player to have full hunger bar before being able to sleep")
                        .define("require_full_hunger", false);
        XP_COST = builder.comment("Xp cost for sleeping. Does not affect peaceful")
                        .define("xp_cost", 0, 0, 200);
        builder.pop();

        builder.push("bed");

        builder.push("home_bed");
        SLEEP_INTERVAL = builder.comment("Interval between two consecutive sleep times for them to not be considered consecutive")
                .define("sleep_interval", 24000, 0, 1000000);
        HOME_BED_REQUIRED_NIGHTS = builder.comment("Amount of nights needed to mark a bed as home bed")
                .define("home_bed_required_nights", 8, 1, 50);

        BED_COOLDOWN = builder.comment("Time before you can sleep/rest again after you've successfully slept in a bed")
                .define("sleep_cooldown", 6000, 0, 1000000);


        builder.pop();

        builder.push("nightmares");
        NIGHTMARES_CONSECUTIVE_NIGHTS = builder.comment("Amount of consecutive nights from which nightmares could start to happen")
                .define("minimum_nights_cutoff", 4, 0, 100);
        NIGHTMARE_CHANCE_INCREMENT_PER_NIGHT = builder.define("nightmare_increment_per_night", 0.15, 0, 1);
        NIGHTMARE_SLEEP_TIME_MULTIPLIER = builder.comment("Multiplier applied to time slept after a nightmare")
                .define("sleep_time_multiplier", 0.5, 0.01, 1);
        NIGHTMARE_INSOMNIA_DURATION = builder.comment("Refractory period after a nightmare in which you won't be able to sleep")
                .define("insomnia_duration", 24000 + 12000, 0, 1000000);

        builder.pop();

        builder.push("wake_up_encounters");

        ENCOUNTER_TRIES = builder.comment("The game will perform x attempts to spawn a mod around each player every time they sleep." +
                        "Increases likelihood of finding one. Note that actual value will also depend on local difficulty")
                .define("tries", 25, 0, 1000);
        ENCOUNTER_MAX_COUNT = builder.comment("Max amount of mobs per encounter")
                .define("max_count", 1, 0, 20);
        ENCOUNTER_RADIUS = builder.define("spawn_radius", 10, 1, 32);
        ENCOUNTER_MIN_RADIUS = builder.define("min_radius", 2, 0, 32);
        ENCOUNTER_HEIGHT = builder.define("height", 3, 1, 10);
        ENCOUNTER_WHITELIST = builder.comment("""
                        Mobs that can randomly wake up the player if sleeping in a dark place. Leave empty to use default spawning behavior. Add a weighted list in the following format (replace line bellow):
                        [[bed.wake_up_encounters.alternative_whitelist]]
                        \t\t\tdata = "minecraft:vindicator"
                        \t\t\tweight = 2
                        [[bed.wake_up_encounters.alternative_whitelist]]
                        \t\t\tdata = "minecraft:creeper"
                        \t\t\tweight = 2
                        """)
                .defineObject("alternative_whitelist", () -> SimpleWeightedRandomList.<EntityType<?>>builder().build(),
                        SimpleWeightedRandomList.wrappedCodecAllowingEmpty(Registry.ENTITY_TYPE.byNameCodec()));
        ENCOUNTER_SLEEP_TIME_MULTIPLIER = builder.comment("Multiplier applied to time slept after an encounter")
                .define("sleep_time_multiplier", 0.5, 0.01, 1);
        ENCOUNTER_INSOMNIA_DURATION = builder.comment("Refractory period after an encounter in which you won't be able to sleep")
                .define("insomnia_duration", 12000, 0, 1000000);

        builder.pop();

        builder.pop();

        builder.setSynced();
        builder.buildAndRegister();
    }

    public static void init() {
    }
}