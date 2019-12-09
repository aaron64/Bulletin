package net.aaronchambers.bulletin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import net.aaronchambers.bulletin.BulletinAPIUtil.BulletinPostsCallback;
import net.aaronchambers.bulletin.BulletinAPIUtil.RequestUtil;

import java.net.URISyntaxException;


public class BulletinBoardActivity extends AppCompatActivity {

    ImageButton boardBtnSendPost;
    EditText boardEditPostText;

    BoardRecyclerViewAdapter adapter = null;

    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);

        RequestUtil.getPosts(this, new BulletinPostsCallback() {
            @Override
            public void onSuccess(JSONArray posts) {
                createRecyclerView(posts);
            }
        });

        try {
            socket = IO.socket(getResources().getString(R.string.socket_api_address));
        } catch(URISyntaxException e) {}
        socket.connect();
        socket.on("FF:FF:FF:FF", onNewMessage);

        final TextView boardTextHeader = findViewById(R.id.board_text_header);
        boardBtnSendPost = findViewById(R.id.board_btn_send_post);
        boardEditPostText = findViewById(R.id.board_edit_post);

        boardBtnSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String message = boardEditPostText.getText().toString().trim();
        JSONObject messageObject = new JSONObject();
        try {
            messageObject.put("message", message);
            messageObject.put("board", "FF:FF:FF:FF");
                    //NetworkUtil.getMacAddress(getApplicationContext()));
        } catch (JSONException e) {

        }

        if (TextUtils.isEmpty(message)) {
            return;
        }

        socket.emit("message", messageObject);
        boardEditPostText.setText("");
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("ASDF", "a");
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    String date;
                    try {
                        message = data.getString("message");
                        date = data.getString("date");
                    } catch (JSONException e) {
                        return;
                    }
                    Log.i("ASDF", date);
                    Log.i("ASDF", message);
                    adapter.addPost(new BulletinPost(message, date));
                }
            });
        }
    };

    public void createRecyclerView(JSONArray data) {
        RecyclerView boardRecycleView = findViewById(R.id.board_recycler_view);
        boardRecycleView.setHasFixedSize(true);

        adapter = new BoardRecyclerViewAdapter(data);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        boardRecycleView.setLayoutManager(layoutManager);
        boardRecycleView.setItemAnimator(new DefaultItemAnimator());
        boardRecycleView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        boardRecycleView.setAdapter(adapter);
    }
}
