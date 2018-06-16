package cc.hyperium.mods.orangemarshall.enhancements.util.http;

import java.util.*;

public class Username
{
    private long creationDate;
    private UUID uuid;
    private String name;
    
    public Username(final UUID uuid, final String name) {
        this.creationDate = new Date().getTime();
        this.uuid = uuid;
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public long getCreationDate() {
        return this.creationDate;
    }
}
