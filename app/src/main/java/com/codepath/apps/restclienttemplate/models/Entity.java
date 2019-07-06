package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Entity {
    public String loadURL;

    public static Entity fromJSON(JSONObject js)throws JSONException {
        Entity ent = new Entity();
        ent.loadURL = js.getJSONArray("media").getJSONObject(0).getString("media_url_https");
        return ent;
    }
}
