package bd.com.infobox.contentprovider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bd.com.infobox.contentprovider.databinding.ActivityMainBinding;
import bd.com.infobox.sqlitelibrary.DatabaseModule.Student;

public class StudentRVadapter extends RecyclerView.Adapter<StudentRVadapter.StudentViewHolder>{
    private Context context;
    private List<Student> studentList;

    public StudentRVadapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_student_rv, viewGroup, false);
        StudentViewHolder studentViewHolder = new StudentViewHolder(view);
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        studentViewHolder.nameTV.setText(studentList.get(i).getName());
        studentViewHolder.deptTV.setText(studentList.get(i).getDept());
        studentViewHolder.genderTV.setText(studentList.get(i).getGender());

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView nameTV, deptTV, genderTV;
        ImageView menuItem;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.rv_stdName);
            deptTV = itemView.findViewById(R.id.rv_stdDept);
            genderTV = itemView.findViewById(R.id.rv_stdGender);

            menuItem = itemView.findViewById(R.id.rv_itemMenu);


        }
    }

    public void updateCollection(List<Student> studentList){
        this.studentList = studentList;
        notifyDataSetChanged();
    }
}
