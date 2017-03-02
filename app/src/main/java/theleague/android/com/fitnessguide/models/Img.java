package theleague.android.com.fitnessguide.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Priyanka
 */

@Table(name="img")
public class Img extends Model{

    /*/// This is the unique id given by the server
    @Column(name = "remote_id", unique = true)
    public long remoteId;*/

    public Img() {
        super();
    }

    public Img(String height, String src) {
        this.height = height;
        this.src = src;
    }

   /* public long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(long remoteId) {
        this.remoteId = remoteId;
    }*/

    @Column(name = "src")
    public String src;

    @Column(name = "height")
    public String height;
    // Make sure to have a default constructor for every ActiveAndroid model


    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    // Used to return items from another table based on the foreign key
    public List<Img> images() {
        return getMany(Img.class, "img");
    }

    public static List<Img> getImageList(long id){
        return new Select().from(Img.class).where("Id=?",id).execute();
    }
}
