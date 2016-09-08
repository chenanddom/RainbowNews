package git.dom.com.rainbownews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import com.dom.rainbownews.adapter.HistoryAdapter;
import com.dom.rainbownews.base.BaseActivity;
import com.dom.rainbownews.db.HistoryRecord;
import com.dom.rainbownews.domain.History;
import com.dom.rainbownews.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity implements AdapterView.OnItemLongClickListener {
    private ListView listView;
    private HistoryRecord record;
    private List<History> list = new ArrayList<>();
    private HistoryAdapter adapter;
    private ImageButton btnClear;
    private List<History> mlist = new ArrayList<>();
    private ImageButton mImageBack;

    @Override
    public void setView() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_history);
        record = new HistoryRecord(HistoryActivity.this);
    }
    @Override
    public void initView() {
        listView = (ListView) findViewById(R.id.lvhistorycontent);
        btnClear = (ImageButton) findViewById(R.id.recordclear);
        mImageBack=(ImageButton)findViewById(R.id.imghistoryback) ;
        adapter = new HistoryAdapter(HistoryActivity.this, list);
        listView.setAdapter(adapter);
    }
    @Override
    public void initData() {
        list = record.findAllHistory();
        System.out.println(list.toString());
    }
    @Override
    public void setListener() {
        listView.setOnItemLongClickListener(this);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean tag;
                for (int i = 0; i < mlist.size(); i++) {
                    tag = record.deleteHistory(mlist.get(i).getId());
                    LogUtils.printInfo("tag","---------------------------------"+tag+"-----------------------------------");
                    if (tag) {
                        list.remove(mlist.get(i));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
            }
        });
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        CheckBox cb = (CheckBox) view.findViewById(R.id.cbselect);
        cb.setVisibility(View.VISIBLE);
        cb.setChecked(true);
        mlist.add(list.get(position));
        return false;
    }
}
