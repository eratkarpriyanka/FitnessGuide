package theleague.android.com.fitnessguide.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Priyanka
 */

@Table(name = "instructions")
public class Instructions extends Model {

    /*// This is the unique id given by the server
    @Column(name = "remote_id", unique = true)
    public long remoteId;*/


    @Column(name = "repeat")
    public String repeat;

    @Column(name = "hold")
    public String hold;

    @Column(name = "complete")
    public String complete;

    @Column(name = "perform")
    public String perform;

    public Instructions() {
        super();
    }

    public Instructions(String complete, String hold, String perform, String repeat) {
        this.complete = complete;
        this.hold = hold;
        this.perform = perform;
        this.repeat = repeat;
    }

    /*public long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(long remoteId) {
        this.remoteId = remoteId;
    }*/

    public String getHold() {
        return hold;
    }

    public void setHold(String hold) {
        this.hold = hold;
    }

    public String getPerform() {
        return perform;
    }

    public void setPerform(String perform) {
        this.perform = perform;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    // Used to return items from another table based on the foreign key
    public List<Instructions> instructions() {
        return getMany(Instructions.class, "instructions");
    }

    public static List<Instructions> getInstructionsList(long id) {
        return new Select().from(Instructions.class).where("Id=?", id).execute();
    }
}
