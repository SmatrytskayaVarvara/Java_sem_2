package laba5;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name="address_book")
public class Contact {
    private int contact_id;
    private String first_name;
    private String last_name;
    private String address;
    private String telephone;
    private String email;


    public Contact(String first_name, String last_name, String address, String telephone, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
    }

    public Contact(){

    }

    @Id
    @Column(name = "contact_id", nullable = false)
    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 50)
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 50)
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Basic
    @Column(name = "address", nullable = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "telephone", nullable = true)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "email", nullable = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return contact_id == contact.contact_id &&
                Objects.equals(first_name, contact.first_name) &&
                Objects.equals(last_name, contact.last_name) &&
                Objects.equals(address, contact.address) &&
                Objects.equals(telephone, contact.telephone) &&
                Objects.equals(email, contact.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contact_id, first_name, last_name, address, telephone, email);
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "contact_id=" + contact_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", address=" + address +
                ", telephone=" + telephone +
                ", email=" + email +
                '}';
    }

}
