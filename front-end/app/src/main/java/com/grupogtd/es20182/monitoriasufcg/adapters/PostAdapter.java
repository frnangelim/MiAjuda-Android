package com.grupogtd.es20182.monitoriasufcg.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.grupogtd.es20182.monitoriasufcg.R;
import com.grupogtd.es20182.monitoriasufcg.activities.ClassActivity;
import com.grupogtd.es20182.monitoriasufcg.activities.LoginActivity;
import com.grupogtd.es20182.monitoriasufcg.activities.MainActivity;
import com.grupogtd.es20182.monitoriasufcg.service.domain.Course;
import com.grupogtd.es20182.monitoriasufcg.service.domain.Post;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.Callback.IServerObjectCallback;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.ServerConnector;
import com.grupogtd.es20182.monitoriasufcg.utils.Constant;
import com.grupogtd.es20182.monitoriasufcg.utils.Util;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context mContext;
    private ArrayList<Post> postsList;
    private Boolean hidden;

    public PostAdapter(Context mContext, ArrayList<Post> postsList) {
        this.mContext = mContext;
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.feed_item, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        Post currentPost = postsList.get(position);

        holder.author.setText(currentPost.getAuthor().getName());
        Date createdAt = currentPost.getCreatedAt();
        String dateFormatted = new SimpleDateFormat("dd/MM/yyyy").format(createdAt);
        String timeFormatted = new SimpleDateFormat("HH:mm").format(createdAt);
        holder.postDate.setText(dateFormatted);
        holder.postTime.setText(timeFormatted);
        holder.postTitle.setText(currentPost.getTitle());
        final String textBody = currentPost.getTextBody();
        if(textBody.length() > 40) {
            hidden = true;
            final String shortMsg = textBody.substring(0,40) + "...";
            holder.postBody.setText(shortMsg);
            holder.viewText.setVisibility(View.VISIBLE);
            holder.viewText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hidden) {
                        holder.postBody.setText(textBody);
                        hidden = false;
                        holder.viewText.setText(mContext.getString(R.string.hide));
                    } else {
                        holder.postBody.setText(shortMsg);
                        hidden = true;
                        holder.viewText.setText(mContext.getString(R.string.view));
                    }
                }
            });
        } else {
            holder.postBody.setText(textBody);
            holder.viewText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView postDate;
        TextView postTime;
        TextView postTitle;
        TextView postBody;
        Button viewText;

        public PostViewHolder(View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.author);
            postDate = itemView.findViewById(R.id.post_date);
            postTime = itemView.findViewById(R.id.post_time);
            postTitle = itemView.findViewById(R.id.post_title);
            postBody = itemView.findViewById(R.id.post_body);
            viewText = itemView.findViewById(R.id.btn_view);
        }
    }
}
