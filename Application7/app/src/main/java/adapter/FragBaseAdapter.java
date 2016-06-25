package adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.application7.R;
import com.example.administrator.httplib.BitmapRequest;
import com.example.administrator.httplib.HttpHelper;
import com.example.administrator.httplib.Request;

import java.util.ArrayList;
import java.util.List;

import beans.News;

public class FragBaseAdapter extends BaseAdapter {

    private List<News> newsList = new ArrayList<>();

    public FragBaseAdapter (List<News> list){
        this.newsList = list;
    }


    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public News getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null){
            vh = new ViewHolder();

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_lv_item,null);
            vh.item_img = (ImageView) convertView.findViewById(R.id.item_img);
            vh.item_tv_title = (TextView) convertView.findViewById(R.id.item_tv_title);
            vh.item_tv_desc = (TextView) convertView.findViewById(R.id.item_tv_desc);
            vh.item_tv_rcount = (TextView) convertView.findViewById(R.id.item_tv_rcount);
            vh.item_tv_time = (TextView) convertView.findViewById(R.id.item_tv_time);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        News news = newsList.get(position);
        //文字的设置
        vh.item_tv_title.setText(news.getTitle());
        vh.item_tv_desc.setText(news.getDesc());
        vh.item_tv_rcount.setText(news.getRcount());
        vh.item_tv_time.setText(news.getTime());
        //图片的设置
        downLoadImgFromNet(vh.item_img,"http://tnfs.tngou.net/image"+news.getImg()+"_100x100");


        return convertView;
    }

    private void downLoadImgFromNet(final ImageView iv, final String url) {
        iv.setTag(url);
        BitmapRequest br = new BitmapRequest(url, Request.Method.GET, new Request.Callback<Bitmap>() {
            @Override
            public void onEror(Exception e) {
                e.printStackTrace();
            }


            @Override
            public void onResonse(final Bitmap response) {
                if (iv !=null && response != null && (iv.getTag().toString()).equals(url)){

                    new Handler(Looper.getMainLooper()).post(new Runnable() {//?
                        @Override
                        public void run() {
                            iv.setImageBitmap(response);

                        }
                    });
                }
            }
        });
        HttpHelper.addRequest(br);//?

    }

    class ViewHolder{
        ImageView item_img;
        TextView item_tv_title;
        TextView item_tv_desc;
        TextView item_tv_rcount;
        TextView item_tv_time;

    }
}
