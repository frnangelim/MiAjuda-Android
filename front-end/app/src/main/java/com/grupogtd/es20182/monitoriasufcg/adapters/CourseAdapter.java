package com.grupogtd.es20182.monitoriasufcg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grupogtd.es20182.monitoriasufcg.R;
import com.grupogtd.es20182.monitoriasufcg.service.domain.Course;

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
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.courseName.setText(courseList.get(position).getName());
        holder.teacherName.setText(courseList.get(position).getTeacher().getName());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView courseName;
        TextView teacherName;

        public CourseViewHolder(View itemView) {
            super(itemView);

            courseName = itemView.findViewById(R.id.tv_course);
            teacherName = itemView.findViewById(R.id.tv_teacher);

        }
    }
}
