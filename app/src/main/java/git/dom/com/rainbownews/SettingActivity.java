package git.dom.com.rainbownews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.dom.rainbownews.base.BaseActivity;
import com.dom.rainbownews.server.NewsPushServer;
import com.dom.rainbownews.utils.ToastUtils;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class SettingActivity extends BaseActivity {
    private ToggleButton mTBNewsPush;
    private ToggleButton mTBAccoutSave;
    private SharedPreferences preferences;
    private boolean isPush = false;
    private boolean isSave = false;
   private Intent intent;

    @Override
    public void setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initData() {
        preferences = this.getSharedPreferences("config", MODE_PRIVATE);
        isPush = preferences.getBoolean(Const.ISNEWSPUSH, false);
        isSave = preferences.getBoolean(Const.ISACCOUTSAVE, false);
//        ToastUtils.ToastInfo(SettingActivity.this,"isPush"+isPush+"-"+"isSave"+isSave);
     intent =new Intent(SettingActivity.this, NewsPushServer.class);
    }

    @Override
    public void initView() {
        mTBNewsPush = (ToggleButton) findViewById(R.id.news_push);
        mTBAccoutSave = (ToggleButton) findViewById(R.id.accout_save);
        if (isPush) {
            mTBNewsPush.setChecked(true);
        } else {
            mTBNewsPush.setChecked(false);
        }
        if (isSave) {
            mTBAccoutSave.setChecked(true);
        } else {
            mTBAccoutSave.setChecked(false);
        }
    }

    @Override
    public void setListener() {
        mTBNewsPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPush = preferences.getBoolean(Const.ISNEWSPUSH, false);
                if (isPush) {
                    mTBNewsPush.setChecked(false);
                    preferences.edit().putBoolean(Const.ISNEWSPUSH, false).commit();
                    ToastUtils.ToastInfo(SettingActivity.this, "已经关闭新闻推送");
                    stopService(intent);
                } else {
                    mTBNewsPush.setChecked(true);
                    preferences.edit().putBoolean(Const.ISNEWSPUSH, true).commit();
                    ToastUtils.ToastInfo(SettingActivity.this, "已经开启新闻推送");
                    startService(intent);
                }

            }
        });
        mTBAccoutSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSave = preferences.getBoolean(Const.ISACCOUTSAVE, false);
                if (isSave) {
                    mTBAccoutSave.setChecked(false);
                    preferences.edit().putBoolean(Const.ISACCOUTSAVE, false).commit();
                    ToastUtils.ToastInfo(SettingActivity.this, "已经关闭账户安全");
                } else {
                    mTBAccoutSave.setChecked(true);
                    preferences.edit().putBoolean(Const.ISACCOUTSAVE, true).commit();
                    ToastUtils.ToastInfo(SettingActivity.this, "已经开启账户安全");
                    showDialog();
                }
            }
        });
    }

    public void showDialog() {
        View view = View.inflate(SettingActivity.this, R.layout.accout_input_dailog, null);
        final EditText edit_accout = (EditText)view.findViewById(R.id.edit_accout);
        final EditText edit_pass = (EditText)view.findViewById(R.id.edit_pass);
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setIcon(R.mipmap.accout);
        builder.setTitle("账户信息");
        builder.setView(view);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String accoutName = edit_accout.getText().toString().trim();
                String accoutPass = edit_pass.getText().toString().trim();
                if (TextUtils.isEmpty(accoutName) && TextUtils.isEmpty(accoutPass)) {
                    ToastUtils.ToastInfo(SettingActivity.this, "输入内容不能空");
                    return;
                } else {
                    preferences.edit().putString(Const.ACCOUTNAME, accoutName).commit();
                    preferences.edit().putString(Const.ACCOUTPASS, accoutPass).commit();
                    ToastUtils.ToastInfo(SettingActivity.this, "设置成功");
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
