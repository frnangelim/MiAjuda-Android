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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.grupogtd.es20182.monitoriasufcg.R;
import com.grupogtd.es20182.monitoriasufcg.activities.ClassActivity;
import com.grupogtd.es20182.monitoriasufcg.service.domain.Course;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.Callback.IServerObjectCallback;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.ServerConnector;
import com.grupogtd.es20182.monitoriasufcg.utils.Constant;
import com.grupogtd.es20182.monitoriasufcg.utils.Util;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by francisco on 27/09/18.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private Context mContext;
    private ArrayList<Course> courseList;

    public CourseAdapter(Context mContext, ArrayList<Course> courseList) {
        this.mContext = mContext;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.course_item, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, final int position) {
        holder.courseName.setText(courseList.get(position).getName());
        holder.teacherName.setText(courseList.get(position).getTeacher().getName());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfirmDialog(position);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClassActivity.class);
                intent.putExtra(Constant.CLASS_OBJ_KEY, courseList.get(position));
                mContext.startActivity(intent);
            }
        });

        holder.courseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClassActivity.class);
                intent.putExtra(Constant.CLASS_OBJ_KEY, courseList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView courseName;
        TextView teacherName;
        ImageButton delete;
        LinearLayout courseLayout;

        public CourseViewHolder(View itemView) {
            super(itemView);

            courseName = itemView.findViewById(R.id.tv_course);
            teacherName = itemView.findViewById(R.id.tv_teacher);
            delete = itemView.findViewById(R.id.delete_class);
            cardView = itemView.findViewById(R.id.card_view);
            courseLayout = itemView.findViewById(R.id.course);
        }
    }

    private void openConfirmDialog(final int position) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }

        String courseName = courseList.get(position).getName();
        builder.setTitle(courseName)
                .setMessage(mContext.getString(R.string.confirm_course_exit))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        quitFromClass(position);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void quitFromClass(final int position) {
        ServerConnector mServerConnector = new ServerConnector(mContext);

        String courseId = courseList.get(position).get_id();

        mServerConnector.deleteObject(Constant.BASE_URL + Constant.QUIT_CLASS_QUERY + '/' + courseId, null, new IServerObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                courseList.remove(position);
                notifyDataSetChanged();
                Util.showShortToast(mContext, mContext.getString(R.string.course_exited));
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("result2", error.toString());
            }
        });
    }
}
