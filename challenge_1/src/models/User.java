package models;

public class User {
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String avator;
    private String job;

    public User(String id, String first_name, String last_name, String email, String avator) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.avator = avator;
    }

    public String getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvator() {
        return avator;
    }

    public String getJob() { return job; }

    public void setJob(String job) { this.job = job; }
}
