package com.example.exam03;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * Created by acer on 2017/11/30.
 */

class MyAdapter extends RecyclerView.Adapter <MyAdapter.IViewHolder>{
    Context context;
    List<DataDataBean.ResultBean.DataBean> list;
    public MyAdapter(Context context) {
        this.context=context;
    }

    @Override
    public MyAdapter.IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.adapter,null);
        IViewHolder holder=new IViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyAdapter.IViewHolder holder, int position) {
         holder.mtitle.setText(list.get(position).getTitle());
        ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s(),holder.mpic);
        holder.itemView.setTag(position);//当前位置成标记
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void add(List<DataDataBean.ResultBean.DataBean> list) {
        this.list=list;
    }

    class IViewHolder extends RecyclerView.ViewHolder{


        private final ImageView mpic;
        private final TextView mtitle;

        public IViewHolder(View itemView) {
            super(itemView);
            mpic = (ImageView) itemView.findViewById(R.id.mpic);
            mtitle = (TextView) itemView.findViewById(R.id.mtitle);
        }
    }
    onItemClickListener onItemClickListener=null;
    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    //定义的接口
    public interface onItemClickListener{
        public void onItemClick(View view,int position);
    }
}
