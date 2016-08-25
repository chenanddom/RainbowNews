package git.dom.com.rainbownews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dom.rainbownews.adapter.CollectionListAdapter;
import com.dom.rainbownews.base.BaseActivity;
import com.dom.rainbownews.db.NewsCollect;
import com.dom.rainbownews.domain.Collection;
import com.dom.rainbownews.utils.LogUtils;
import com.dom.rainbownews.utils.ToastUtils;

import java.util.List;

public class CollectionActivity extends BaseActivity {
    private NewsCollect collect;
    private List<Collection> list;
    private ListView mTtemList;
    private CollectionListAdapter adapter;

    @Override
    public void setView() {
        collect=new NewsCollect(CollectionActivity.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_collect);
    }

    @Override
    public void initData() {
        for (int i=0;i<10;i++)
//            collect.add("标题"+i,"http://www.baidu.com"+i,System.currentTimeMillis()+"");
    list = collect.findAll();
        for (int i=0;i<list.size();i++)
        LogUtils.printInfo("data",list.get(i).getTitle()+"--"+list.get(i).getTime());
    }

    @Override
    public void initView() {
        mTtemList=(ListView)findViewById(R.id.lv_collection_news);
        if(list.size()>0){
            mTtemList.setVisibility(View.VISIBLE);
            adapter=new CollectionListAdapter(CollectionActivity.this,list);
        }
        mTtemList.setAdapter(adapter);

    }

    @Override
    public void setListener() {
        mTtemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog2(position);
                return true;
            }
        });
        mTtemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CollectionActivity.this,ScanActivity.class);
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("url", list.get(position).getUrl());
                startActivity(intent);
                overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
            }
        });
    }
    public void showDialog2(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CollectionActivity.this);
        builder.setTitle("提示");
        builder.setMessage("是否删除该选项");
        // builder.setCancelable(false);//设置用户在对话框弹出的时候点返回无响应(但是此方法尽量不要使用，体验不好)
        builder.setPositiveButton("立即删除", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                boolean flag = collect.delete(list.get(position).getTime());
                if (flag) {
                    ToastUtils.ToastInfo(CollectionActivity.this,"删除成功");
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.ToastInfo(CollectionActivity.this,"删除失败！");
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消删除", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
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
    protected void onStop() {
        super.onStop();
        this.finish();
    }
}

