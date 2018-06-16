package cc.hyperium.mods.orangemarshall.enhancements.util;

public class Cooldown
{
    private final int COOLDOWN_IN_MS;
    private long lastUse;
    
    private Cooldown(final int milis) {
        this.COOLDOWN_IN_MS = milis;
    }
    
    public static Cooldown getNewCooldownSeconds(final int seconds) {
        return getNewCooldownMiliseconds(seconds * 1000);
    }
    
    public static Cooldown getNewCooldownMiliseconds(final int milis) {
        return new Cooldown(milis);
    }
    
    public boolean attemptReset() {
        if (System.currentTimeMillis() - this.lastUse > this.COOLDOWN_IN_MS) {
            this.lastUse = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
