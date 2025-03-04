package bta.aether.world;

import bta.aether.Aether;
import bta.aether.world.generate.chunk.perlin.aether.ChunkGeneratorAether;
import net.minecraft.core.Global;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.biome.provider.BiomeProviderSingleBiome;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManager;

public abstract class WorldTypeAether
    extends WorldType
{
    public WorldTypeAether(String languageKey, Weather defaultWeather, WindManager windManager, SeasonConfig defaultSeasonConfig) {
        super(
            languageKey,
            defaultWeather,
            windManager,
            false,
            createLightRamp(),
            defaultSeasonConfig
        );
    }

    private static float[] createLightRamp()
    {
        float[] brightnessRamp = new float[32];
        float f = 0.05F;
        for(int i = 0; i <= 31; i++)
        {
            float f1 = 1.0F - (float)i / 15F;
            if (i > 15) f1 = 0.0f;
            brightnessRamp[i] = ((1.0F - f1) / (f1 * 3F + 1.0F)) * (1.0F - f) + f;
        }
        return brightnessRamp;
    }

    @Override
    public int getOceanY() {
        return 0;
    }

    @Override
    public int getOceanBlock() {
        return 0;
    }

    @Override
    public int getFillerBlock() {
        return Aether.holystone.id;
    }

    @Override
    public BiomeProvider createBiomeProvider(World world) {
        return new BiomeProviderSingleBiome(Aether.biomeAether, 0.5, 0.5, 0.5);
    }

    @Override
    public ChunkGenerator createChunkGenerator(World world) {
        return new ChunkGeneratorAether(world);
    }

    @Override
    public boolean isValidSpawn(World world, int i, int j, int k) {
        return true;
    }

    @Override
    public int getDayNightCycleLengthTicks() {
        return Global.DAY_LENGTH_TICKS;
    }

    @Override
    public float getCelestialAngle(World world, long l, float f) {
        return 0f;
    }

    @Override
    public float[] getSunriseColor(float timeOfDay, float partialTick) {
        float[] sunriseCol = new float[4];
        float f2 = 0.4F;
        float f3 = MathHelper.cos(timeOfDay * 3.141593F * 2.0F) - 0.0F;
        float f4 = -0F;
        if(f3 >= f4 - f2 && f3 <= f4 + f2)
        {
            float c = ((f3 - f4) / f2) * 0.5F + 0.5F;
            float a = 1.0F - (1.0F - MathHelper.sin(c * 3.141593F)) * 0.99F;
            a *= a;
            sunriseCol[0] = c * 0.3F + 0.7F;
            sunriseCol[1] = c * c * 0.7F + 0.2F;
            sunriseCol[2] = c * c * 0.0F + 0.2F;
            sunriseCol[3] = a;
            return sunriseCol;
        }
        else
        {
            return null;
        }
    }

    @Override
    public int getSkyDarken(World world, long l, float f) {
        return 0;
    }

    @Override
    public Vec3d getFogColor(float f, float g) {
        int i = 0x8080a0;
        float f2 = MathHelper.cos(f * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if(f2 < 0.0F)
        {
            f2 = 0.0F;
        }
        if(f2 > 1.0F)
        {
            f2 = 1.0F;
        }
        float f3 = (float)(i >> 16 & 0xff) / 255F;
        float f4 = (float)(i >> 8 & 0xff) / 255F;
        float f5 = (float)(i & 0xff) / 255F;
        f3 *= f2 * 0.94F + 0.06F;
        f4 *= f2 * 0.94F + 0.06F;
        f5 *= f2 * 0.91F + 0.09F;
        return Vec3d.createVector(f3, f4, f5);
    }

    @Override
    public boolean mayRespawn() {
        return false;
    }

    @Override
    public float getCloudHeight() {
        return 8f;
    }

    @Override
    public boolean hasGround() {
        return false;
    }
}
