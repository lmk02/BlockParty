package de.pauhull.utils.particle;


import de.pauhull.utils.misc.MinecraftVersion;

import static de.pauhull.utils.misc.MinecraftVersion.v1_13;
import static de.pauhull.utils.misc.MinecraftVersion.v1_14;

public enum Particles implements IParticles{

    EXPLOSION_NORMAL{
        @Override
        public Object get() {
            if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_13) && MinecraftVersion.CURRENT_VERSION.isLower(v1_14)) {
                return de.pauhull.utils.particle.v1_13.Particles.EXPLOSION_NORMAL.get();
            } else  if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_14)) {
                return de.pauhull.utils.particle.v1_14.Particles.EXPLOSION_NORMAL.get();
            }
            return null;
        }
    },
    CRIT{
        @Override
        public Object get() {
            if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_13) && MinecraftVersion.CURRENT_VERSION.isLower(v1_14)) {
                return de.pauhull.utils.particle.v1_13.Particles.CRIT.get();
            } else  if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_14)) {
                return de.pauhull.utils.particle.v1_14.Particles.CRIT.get();
            }
            return null;
        }
    },
    SPELL{
        @Override
        public Object get() {
            if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_13) && MinecraftVersion.CURRENT_VERSION.isLower(v1_14)) {
                return de.pauhull.utils.particle.v1_13.Particles.SPELL.get();
            } else  if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_14)) {
                return de.pauhull.utils.particle.v1_14.Particles.SPELL.get();
            }
            return null;
        }
    },
    CLOUD{
        @Override
        public Object get() {
            if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_13) && MinecraftVersion.CURRENT_VERSION.isLower(v1_14)) {
                return de.pauhull.utils.particle.v1_13.Particles.CLOUD.get();
            } else if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_14)) {
                return de.pauhull.utils.particle.v1_14.Particles.CLOUD.get();
            }
            return null;
        }
    }; //TODO: Add more

}
