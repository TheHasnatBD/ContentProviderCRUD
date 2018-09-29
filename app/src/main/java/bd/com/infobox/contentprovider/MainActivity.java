package bd.com.infobox.contentprovider;

import android.content.ContentValues;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import bd.com.infobox.contentprovider.databinding.ActivityMainBinding;
import bd.com.infobox.sqlitelibrary.DatabaseModule.MyDatabaseHelper;
import bd.com.infobox.sqlitelibrary.DatabaseModule.Student;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final Uri CONTENT_URI = Uri.parse(StudentContentProvider.CONTENT_STRING
            +"/"+MyDatabaseHelper.TABLE_STD_INFO);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    public void saveInfo(View view) {
        String name = binding.inputName.getText().toString();
        String dept = binding.inputDept.getText().toString();
        String gender = binding.inputGender.getText().toString();
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
            binding.inputDept.setText("");
            binding.inputGender.setText("");
            binding.inputYear.setText("");
        } else {
            Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show();
        }

    }
}
