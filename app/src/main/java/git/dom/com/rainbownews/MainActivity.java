package git.dom.com.rainbownews;

import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dom.rainbownews.base.BaseActivity;
import com.dom.rainbownews.slidingmenu.MySlidingMenu;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * 主窗体实现新闻的概要显示
 * Created by Administrator on 2016/8/23 0023.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mMenuToggle;
    private MySlidingMenu mSlidingMenu;
    private ListView listItem;
    private RelativeLayout mLayoutUpdate;
    private RelativeLayout mNightMode;
    private RelativeLayout mCollecte;
    private RelativeLayout mNote;
    private RelativeLayout mAbout;
    private RelativeLayout mSetting;
    private SharedPreferences preferences;
    private CheckBox checkBox1,checkBox2;
    private boolean isUpdate=false;
    private static final String ISUPDATE="isupdate";

    private List<String> list = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    public void setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences("config",MODE_PRIVATE);
        for (int i = 0; i < 100; i++)
            list.add("选项" + i);
    }

    @Override
    public void initData() {
    isUpdate=preferences.getBoolean(ISUPDATE,false);

    }

    @Override
    public void initView() {
        mMenuToggle = (ImageView) findViewById(R.id.menu_toggle);
        mSlidingMenu = (MySlidingMenu) findViewById(R.id.slidingmenu);
        listItem = (ListView) findViewById(R.id.list_item);
        mMenuToggle.setOnClickListener(this);
        mLayoutUpdate=(RelativeLayout)findViewById(R.id.item_update);
        mNightMode=(RelativeLayout)findViewById(R.id.item_mode);
        mCollecte=(RelativeLayout)findViewById(R.id.item_collect);
        mNote=(RelativeLayout)findViewById(R.id.item_note);
        mAbout=(RelativeLayout)findViewById(R.id.item_about);
        mSetting=(RelativeLayout)findViewById(R.id.item_setting);
        checkBox1=(CheckBox)findViewById(R.id.checkBox1);
        checkBox2=(CheckBox)findViewById(R.id.checkBox2);
        mLayoutUpdate.setOnClickListener(this);
        mNightMode.setOnClickListener(this);
        mCollecte.setOnClickListener(this);
        mNote.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        listItem.setAdapter(new MyAdapter());
        if(isUpdate){
            checkBox1.setChecked(true);
        }else{
            checkBox1.setChecked(false);
        }

    }

    @Override
    public void setListener() {
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, list.get(position)+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_toggle:
                mSlidingMenu.toggleMenu();
                break;
            case R.id.item_update:
                isUpdate=preferences.getBoolean(ISUPDATE,false);
                if (isUpdate){
                    checkBox1.setChecked(false);
                    preferences.edit().putBoolean(ISUPDATE,false).commit();
                }else{
                    checkBox1.setChecked(true);
                    preferences.edit().putBoolean(ISUPDATE,true).commit();
                }
                break;
            case R.id.item_mode:

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
