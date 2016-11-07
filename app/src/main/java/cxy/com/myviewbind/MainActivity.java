package cxy.com.myviewbind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.annotation.BindView;

import cxy.com.bindview.MyBindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edittext)
    EditText edittext;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyBindView.bind(this);

        edittext.setText("界面1");
        button.setText("跳转至第二个界面");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
    }
}
