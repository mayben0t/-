package rabbit.a;


import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String namec;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamec() {
        return namec;
    }

    public void setNamec(String name) {
        this.namec = name;
    }
}
