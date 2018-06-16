package cc.hyperium.mods.orangemarshall.enhancements.util.http;

import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import com.google.gson.*;
import java.util.*;

public class UuidRequest extends HttpsRequest
{
    protected static final String REQUEST_UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%name";
    
    public UuidRequest(final String name) {
        super("https://api.mojang.com/users/profiles/minecraft/%name".replace("%name", name));
    }
    
    public Username requestUser() {
        final String response = this.request();
        return this.parseResponse(response);
    }
    
    private Username parseResponse(final String response) {
        final JsonObject obj = new JsonParser().parse(response).getAsJsonObject();
        if (obj == null) {
            throw new IllegalStateException("No name in response: " + response);
        }
        final String id = obj.get("id").getAsString();
        final UUID uuid = StringUtil.unstripUuidAsUuid(id);
        final String name = obj.get("name").getAsString();
        return new Username(uuid, name);
    }
}
