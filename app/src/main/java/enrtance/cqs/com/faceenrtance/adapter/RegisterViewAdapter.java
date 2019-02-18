package enrtance.cqs.com.faceenrtance.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guo.android_extend.widget.ExtImageView;

import java.util.List;
import enrtance.cqs.com.faceenrtance.R;
import enrtance.cqs.com.faceenrtance.bean.FaceRegistBean;

public class RegisterViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLInflater;
    List<FaceRegistBean> registerlist;

    public RegisterViewAdapter(Context c ,List<FaceRegistBean> list) {
        // TODO Auto-generated constructor stub
        mContext = c;
        this.registerlist = list;
        mLInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return registerlist.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (registerlist != null && registerlist.size() > 0) {
            FaceRegistBean bean = registerlist.get(position);
            ViewHolder holder = null;

            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = mLInflater.inflate(R.layout.item_sample, null);
                holder = new ViewHolder();

                holder.registeImg = (ExtImageView) convertView.findViewById(R.id.imageView1);
                holder.registerName = (TextView) convertView.findViewById(R.id.textView1);
                convertView.setTag(holder);
            }
            holder.registerName.setText(bean.mName);

            convertView.setWillNotDraw(false);
//        if (!((App)mContext.getApplicationContext()).mFaceDB.mRegister.isEmpty()) {
////            FaceRegistBean face = ((App) mContext.getApplicationContext()).mFaceDB.mRegister.get(position);
////            convertView.setWillNotDraw(false);
////        }
        }
            return convertView;
        }

        public static class ViewHolder {
            public ExtImageView registeImg;
            public TextView registerName;
        }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Log.d("onItemClick", "onItemClick = " + position + "pos=" + mHListView.getScroll());
//        final String name = ((App)mContext.getApplicationContext()).mFaceDB.mRegister.get(position).mName;
//        final int count = ((App)mContext.getApplicationContext()).mFaceDB.mRegister.get(position).mFaceList.size();
//        new AlertDialog.Builder(RegisterActivity.this)
//                .setTitle("删除注册名:" + name)
//                .setMessage("包含:" + count + "个注册人脸特征信息")
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ((App)mContext.getApplicationContext()).mFaceDB.delete(name);
//                        mRegisterViewAdapter.notifyDataSetChanged();
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .show();
//    }
}