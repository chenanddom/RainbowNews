package git.dom.com.rainbownews;

import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.dom.rainbownews.base.BaseActivity;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class AboutActivity extends BaseActivity {
    private ImageButton mBack;
    @Override
    public void setView() {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mBack=(ImageButton)findViewById(R.id.btn_about_back);
    }

    @Override
    public void setListener() {
    mBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
        }
    });
    }
}
