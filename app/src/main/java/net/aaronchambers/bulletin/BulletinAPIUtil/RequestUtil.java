package net.aaronchambers.bulletin.BulletinAPIUtil;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.aaronchambers.bulletin.NetworkUtil;
import net.aaronchambers.bulletin.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


public class RequestUtil {

    public static void getPosts(Context context, final BulletinPostsCallback callback) {
        String macAddress = NetworkUtil.getMacAddress(context);

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = context.getResources().getString(R.string.api_address) + context.getResources().getString(R.string.api_get_messages) + "FF:FF:FF:FF";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            callback.onSuccess(new JSONArray(response));
                        } catch (JSONException e) {
                            // TODO:
                            callback.onSuccess(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: add error
                        callback.onSuccess(null);
                    }
                });
        queue.add(stringRequest);
    }

    private static long parseMacAddress(String macString) {
        macString = macString.replace(":", "");
        return Long.decode("0x" + macString);
    }
}
