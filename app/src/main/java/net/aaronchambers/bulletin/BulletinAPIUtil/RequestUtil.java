package net.aaronchambers.bulletin.BulletinAPIUtil;

import android.content.Context;

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
import org.json.JSONObject;


public class RequestUtil {

    public static void getBulletinName(Context context, final BulletinNameCallback callback) {
        String macAddress = NetworkUtil.getMacAddress(context);
        long macInt = parseMacAddress(macAddress);

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = context.getResources().getString(R.string.api_address) + context.getResources().getString(R.string.api_get_name) + macInt;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            callback.onSuccess(obj.getString("name"));
                        } catch (JSONException e) {
                            // TODO:
                            callback.onSuccess("Error parsing JSON");
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

    public static void post(Context context, String text, final BulletinPostCallback callback) {
        String macAddress = NetworkUtil.getMacAddress(context);
        long macInt = parseMacAddress(macAddress);

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = context.getResources().getString(R.string.api_address) + context.getResources().getString(R.string.api_post) + macInt + "/" + text;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess("success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: add error
                        callback.onSuccess("ERROR");
                    }
                });
        queue.add(stringRequest);
    }

    public static void getBulletinPosts(Context context, final BulletinPostsCallback callback) {
        String macAddress = NetworkUtil.getMacAddress(context);
        long macInt = parseMacAddress(macAddress);

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = context.getResources().getString(R.string.api_address) + context.getResources().getString(R.string.api_get_posts) + macInt;


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
