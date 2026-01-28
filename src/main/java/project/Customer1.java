package project;

public class Customer1 {
    private int id;

    public Customer1() {}

    public Customer1(int id) {
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "Customer ID: " + id;
    }
}
