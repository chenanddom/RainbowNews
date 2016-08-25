package git.dom.com.rainbownews;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dom.rainbownews.base.BaseActivity;
import com.dom.rainbownews.base.BaseFragment;
import com.dom.rainbownews.fragment.BackHandledInterface;
import com.dom.rainbownews.fragment.ForeignNewsFragment;
import com.dom.rainbownews.fragment.HomeNewsFragment;
import com.dom.rainbownews.fragment.SocietyNewsFragdment;
import com.dom.rainbownews.fragment.TechNewsFragment;
import com.dom.rainbownews.slidingmenu.MySlidingMenu;
import com.dom.rainbownews.utils.LogUtils;
import com.dom.rainbownews.utils.StreamUtils;
import com.dom.rainbownews.utils.ToastUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * 主窗体实现新闻的概要显示
 * Created by Administrator on 2016/8/23 0023.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener ,BackHandledInterface,RadioButton.OnCheckedChangeListener{
    private ImageView mMenuToggle;
    private MySlidingMenu mSlidingMenu;
    private RelativeLayout mLayoutUpdate;
    private RelativeLayout mNightMode;
    private RelativeLayout mCollecte;
    private RelativeLayout mNote;
    private RelativeLayout mAbout;
    private RelativeLayout mSetting;
    private SharedPreferences preferences;
    private CheckBox checkBox1, checkBox2;
    private boolean isUpdate = false;
    private boolean isNightMode = false;
    private String mVersionName;
    private int mVersionConde;
    private String mDescription;
    private String mDownloadUrl;
    private FragmentManager fragmentManager;
    private List<String> list = new ArrayList<>();
    private RadioButton mRbHome,mRbForeign,mRbSociety,mRbTech;
    private HomeNewsFragment homeNewsFragment;
    private ForeignNewsFragment foreignNewsFragment;
    private SocietyNewsFragdment societyNewsFragdment;
    private TechNewsFragment techNewsFragment;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Const.UPDATE_CODE:
                    showDialog();
                    break;
                case Const.ERR_CODE:
                    ToastUtils.ToastInfo(MainActivity.this, "出错了!");
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
            }
        }
    };

    @Override
    public void setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("config", MODE_PRIVATE);
        fragmentManager=getFragmentManager();
    }

    @Override
    public void initData() {
        isUpdate = preferences.getBoolean(Const.ISUPDATE, false);
        isNightMode = preferences.getBoolean(Const.ISNIGHTMODE, false);

    }

    @Override
    public void initView() {
        mMenuToggle = (ImageView) findViewById(R.id.menu_toggle);
        mSlidingMenu = (MySlidingMenu) findViewById(R.id.slidingmenu);
        mMenuToggle.setOnClickListener(this);
        mLayoutUpdate = (RelativeLayout) findViewById(R.id.item_update);
        mNightMode = (RelativeLayout) findViewById(R.id.item_mode);
        mCollecte = (RelativeLayout) findViewById(R.id.item_collect);
        mNote = (RelativeLayout) findViewById(R.id.item_note);
        mAbout = (RelativeLayout) findViewById(R.id.item_about);
        mSetting = (RelativeLayout) findViewById(R.id.item_setting);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        mRbHome=(RadioButton)findViewById(R.id.rb_home_news);
        mRbForeign=(RadioButton)findViewById(R.id.rb_foreign_news);
        mRbSociety=(RadioButton)findViewById(R.id.rb_socienty_news);
        mRbTech=(RadioButton)findViewById(R.id.rb_tech_news);

        mLayoutUpdate.setOnClickListener(this);
        mNightMode.setOnClickListener(this);
        mCollecte.setOnClickListener(this);
        mNote.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mSetting.setOnClickListener(this);

        mRbHome.setOnCheckedChangeListener(this);
        mRbForeign.setOnCheckedChangeListener(this);
        mRbSociety.setOnCheckedChangeListener(this);
        mRbTech.setOnCheckedChangeListener(this);

//        System.out.println("update:" + isUpdate + "----------------------------");
        LogUtils.printInfo("update", "-------------------" + true);
        if (isUpdate) {
            checkBox1.setChecked(true);
            checkVesion();
        } else {
            checkBox1.setChecked(false);
        }
        if (isNightMode) {
            checkBox2.setChecked(true);
        } else {
            checkBox2.setChecked(false);
        }
        mRbHome.setChecked(true);
        homeNewsFragment=HomeNewsFragment.getInstance();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        // 把内容显示至真布局
        fragmentTransaction.replace(R.id.container,homeNewsFragment);
        // 提交事务
        fragmentTransaction.commit();
    }

    /*
    * 检查版本号
    * */
    public void checkVesion() {
        //获取当前时间
        final long startTime = System.currentTimeMillis();
        new Thread(/*new Runnable()*/) {
            @Override
            public void run() {
                Message msg = Message.obtain();
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(Const.STRUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    //设置请求方法
                    conn.setRequestMethod("GET");
                    //设置连接时间
                    conn.setConnectTimeout(8000);
                    //设置读取时长
                    conn.setReadTimeout(5000);
                    //建立真正的连接
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
                        // 通过链接获取输入流
                        InputStream is = conn.getInputStream();
                        // 将流转化成字符串
                        String result = StreamUtils.readFromStream(is);
                        JSONObject json = new JSONObject(result);
                        mVersionName = json.getString("versionName");
                        mVersionConde = json.getInt("versionCode");
                        mDescription = json.getString("description");
                        mDownloadUrl = json.getString("downloadUrl");
                        // 判断服务器的versioncode和本地放入versionCode对比
                        LogUtils.printInfo("versionCode", "--------------------------" + mVersionConde);
                        if (mVersionConde > getVersionCode()) {
                            // 如果网络服务器端获取的版本号比当前的版本号要大，那么就发送消息弹出对话框提示更新
                            msg.what = Const.UPDATE_CODE;
                        } else {
                            // 没有更新
                            msg.what = Const.ENTERY_HOME;
                        }
                    }
                } catch (Exception e) {
                    msg.what = Const.ERR_CODE;
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    long time = endTime - startTime;
                    // 此处强制休眠两秒钟
                    if (time < 2000) {
                        try {
                            Thread.sleep(2000 - time);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    handler.sendMessage(msg);
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }.start();
    }

    /**
     * 获取版本号
     *
     * @return 返回版本号
     */
    public int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            // 获取程序的信息
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取版本名
     *
     * @return 返回版本名
     */
    private String getVersionName() {
        String versionName = "";
        PackageManager packageManager = getPackageManager();
        try {
            // 获取程序包的信息
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            versionName = packageInfo.versionName;
            return versionName;

        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    private void downloadAPK() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            String target = Environment.getExternalStorageDirectory()
                    + "/update.apk";
            HttpUtils utils = new HttpUtils();
            utils.download(mDownloadUrl, target, new RequestCallBack<File>() {
                // 表示文件的下载进度
                @Override
                public void onLoading(long total, long current,
                                      boolean isUploading) {
                    // TODO Auto-generated method stub
                    super.onLoading(total, current, isUploading);
                    System.out.println("" + current + "--" + total);
                }

                // 下载成功是调用
                @Override
                public void onSuccess(ResponseInfo<File> arg0) {
                    // TODO Auto-generated method stub
                    // Toast.makeText(SplashActivity.this, "下载成功!",
                    // Toast.LENGTH_SHORT).show();
                    // 下载成功就跳到系统提示安装的界面
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(arg0.result),
                            "application/vnd.android.package-archive");
                    // startActivity(intent);
                    startActivityForResult(intent, 0);// 如果用户取消安装的话,
                    // 如果用户取消了安装就直接进入主界面
                }

                // 下载失败时调用
                @Override
                public void onFailure(HttpException arg0, String arg1) {
                    // TODO Auto-generated method stub
                    Toast.makeText(MainActivity.this, "下载失败!",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "你没有SD卡", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void setListener() {

    }

    /**
     * 弹出对话框提示是否升级
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("最新版本:" + mVersionName);
        builder.setMessage(mDescription);
        // builder.setCancelable(false);//设置用户在对话框弹出的时候点返回无响应(但是此方法尽量不要使用，体验不好)
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                System.out.println("立即更新。。。。。。。。。。。。。。。。。");
                downloadAPK();
            }
        });
        builder.setNegativeButton("稍后提醒", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                System.out.println("稍后提醒。。。。。。。。。。。。。。。。。");
                dialog.dismiss();
            }
        });
        // 用户点击放回就说明用户不感兴趣，需要监听他是否按了返回键，如果是就直接到达主页面
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_toggle:
                mSlidingMenu.toggleMenu();
                break;
            case R.id.item_update:
                isUpdate = preferences.getBoolean(Const.ISUPDATE, false);
                if (isUpdate) {
                    checkBox1.setChecked(false);
                    preferences.edit().putBoolean(Const.ISUPDATE, false).commit();
                } else {
                    checkBox1.setChecked(true);
                    preferences.edit().putBoolean(Const.ISUPDATE, true).commit();
                }
                break;
            case R.id.item_mode:
                isNightMode = preferences.getBoolean(Const.ISNIGHTMODE, false);
                if (isNightMode) {
                    checkBox2.setChecked(false);
                    preferences.edit().putBoolean(Const.ISNIGHTMODE, false).commit();
                } else {
                    checkBox2.setChecked(true);
                    preferences.edit().putBoolean(Const.ISNIGHTMODE, true).commit();
                }
                break;
            case R.id.item_collect:
                break;
            case R.id.item_note:
                break;
            case R.id.item_about:
                break;
            case R.id.item_setting:
                break;
            default:
        }
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
        switch (buttonView.getId()){
            case R.id.rb_home_news:
                if(homeNewsFragment==null){
                    homeNewsFragment=HomeNewsFragment.getInstance();
                }
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                // 把内容显示至真布局
                fragmentTransaction.replace(R.id.container,homeNewsFragment);
                // 提交事务
                fragmentTransaction.commit();
                break;
            case R.id.rb_foreign_news:
                    if(foreignNewsFragment==null){
                foreignNewsFragment=ForeignNewsFragment.getInstance();
            }
                FragmentTransaction fragmentTransaction2 = fragmentManager
                        .beginTransaction();
                // 把内容显示至真布局
                fragmentTransaction2.replace(R.id.container,foreignNewsFragment);
                // 提交事务
                fragmentTransaction2.commit();
                break;
            case R.id.rb_socienty_news:
                if (societyNewsFragdment==null){
                    societyNewsFragdment=SocietyNewsFragdment.getInstance();
                }
                FragmentTransaction fragmentTransaction3 = fragmentManager
                        .beginTransaction();
                // 把内容显示至真布局
                fragmentTransaction3.replace(R.id.container,societyNewsFragdment);
                // 提交事务
                fragmentTransaction3.commit();
                break;
            case R.id.rb_tech_news:
                if(techNewsFragment==null){
                    techNewsFragment=TechNewsFragment.getInstance();
                }
                FragmentTransaction fragmentTransaction4 = fragmentManager
                        .beginTransaction();
                // 把内容显示至真布局
                fragmentTransaction4.replace(R.id.container,techNewsFragment);
                // 提交事务
                fragmentTransaction4.commit();
                break;
        }}
    }


    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(MainActivity.this);
            tv.setTextSize(30);
            tv.setGravity(Gravity.CENTER);
            tv.setText(list.get(position) + "");
            return tv;
        }
    }
}
