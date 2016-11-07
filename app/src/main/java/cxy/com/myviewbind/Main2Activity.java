package cxy.com.myviewbind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;

import com.example.annotation.BindView;

import cxy.com.bindview.MyBindView;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.textview)
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MyBindView.bind(this);
        textView.setText("我是第二个界面");
    }
}
