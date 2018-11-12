package com.grupogtd.es20182.monitoriasufcg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grupogtd.es20182.monitoriasufcg.R;
import com.grupogtd.es20182.monitoriasufcg.service.domain.Course;
import com.grupogtd.es20182.monitoriasufcg.utils.Constant;

public class ClassInfoFragment extends Fragment {
    private static Course course;

    public ClassInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ClassInfoFragment newInstance(Course course) {
        ClassInfoFragment fragment = new ClassInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.CLASS_OBJ_KEY, course);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            course = getArguments().getParcelable(Constant.CLASS_OBJ_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_class_info, container, false);

        TextView className = v.findViewById(R.id.class_name);
        className.setText(course.getName());

        TextView teacher = v.findViewById(R.id.class_teacher);
        teacher.setText(course.getTeacher().getName());

        TextView schedule = v.findViewById(R.id.class_schedule);
        schedule.setText(course.getSchedule());

        TextView roomNumber = v.findViewById(R.id.class_room);
        roomNumber.setText(course.getRoomNumber());

        TextView semester = v.findViewById(R.id.class_semester);
        semester.setText(course.getSemester());

        TextView office = v.findViewById(R.id.class_office);
        office.setText(course.getOffice_hours());

        TextView info = v.findViewById(R.id.class_info);
        info.setText(course.getInfo());

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
