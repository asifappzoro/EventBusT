package com.beyram.eventbust;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.btnPostSticky)
    Button btnPostSticky;
    @BindView(R.id.btnPost)
    Button btnPost;
    private EventBus bus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnPost.setOnClickListener(this);
        btnPostSticky.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onStop() {
        bus.unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUser(User event) {
        txtName.setText("Name : " + event.getName());
        txtPhone.setText("Phone : " + event.getPhone());
        txtEmail.setText("Email : " + event.getEmail());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPost: {
                if (etName.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all empty field", Toast.LENGTH_LONG).show();
                } else {
                    bus.post(new User(etName.getText().toString(), etPhone.getText().toString(), etEmail.getText().toString()));
                }
                break;
            }
            case R.id.btnPostSticky: {
                if (etName.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all empty field", Toast.LENGTH_LONG).show();
                } else {
                    bus.postSticky(new User(etName.getText().toString(), etPhone.getText().toString(), etEmail.getText().toString()));
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                }
                break;
            }
        }
    }
}
