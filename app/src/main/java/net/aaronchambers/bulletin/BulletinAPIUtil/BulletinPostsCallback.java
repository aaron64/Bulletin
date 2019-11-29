package net.aaronchambers.bulletin.BulletinAPIUtil;

import org.json.JSONArray;
import org.json.JSONObject;

public interface BulletinPostsCallback {
    void onSuccess(JSONArray posts);
}
