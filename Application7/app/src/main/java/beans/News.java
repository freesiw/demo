package beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/22.
 */
public class News implements Parcelable {
    /**
     * img:    图片URL地址
     * fromUrl: 新闻来源页
     * title:  标题
     * desc:   描述
     * rcount: 阅读数
     * time:   更新时间
     */
    private String img;
    private String title;
    private String desc;
    private String rcount;
    private String time;

    private String fromUrl;

    public News(String title,String time){
        this.title = title;
        this.time = time;

    }

    public News() {

    }

    protected News(Parcel in) {
        img = in.readString();
        title = in.readString();
        desc = in.readString();
        rcount = in.readString();
        time = in.readString();
        fromUrl = in.readString();

    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRcount() {
        return rcount;
    }

    public void setRcount(String rcount) {
        this.rcount = rcount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(rcount);
        dest.writeString(time);
        dest.writeString(fromUrl);
    }

    @Override
    public String toString() {
        return "News{" +
                "img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", rcount='" + rcount + '\'' +
                ", time='" + time + '\'' +
                ", fromUrl='" + fromUrl + '\'' +
                '}';
    }
}
