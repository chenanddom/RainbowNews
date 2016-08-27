package git.dom.com.rainbownews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.dom.rainbownews.base.BaseActivity;
import com.dom.rainbownews.utils.LogUtils;
import com.dom.rainbownews.utils.ToastUtils;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class LoginActivity extends BaseActivity {
    private EditText editPass;
    private EditText editConfirmPass;
    private SharedPreferences preferences;
    private String accoutPass;
    private Button btnLogin;
    private Button btnCancel;
    private  String  mPass;
    private String  mConfirmPass;
    @Override
    public void setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initData() {
        preferences=getSharedPreferences("config",MODE_PRIVATE);
        accoutPass=preferences.getString(Const.ACCOUTPASS, "");
        LogUtils.printInfo("tag","accoutPass"+accoutPass);
    }

    @Override
    public void initView() {
        editPass = (EditText)findViewById(R.id.login_password);
        editConfirmPass=(EditText)findViewById(R.id.login_confirm_password);
        btnLogin=(Button)findViewById(R.id.btn_login);
        btnCancel=(Button)findViewById(R.id.btn_cancel);

    }

    @Override
    public void setListener() {
    btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPass=editPass.getText().toString().trim();
            mConfirmPass=editConfirmPass.getText().toString().trim();
            LogUtils.printInfo("tag","pass:"+mPass+"-"+"confirmPass:"+mConfirmPass+"-"+"accoutPass:"+accoutPass);
            if (!TextUtils.isEmpty(mPass)||!TextUtils.isEmpty(mConfirmPass)) {
                if (mPass.equals(mConfirmPass) && mPass.equals(accoutPass)) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
                } else {
                    ToastUtils.ToastInfo(LoginActivity.this, "密码不对");
                }
            }else{
                ToastUtils.ToastInfo(LoginActivity.this, "输入内容不能为空");
            }

        }
    });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    finish();
    }
}
