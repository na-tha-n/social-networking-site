

public class Profile {

    private String id;
    private String name;
    private boolean status;

    public Profile(String id, String name) {
        this.id = id;
        this.name = name;
        this.status = true;
    }

    public Profile(String id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
    

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String str = "Id: " + id;
        str += "\n\tName: " + name;
        
        str += "\n\tStatus: " + (status ? "Online" : "Offline");
        str += "\n\tFriends Id: ";
        return str;
    }

    @Override
    public boolean equals(Object obj) {
        Profile p = (Profile)obj;
        return id.equals(p.getId());
    }
    

}
