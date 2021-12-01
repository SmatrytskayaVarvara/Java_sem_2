package Client;

public class Contact {
    private String contact_id;
    private String first_name;
    private String last_name;
    private String address;
    private String telephone;
    private String email;

    public Contact(String contact_id, String first_name, String last_name, String address, String telephone, String email) {
        this.contact_id = contact_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
    }

    public Contact(){};

    public String toString(){
        return contact_id + "/" + first_name + "/" + last_name
                + "/" + address + "/" + telephone + "/" + email+"/";
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
