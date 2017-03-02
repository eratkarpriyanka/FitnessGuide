package theleague.android.com.fitnessguide.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Priyanka
 */

@Table(name = "workouts")
public class Workouts extends Model {

    /*// This is the unique id given by the server
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;
*/

    @Column(name = "description")
    public String description;

    @Column(name = "instructions", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public Instructions instructions;

    @Column(name = "img" , onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public Img img;

    @Column(name = "video")
    public String video;

    public Workouts(){
        super();
    }

    public Workouts(String description, Img img, Instructions instructions,  String video) {

        this.description = description;
        this.description = description;
        this.img = img;
        this.instructions = instructions;
      //  this.remoteId = remoteId;
        this.video = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Img getImageInfo() {
        return img;
    }

    public void setImageInfo(Img img) {
        this.img = img;
    }

    public Instructions getInstructions() {
        return instructions;
    }

    public void setInstructions(Instructions instructions) {
        this.instructions = instructions;
    }

    /*public long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(long remoteId) {
        this.remoteId = remoteId;
    }*/

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public static List<Workouts> getAll() {
        // This is how you execute a query
        return new Select()
                .from(Workouts.class)
                .execute();
    }

    public static List<Workouts> getExercisesList(){
        return new Select().from(Workouts.class).execute();
    }

    public static List<Workouts> getWorkoutDetail(long id){
        return new Select().from(Workouts.class).where("Id=?",id).execute();
    }
}
