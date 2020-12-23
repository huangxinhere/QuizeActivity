package com.example.criminallintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override/*使用布局并找到布局中的RecyclerView视图*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list,container,false);//假想相关布局文件、容器、布尔值放在inflate（加载布局的系统服务）熔炉里面锻造出View类的对象

        /*创建布局/的实例化？与创建view相比，为什么这个引用id，上面引用文件名？*/
        mCrimeRecyclerView = (RecyclerView) view//这个view就是前面创建的view，它包含的文件再取出id？
                .findViewById(R.id.crime_recycle_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//启动一个activity，就需要拿到activity对象才可以启动，而fragment对象是没有startActivity()方法的
        /*RecyclerView视图创建完成后，就立即转交给了LayoutManager对象,LM负责放置和屏幕滚动*/

        updateUI();//有crimes，有adapter，有ViewHolder

        return view;//返回给托管的activity？
    }

    /*关联Adapter和RecyclerView：创建CrimeAdapter，设置给RecyclerView*/
    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());//返回或构造crimeLab，似乎是CrimeLab类的唯一指定对象；然后才能用它的方法？public……？
        List<Crime> crimes = crimeLab.getCrimes();//返回数组数组列表。为什么不直接创建数组？

        mAdapter = new CrimeAdapter(crimes);//创建对象：类加实例
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    /*定义ViewHolder内部类：实例化并使用list_item_crime布局*/
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;

        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime,parent,false));
            View itemView = inflater.inflate(R.layout.list_item_crime,parent,false);
            itemView.setOnClickListener(this);//itemView是什么？和之前的设置监听器有什么不同？

            /*实例化组件：在哪实例化？现在在内部类里；相关的布局中？/如何实例化？和平常的有何不同？*/
            mTitleTextView = itemView.findViewById(R.id.textView);
            mDateTextView = itemView.findViewById(R.id.crime_date);
        }

        /*只要取到一个Crime，CrimeHolder就会刷新显示TextView标题视图和TextView日期视图;bind:捆绑，捆绑组件和数据*/
        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());//出现了什么错误啊到底……
            mDateTextView.setText(mCrime.getDate().toString());
        }

        @Override
        public void onClick(View view){
            Toast.makeText(getActivity(),
                    mCrime.getTitle() + "clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /*Adapter:显示新创建的ViewHolder或让Crime对象和已创建的ViewHolder关联*/
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           LayoutInflater layoutInflater = LayoutInflater.from(getActivity());//菜鸟教程里有关于LayoutInflater

            return new CrimeHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);//数组里面int参数原来是索引，也就是CrimeLab类里面得到相应ID的方法
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
