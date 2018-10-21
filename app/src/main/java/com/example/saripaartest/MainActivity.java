package com.example.saripaartest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {

    @BindView(R.id.eMailForm)
    @NotEmpty(message = "文字を入力してください")
    @Email(message = "形式が誤っています")
    EditText emailEditText;

    @BindView(R.id.passwordForm)
    //最小文字数
    @Password(min = 4,message = "4文字以上入力してください")
    EditText passwordForm;

    //private CheckBox iAgreeCheckBox;

    Validator validator;

    @OnClick(R.id.submit)
    public void onButtonClick(View view) {
        validator.validate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize ButterKnife
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onValidationSucceeded() {
        //バリデーションを抜けれたとき表示される
        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            //エラーになったViewを取得する
            View view = error.getView();
            //エラーメッセージを取得する(指定のない場合はデフォルトのメッセージ)
            String message = error.getCollatedErrorMessage(this);

            // エラーメッセージをeditTextやtextViewに表示する
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
