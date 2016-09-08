package git.dom.com.rainbownews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.dom.rainbownews.base.BaseActivity;
import com.dom.rainbownews.db.HistoryRecord;
import com.dom.rainbownews.poupwidowmenu.MyPopupMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25 0025.
 */

@SuppressLint("SetJavaScriptEnabled")
public class ScanActivity extends BaseActivity implements OnClickListener {
    private WebView webView;
    private String url;
    private ImageButton back_btn, share_btn, webview_back, webview_next,
            menu_btn;
    private String title;
    private SharedPreferences sharedPreferences;
    private boolean night_mode;
    private RelativeLayout layout_bar;
    private PopupWindow popupWindow;
    private View view;
    private GridView gridView;
    private List<String> titles;
    private List<List<String>> item_names; // 选项名称
    private List<List<Integer>> item_images; // 选项图标
    private MyPopupMenu myPopupMenu;
    private String[] data;
    //    private HistoryItem historyItem;
    private boolean tag;//是否开启隐私模式
    private HistoryRecord record;

    @Override
    public void setView() {
        // TODO Auto-generated method stub

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scan);
        record = new HistoryRecord(ScanActivity.this);
        data = new String[3];
        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
//        historyItem = new HistoryItem(ScanActivity.this);
        layout_bar = (RelativeLayout) findViewById(R.id.layout_bar);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        if (TextUtils.isEmpty(title)) {
            data[0] = "标题未知";
            data[1] = url;
            data[2] = System.currentTimeMillis() + "";
           boolean flag= record.addHistory("未知标题", url);
            Log.i("tag","-------------------------------------------------"+flag+"---------------------------------------");
        } else {
            data[0] = title;
            data[1] = url;
            data[2] = System.currentTimeMillis() + "";
            boolean tag = record.addHistory(title, url);
            Log.i("tag","-------------------------------------------------"+tag+"---------------------------------------");
        }
        night_mode = sharedPreferences.getBoolean(Const.ISNIGHTMODE, false);
        if (night_mode) {
            layout_bar.setBackgroundResource(R.mipmap.title_barbackground);
        }
        tag = sharedPreferences.getBoolean(Const.ISPRIVATE, false);
        if (tag) {
            System.out.println("------------------》" + title + "---" + url);
        } else {
            addHistory(title, url);
        }
        initData2();
    }

    @Override
    public void initData() {

    }

    public void initData2() {
        /**
         * 选项图标
         */
        item_images = new ArrayList<List<Integer>>();
        item_images.add(addItems(new Integer[]{R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher}));
        item_names = new ArrayList<List<String>>();
        item_names.add(addItems(new String[]{"我的收藏", "分享出去", "夜间模式",
                "历史纪录", "清理缓存", "退出"}));

        myPopupMenu = new MyPopupMenu(this, titles, item_names, item_images,
                data);
        /**
         * 设置菜单栏推拉动画效果 res/anim中的xml文件与styles.xml中的style配合使用
         */
        myPopupMenu.setAnimationStyle(R.style.AppTheme);
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        webView = (WebView) findViewById(R.id.webView);
        back_btn = (ImageButton) findViewById(R.id.btn_back);
        share_btn = (ImageButton) findViewById(R.id.btn_share);
        webview_back = (ImageButton) findViewById(R.id.webview_back);
        webview_next = (ImageButton) findViewById(R.id.webview_next);
        menu_btn = (ImageButton) findViewById(R.id.webview_menu);
        back_btn.setOnClickListener(this);
        share_btn.setOnClickListener(this);
        webview_back.setOnClickListener(this);
        webview_next.setOnClickListener(this);
        menu_btn.setOnClickListener(this);
        webViewSetting(webView);
        webView.loadUrl(url);


    }

    @Override
    public void setListener() {
        // TODO Auto-generated method stub
        /*
		 * NewsCollect nc = new NewsCollect(ScanActivity.this); List<Collection>
		 * list = nc.findAll();
		 * System.out.println("========"+list.get(0).getTitle());
		 */
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
                break;
            case R.id.btn_share:
                shareUrl(url);
                break;
            case R.id.webview_back:
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                break;
            case R.id.webview_next:
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                break;
            case R.id.webview_menu:
			/*
			 * Intent intent = new Intent(ScanActivity.this,MenuDialog.class);
			 * intent.putExtra("title", title); intent.putExtra("url", url);
			 * startActivity(intent);
			 */
                if (myPopupMenu.isShowing()) {
                    myPopupMenu.dismiss();
                } else {
                    /**
                     * 这句代码可以使菜单栏如对话框一样弹出的效果
                     * myPopupMenu.setAnimationStyle(android.R.
                     * style.Animation_Dialog);
                     */
                    // 设置菜单栏显示位置
                    myPopupMenu.showAtLocation(findViewById(R.id.layout),
                            Gravity.BOTTOM, 0, 60);
                    myPopupMenu.isShowing();
                }
                break;
            default:
        }
    }

    public void menuDialog() {

    }

    /**
     * 设置webview,使得webView更贱的适应于使用
     *
     * @parammWebView将要设置的webview传进来
     */
    public void webViewSetting(WebView mWebView) {
        // 设置webview可以伸缩
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        // 设置此属性，可任意比例缩放
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // webSettings.setJavaScriptEnabled(true); //支持js
        // 优先使用缓存：
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.clearCache(true);
        mWebView.destroyDrawingCache();
    }

    /**
     * 设置分享的内容
     *
     * @paramcontent要分享URL
     */
    public void shareUrl(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        // webView.loadDataWithBaseURL(null, "","text/html", "utf-8",null);
        super.onDestroy();
    }

    /**
     * 转换为List<Integer> 用于菜单栏中的标题赋值
     *
     * @param values
     * @return
     */
    private List<Integer> addItems(Integer[] values) {

        List<Integer> list = new ArrayList<Integer>();
        for (Integer var : values) {
            list.add(var);
        }

        return list;
    }

    /**
     * 转换为List<String> 用于菜单栏中的菜单项图标赋值
     *
     * @param values
     * @return
     */
    private List<String> addItems(String[] values) {

        List<String> list = new ArrayList<String>();
        for (String var : values) {
            list.add(var);
        }

        return list;
    }

    /**
     * 添加历史记录
     *
     * @paramtitle历史记录的标题
     * @paramurl历史记录的网址
     */
    public void addHistory(String title, String url) {
 /*      boolean flag = historyItem.addHistory(title, url);
        if (flag) {
            System.out.println("历史记录插入成功!!------------->>>>>>");
        } else {
            System.out.println("历史记录插入失败!!-------------<<<<<<");
        }*/
    }
}
