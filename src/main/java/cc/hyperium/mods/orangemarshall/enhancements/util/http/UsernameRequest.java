package cc.hyperium.mods.orangemarshall.enhancements.util.http;

import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import com.google.common.collect.*;
import com.google.gson.*;

public class UsernameRequest extends HttpsRequest
{
    protected static final String REQUEST_USERNAME_URL = "https://api.mojang.com/user/profiles/%uuid/names";
    
    public UsernameRequest(final UUID uuid) {
        super("https://api.mojang.com/user/profiles/%uuid/names".replace("%uuid", StringUtil.stripUUID(uuid)));
    }
    
    public List<Pair<Long, String>> requestNames() {
        final String response = this.request();
        return this.parseResponse(response);
    }
    
    public Pair<Long, String> requestCurrentName() {
        final List<Pair<Long, String>> names = this.requestNames();
        return names.get(names.size() - 1);
    }
    
    public Pair<Long, String> requestFirstName() {
        final List<Pair<Long, String>> names = this.requestNames();
        return names.get(0);
    }
    
    private List<Pair<Long, String>> parseResponse(final String response) {
        final JsonArray array = new JsonParser().parse(response).getAsJsonArray();
        if (array.size() == 0) {
            throw new IllegalStateException("No name in response: " + response);
        }
        final List<Pair<Long, String>> names = (List<Pair<Long, String>>)Lists.newArrayListWithCapacity(array.size());
        for (int i = 0; i < array.size(); ++i) {
            final JsonObject obj = array.get(i).getAsJsonObject();
            final String name = obj.get("name").getAsString();
            final long timestamp = (i == 0) ? 0L : obj.get("changedToAt").getAsLong();
            names.add(new Pair<Long, String>(timestamp, name));
        }
        return names;
    }
}
