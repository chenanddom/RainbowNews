package com.dom.rainbownews.base;


import android.app.Fragment;
import android.os.Bundle;



import com.dom.rainbownews.fragment.BackHandledInterface;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public abstract class BaseFragment extends Fragment {
    protected BackHandledInterface mBackHandledInterface;

    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
    public abstract boolean onBackPressd();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例
        if (!(getActivity() instanceof BackHandledInterface)) {
            throw new ClassCastException("托管的窗体必须实现BackHandledInterface接口");
        } else {
            this.mBackHandledInterface = (BackHandledInterface) getActivity();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mBackHandledInterface.setSelectedFragment(this);
    }
}
