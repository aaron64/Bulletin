package net.aaronchambers.bulletin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BoardRecyclerViewAdapter extends RecyclerView.Adapter<BoardRecyclerViewAdapter.MyViewHolder> {
        private ArrayList<BulletinPost> posts = new ArrayList<BulletinPost>();

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView bulletinRowTextPost, bulletinRowDate;
            public MyViewHolder(View v) {
                super(v);
                bulletinRowTextPost = v.findViewById(R.id.bulletin_row_text_post);
                bulletinRowDate = v.findViewById(R.id.bulletin_row_text_time);
            }
        }

        public BoardRecyclerViewAdapter(JSONArray data) {
            try {
                for (int i = 0; i < data.length(); i++) {
                    JSONObject item = data.getJSONObject(i);
                    BulletinPost p = new BulletinPost(item.getString("text"), item.getString("updated"));
                    posts.add(p);
                }
            } catch(JSONException e) {

            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bulletin_row, parent, false);

            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int pos) {
            holder.bulletinRowTextPost.setText(posts.get(pos).getName());
            holder.bulletinRowDate.setText(posts.get(pos).getDate());
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }

        public void addPost(BulletinPost p) {
            posts.add(p);
            notifyDataSetChanged();
        }
}
