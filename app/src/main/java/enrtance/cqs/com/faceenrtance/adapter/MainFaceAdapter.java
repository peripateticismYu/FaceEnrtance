package enrtance.cqs.com.faceenrtance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enrtance.cqs.com.faceenrtance.R;
import enrtance.cqs.com.faceenrtance.bean.FaceRegistBean;
import enrtance.cqs.com.faceenrtance.bean.Order;

public class MainFaceAdapter extends BaseAdapter {
    private Context context;
    List<FaceRegistBean> registerlist;

    public MainFaceAdapter(Context context, List<FaceRegistBean> orderList) {
        this.context = context;
        this.registerlist = orderList;
    }

    @Override
    public int getCount() {
        return registerlist.size();
    }

    @Override
    public Object getItem(int position) {
        return registerlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        FaceRegistBean bean = registerlist.get(position);
        if (bean == null){
            return null;
        }

       ViewHolder holder = null;
        if (view != null){
            holder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_mainface, null);
            holder = new ViewHolder();

            holder.tv_id = view.findViewById(R.id.tv_id);
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_listSize = view.findViewById(R.id.tv_listSize);
            view.setTag(holder);
        }

       holder.tv_id.setText(position+"");
       holder.tv_name.setText(bean.mName);
      // holder.tv_listSize.setText(bean.mFaceList.size()+"");

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public static class ViewHolder{
        TextView tv_id;
        TextView tv_name;
        TextView tv_listSize;
    }




}
