package enrtance.cqs.com.faceenrtance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enrtance.cqs.com.faceenrtance.R;
import enrtance.cqs.com.faceenrtance.bean.Order;

public class OrderListAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orderList;

    public OrderListAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Order order = orderList.get(position);
        if (order == null){
            return null;
        }

        ViewHolder holder = null;
        if (view != null){
            holder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.show_sql_item, null);
            holder = new ViewHolder();

            holder.dateIdTextView = (TextView) view.findViewById(R.id.dateIdTextView);
            holder.dateCustomTextView = (TextView) view.findViewById(R.id.dateCustomTextView);
            holder.dateOrderPriceTextView = (TextView) view.findViewById(R.id.dateOrderPriceTextView);
            holder.dateCountoryTextView = (TextView) view.findViewById(R.id.dateCountoryTextView);
            holder.imgUrl = (TextView) view.findViewById(R.id.imgUrl);
            view.setTag(holder);
        }

        holder.dateIdTextView.setText(order.Id + "");
        holder.dateCustomTextView.setText(order.Name);
        holder.dateOrderPriceTextView.setText(order.Sex+ "");
        holder.dateCountoryTextView.setText(order.FaceEigenvalue);
        holder.imgUrl.setText(order.FaceImageUrl);
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public static class ViewHolder{
        public TextView dateIdTextView;
        public TextView dateCustomTextView;
        public TextView dateOrderPriceTextView;
        public TextView dateCountoryTextView;
        public TextView imgUrl;
    }
}

