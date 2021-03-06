package com.dom.rainbownews.poupwidowmenu;

/**
 * Created by Administrator on 2016/8/25 0025.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dom.rainbownews.adapter.BodyAdapter;
import com.dom.rainbownews.db.NewsCollect;
import com.dom.rainbownews.utils.ToastUtils;

import java.util.List;

import git.dom.com.rainbownews.Const;
import git.dom.com.rainbownews.HistoryActivity;

public class MyPopupMenu extends PopupWindow {

    /**
     * 菜单栏的整体布局LinearLayout
     */
    private LinearLayout linearLayout;

    /**
     * 菜单栏分类标题布局GridView
     */
    private GridView gv_title;

    /**
     * 菜单栏功能图标与名称GridView
     */
    private GridView gv_body;

    /**
     * 菜单栏功能图标与名称GridView的适配
     */
    private BodyAdapter[] bodyAdapter;

    /**
     * 菜单栏分类标题GridView的适配
     */
    // private TitleAdapter titleAdapter;

    private Context context;

    /**
     * 当前选中的分类标题
     */
    private int currentIndex = 0;

    /**
     * 上一次选中的分类标题 用于选择分类标题时的左右移动动画，判断应该怎样移动
     */
    private int preIndex = 0;

    /**
     * 标题与功能布局中间的分界线 RelativeLayout + TextView
     */
    private RelativeLayout divisionLayout;

    /**
     * 屏幕宽度
     */
    private int screenWidth = 0;
    private String[] data;
    private NewsCollect newsCollect;
    SharedPreferences sharedPreferences;

    @SuppressWarnings("deprecation")
    public MyPopupMenu(final Context context, List<String> titles,
                       final List<List<String>> item_names,
                       List<List<Integer>> item_images, final String[] data) {
        super(context);
        this.context = context;
        this.data = data;
        newsCollect = new NewsCollect(context);
        sharedPreferences = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        /**
         * 菜单栏的整体布局LinearLayout初始化
         */
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        /**
         * 获取屏幕宽度
         */
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();

        /**
         * 分界线布局初始化
         */
        divisionLayout = new RelativeLayout(context);
        divisionLayout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, 3));
        divisionLayout.setBackgroundColor(Color.DKGRAY);

        /**
         * 标题布局初始化
         */
        gv_title = new GridView(context);

        /**
         * 用于重新初始化adapter
         */
        final List<String> l = titles;
        final Context c = context;

		/* titleAdapter = new TitleAdapter(titles, context, 0); */

        /**
         * 设置被选中后，背景颜色不再是系统原有的黄色，改为TRANSPARENT
         */
		/*
		 * gv_title.setSelector(new ColorDrawable(Color.TRANSPARENT));
		 * gv_title.setAdapter(titleAdapter);
		 */

        /**
         * 设置GridView列数
         */
		/*
		 * gv_title.setNumColumns(titleAdapter.getCount());
		 * gv_title.setBackgroundColor(Color.TRANSPARENT);
		 */

        /**
         * 选择分类标题时的响应事件
         */
        gv_title.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                /**
                 * 重新初始化adapter，为了改变标题选择颜色
                 */
                // titleAdapter = new TitleAdapter(l, c, position);

                preIndex = currentIndex;
                currentIndex = position;

                // gv_title.setAdapter(titleAdapter);

                /**
                 * 分界线布局中的textView跟随选中标题移动位置的，设置为动画效果
                 */
                divisionTran(position);

                /**
                 * 用于功能图标GridView动画效果 TranslateAnimation方法中的参数设置暂时不太明确
                 * 似乎，参数都是相对于控件自身的位置 第一个参数是开始位置，第二个是结束位置 有时间会弄清楚
                 */
                Animation translateBody;
                if (preIndex < currentIndex) {
                    translateBody = new TranslateAnimation(screenWidth, 0, 0, 0);
                    translateBody.setDuration(500);
                    gv_body.startAnimation(translateBody);
                } else if (preIndex > currentIndex) {
                    translateBody = new TranslateAnimation(-screenWidth, 0, 0,
                            0);
                    translateBody.setDuration(500);
                    gv_body.startAnimation(translateBody);
                }

                gv_body.setAdapter(bodyAdapter[position]);

            }
        });

        bodyAdapter = new BodyAdapter[item_names.size()];
        for (int i = 0; i < item_names.size(); i++) {
            bodyAdapter[i] = new BodyAdapter(context, item_names.get(i),
                    item_images.get(i));
        }
        gv_body = new GridView(context);
        gv_body.setNumColumns(3);
        gv_body.setBackgroundColor(Color.TRANSPARENT);
        gv_body.setPadding(0, 10, 0, 10);
        gv_body.setAdapter(bodyAdapter[0]);

        /**
         * 选择功能图标时的响应事件
         */
        gv_body.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				/*	*//**
                 * 这里只是在控制台输出了一下功能的名称
                 */
				/*
				 * System.out.println(item_names.get(currentIndex).get(position))
				 * ;
				 */

                switch (position) {
                    case 0:
                        boolean flag = newsCollect.add(data[0], data[1],data[2]);
                        if (flag) {
                            Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT)
                                    .show();
                        } else {

                            Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        break;

                    case 1:
                        shareUrl(data[1]);
                        break;
                    case 2:
                        boolean night_mode = sharedPreferences.getBoolean(
                               Const.ISNIGHTMODE, false);
                        if (night_mode) {
                            sharedPreferences.edit()
                                    .putBoolean(Const.ISNIGHTMODE, false).commit();
                            Toast.makeText(context,"关闭夜间模式",Toast.LENGTH_SHORT).show();
                        } else {
                            sharedPreferences.edit()
                                    .putBoolean(Const.ISNIGHTMODE, true).commit();
                            Toast.makeText(context, "开启夜间模式", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
         /*               boolean tag = sharedPreferences.getBoolean(Const.ISRECORDS,
                                false);
                        if (tag) {
                           *//* sharedPreferences.edit()
                                    .putBoolean(Const.ISRECORDS, false).commit();*//*
                            ToastUtils.ToastInfo(context, "无痕浏览关闭");
                        } else {
                            sharedPreferences.edit()
                                    .putBoolean(Const.ISRECORDS, true).commit();
                            ToastUtils.ToastInfo(context, "无痕浏览开启");
                        }*/
                        context.startActivity(new Intent(context, HistoryActivity.class));
                        
                        break;
                    case 4:
                        boolean flag2 = sharedPreferences.getBoolean(Const.ISCLEARCHACHE,
                                false);
                        if(flag2){
                            sharedPreferences.edit()
                                    .putBoolean(Const.ISCLEARCHACHE, false).commit();
                            ToastUtils.ToastInfo(context, "缓存清理关闭");
                        }else{
                            sharedPreferences.edit()
                                    .putBoolean(Const.ISCLEARCHACHE, true).commit();
                            ToastUtils.ToastInfo(context, "缓存清理关闭");
                        }
                        break;
                    case 5:
                       System.exit(0);
                        break;

                    default:
                }
            }
        });

        /**
         * 初始化textView位置
         */
        divisionTran(0);

        /**
         * 把三个子布局加入到整体布局中去
         */
        linearLayout.addView(gv_title);
        linearLayout.addView(divisionLayout);
        linearLayout.addView(gv_body);

        this.setContentView(linearLayout);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);

        /**
         * 以下代码是为了解决，菜单栏出现后，不能响应再次按menu按键使菜单栏消失的问题
         * 在这个网址找到的答案http://blog.csdn.net/admin_/article/details/7278402 可以自己去看
         */
        this.setFocusable(true);
        linearLayout.setFocusableInTouchMode(true);
        linearLayout.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_MENU)
                        && (MyPopupMenu.this.isShowing())) {
                    MyPopupMenu.this.dismiss();
                    // titleAdapter = new TitleAdapter(l, c, 0);
                    // gv_title.setAdapter(titleAdapter);
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * 分界线布局中的textView跟随选中标题移动位置的，设置为动画效果
     */
    public void divisionTran(int position) {

        /**
         * 先移除了RelativeLayout中原有的textView
         */
        divisionLayout.removeAllViews();

        /**
         * 重新设置textView布局属性 动态改变控件位置 第一步
         */
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                screenWidth / 3, LayoutParams.MATCH_PARENT);

        /**
         * 设置动画效果
         */
        Animation translateTextView;
        translateTextView = new TranslateAnimation((preIndex - currentIndex)
                * screenWidth / 3, 0, 0, 0);

        /**
         * 根据选中的标题确定布局 动态改变控件位置 第二步
         */
        if (position == 0) {
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        } else if (position == 1) {
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        } else {
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        }

        /**
         * 动态改变控件位置 第三步
         */
        TextView line = new TextView(context);
        line.setBackgroundColor(Color.WHITE);
        divisionLayout.addView(line, lp);

        /**
         * 设置动画执行时间
         */
        translateTextView.setDuration(200);

        /**
         * 启动动画
         */
        line.startAnimation(translateTextView);
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
        context.startActivity(Intent.createChooser(intent, "分享"));
    }
}
