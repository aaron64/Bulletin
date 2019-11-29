package net.aaronchambers.bulletin;

import org.json.JSONArray;
import org.json.JSONObject;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import net.aaronchambers.bulletin.BulletinAPIUtil.BulletinNameCallback;
import net.aaronchambers.bulletin.BulletinAPIUtil.BulletinPostCallback;
import net.aaronchambers.bulletin.BulletinAPIUtil.BulletinPostsCallback;
import net.aaronchambers.bulletin.BulletinAPIUtil.RequestUtil;

public class BulletinBoardActivity extends AppCompatActivity {

    ImageButton boardBtnSendPost;
    EditText boardEditPostText;

    BoardRecyclerViewAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);

        final TextView boardTextHeader = findViewById(R.id.board_text_header);
        boardBtnSendPost = findViewById(R.id.board_btn_send_post);
        boardEditPostText = findViewById(R.id.board_edit_post);

        RequestUtil.getBulletinName(this, new BulletinNameCallback() {
            @Override
            public void onSuccess(String result) {
                boardTextHeader.setText(result);
            }
        });

        RequestUtil.getBulletinPosts(this, new BulletinPostsCallback() {
            @Override
            public void onSuccess(JSONArray posts) {
                createRecyclerView(posts);
            }
        });

        boardBtnSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                RequestUtil.post(v.getContext(), boardEditPostText.getText().toString(), new BulletinPostCallback() {
                    @Override
                    public void onSuccess(String result) {
                        String text = boardEditPostText.getText().toString();
                        adapter.addPost(new BulletinPost(text, "Thu, 29 Nov 2018 06:04:49 GMT"));

                        boardEditPostText.getText().clear();

                        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                });
            }
        });
    }

    public void createRecyclerView(JSONArray data) {
        RecyclerView boardRecycleView = findViewById(R.id.board_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        boardRecycleView.setHasFixedSize(true);

        // use a linear layout manager

        // specify an adapter (see also next example)
        adapter = new BoardRecyclerViewAdapter(data);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        boardRecycleView.setLayoutManager(layoutManager);
        boardRecycleView.setItemAnimator(new DefaultItemAnimator());
        boardRecycleView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        boardRecycleView.setAdapter(adapter);
    }
}
