package git.dom.com.rainbownews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dom.rainbownews.base.BaseActivity;

/**
 * 该页是一个动画引导页
 *
 * @author adminster
 */
public class LandingPageActivity extends BaseActivity {
    private Drawable pic1;
    private ImageView image_back01;
    private TextView landingtitle;
    /**
     * 三个切换的动画
     */
    private Animation mFadeIn;
    private Animation mFadeInScale;
    private Animation mFadeOut;
    private SharedPreferences sharedPreferences;
    private boolean flag;

    @Override
    public void setView() {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        sharedPreferences = this.getSharedPreferences("config", MODE_PRIVATE);
        setContentView(R.layout.activity_landingpage);
        flag = sharedPreferences.getBoolean("islock", false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        image_back01 = (ImageView) findViewById(R.id.image_back01);
        landingtitle = (TextView) findViewById(R.id.tv_landingtitle);
        init();
    }

    private void init() {
        initAnim();
        initBackground();
        /**
         * 界面刚开始显示的内容
         */
        image_back01.setImageDrawable(pic1);

        image_back01.startAnimation(mFadeIn);
    }

    @Override
    public void setListener() {
        // TODO Auto-generated method stub
        mFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                image_back01.setAnimation(mFadeInScale);
            }
        });
        mFadeInScale.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                image_back01.startAnimation(mFadeOut);
            }
        });
        mFadeOut.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                if (flag) {
                    startActivity(new Intent(LandingPageActivity.this, LoginActivity.class));
                    finish();
                    overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
                } else {
                    startActivity(new Intent(LandingPageActivity.this, MainActivity.class));
                    finish();
                    // 页面之间动画切换 enterAnim进入的动画 exitAnim退出的动画
                    overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
                }

            }
        });
    }
    /**
     * 初始化背景图
     */
    private void initBackground() {
        // TODO Auto-generated method stub
        pic1 = getResources().getDrawable(R.mipmap.landingpage_background);
    }
    /**
     * 初始化动画
     */
    private void initAnim() {
        mFadeIn = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_in);
        mFadeIn.setDuration(1000);
        mFadeInScale = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_in_scale);
        mFadeInScale.setDuration(3000);
        mFadeOut = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_out);
        mFadeOut.setDuration(1000);
    }
}

