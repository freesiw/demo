package ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.application7.DetailsActivity;
import com.example.administrator.application7.R;
import com.example.administrator.httplib.HttpHelper;
import com.example.administrator.httplib.Request;
import com.example.administrator.httplib.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.FragBaseAdapter;
import beans.News;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2016/6/22.
 */
public class MyFragment extends Fragment {


    private static final String TAG = "MyFragment";
    private ListView listView;
    private List<News> newsList = new ArrayList<>();
    private int newsId = 0;
    private PtrClassicFrameLayout pcfl;
    private FragBaseAdapter adapter;
    private boolean isBottom;

    private int pageCount = 10;//新闻的页数

    public MyFragment() {
        Log.i(TAG, "new 一个Myfragment");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.frag_info, container, false);
        initView(view);
        newsId = getArguments().getInt("id");
        Log.i(TAG, "onCreateView() returned: id=" + newsId);

        if (bundle != null) {
            //Parcelable[] pl = bundle.getParcelableArray("cache");
            News[] newss = (News[]) bundle.getParcelableArray("cache");
            if (newss != null && newss.length != 0) {
                newsList = Arrays.asList(newss);
                adapter = new FragBaseAdapter(newsList);
                listView.setAdapter(adapter);
            } else {
                getNewsFromNet();//newss为空
            }

        } else {
            getNewsFromNet();//bundle为空
        }


        return view;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.frag_listView);
        pcfl = (PtrClassicFrameLayout) view.findViewById(R.id.frag_tab_layout);

        pcfl.setResistance(1.7f);
        pcfl.setRatioOfHeaderHeightToRefresh(1.2f);
        pcfl.setDurationToClose(200);
        pcfl.setDurationToCloseHeader(1000);
        // default is false
        pcfl.setPullToRefresh(true);
        // default is true
        pcfl.setKeepHeaderWhenRefresh(true);

        pcfl.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //从网络获取news对象
                getNewsFromNet();
            }
        });

        //上拉加载
        final View footer_view = LayoutInflater.from(getActivity()).inflate(R.layout.farg_lv_footer, null);
        final ImageView footer_img = (ImageView) footer_view.findViewById(R.id.footer_img);

        listView.addFooterView(footer_view);
        footer_view.setVisibility(View.GONE);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && isBottom) {
                    footer_view.setVisibility(View.VISIBLE);
                    //加载下一页
                    pageCount = pageCount + 10;
                    getNewsFromNet();
                    RotateAnimation animation = new RotateAnimation(0f, 360f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(3000);
                    animation.setFillAfter(true);
                    animation.setRepeatMode(Animation.RESTART);
                    animation.setRepeatCount(3);
                    footer_img.setAnimation(animation);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = ((firstVisibleItem + visibleItemCount) == totalItemCount);
            }
        });

        //item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News item = newsList.get(position);
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",item.getTitle());
                bundle.putString("desc",item.getDesc());
                bundle.putString("rcount",item.getRcount());
                bundle.putString("time",item.getTime());
                bundle.putString("fromurl",item.getFromUrl());
                intent.putExtra("news", bundle);
                startActivity(intent);
            }
        });

    }

    private void getNewsFromNet() {
        String url = "http://www.tngou.net/api/top/news?id=10&rows=" + pageCount + "&classify=" + newsId;
        StringRequest sr = new StringRequest(url, Request.Method.GET, new Request.Callback<String>() {
            @Override
            public void onEror(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResonse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    List<News> listNews = parseJson2Array(object);
                    Log.d(TAG, "onResonse() returned: listNews.isEmpty =" + listNews.isEmpty());
                    if (listNews != null) {
                        newsList.clear();
                        newsList.addAll(listNews);

                        if (adapter == null) {
                            adapter = new FragBaseAdapter(newsList);
                            //主线程更新UI
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setAdapter(adapter);
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //?
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pcfl.refreshComplete();
                    }
                });
            }
        });
        HttpHelper.addRequest(sr);
    }

    private List<News> parseJson2Array(JSONObject object) throws JSONException {
        List<News> list = new ArrayList<>();
        if (object == null) {
            return null;
        } else {
            JSONArray array = object.getJSONArray("list");
            if (array == null || array.length() == 0) {
                return null;
            } else {
                int len = array.length();
                News news = null;
                JSONObject jo = null;
                for (int i = 0; i < len; i++) {
                    jo = array.getJSONObject(i);
                    news = new News();
                    news.setTitle(jo.optString("title"));
                    news.setDesc(jo.optString("description"));
                    news.setRcount("阅读:" + jo.optString("rcount"));
                    long time = jo.optLong("time");
                    String str = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(time);
                    Log.i(TAG, "parseJson2Array() returned:  time = " + str);
                    news.setTime(str);
                    news.setImg(jo.optString("img"));
                    news.setFromUrl(jo.optString("fromurl"));
                    list.add(news);
                }
                return list;
            }

        }

    }

    /**
     * fragment销毁时调用
     *
     * @param outState 紧急保存的bundle
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (newsList == null || newsList.size() == 0) return;

        News[] parce = new News[newsList.size()];
        newsList.toArray(parce);
        outState.putParcelableArray("cache", parce);

    }


}
