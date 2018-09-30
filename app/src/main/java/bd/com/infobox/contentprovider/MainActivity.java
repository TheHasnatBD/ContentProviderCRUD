package bd.com.infobox.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bd.com.infobox.contentprovider.databinding.ActivityMainBinding;
import bd.com.infobox.sqlitelibrary.DatabaseModule.MyDatabaseHelper;
import bd.com.infobox.sqlitelibrary.DatabaseModule.Student;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final Uri CONTENT_URI = Uri.parse(StudentContentProvider.CONTENT_STRING
            +"/"+MyDatabaseHelper.TABLE_STD_INFO);

    private List<Student> students = new ArrayList<>();
    private StudentRVadapter studentRVadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // showing data from DB
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String name = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COL_NAME));
                String dept = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COL_DEPT));
                String gender = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COL_GENDER));
                String year = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COL_YEAR));

                Student student = new Student(name, dept, gender, year);
                students.add(student);
            }while (cursor.moveToNext());

            // RecyclerView
            if (studentRVadapter == null){
                studentRVadapter = new StudentRVadapter(this, students);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                binding.studentListRV.setLayoutManager(linearLayoutManager);
                binding.studentListRV.setAdapter(studentRVadapter);
            }else {
                studentRVadapter.updateCollection(students);
            } // ending RecyclerView
        }


    } // ending onCreate

    public void saveInfo(View view) {
        String name = binding.inputName.getText().toString();
        String gender = binding.inputGender.getText().toString();
        String dept = binding.inputDept.getText().toString();
        String year = binding.inputYear.getText().toString();

        Student student = new Student(name, dept, gender, year);
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COL_NAME, student.getName());
        values.put(MyDatabaseHelper.COL_DEPT, student.getDept());
        values.put(MyDatabaseHelper.COL_GENDER, student.getGender());
        values.put(MyDatabaseHelper.COL_YEAR, student.getYear());

        Uri inserted_uri = getContentResolver().insert(CONTENT_URI, values);
        if (inserted_uri != null){
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            Log.e("saveInfo", "ID : " + inserted_uri.toString());

            binding.inputName.setText("");
            binding.inputGender.setText("");
            binding.inputDept.setText("");
            binding.inputYear.setText("");
        } else {
            Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show();
        }

    }
}
