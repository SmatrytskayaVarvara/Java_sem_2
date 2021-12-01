package laba5;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ContactRunner {
    private static SessionFactory sessionFactory;
    private static boolean isExit = false;
    private static final Scanner scanner = new Scanner(System.in);

    public static void setSessionFactory(SessionFactory sessionFactory) {
        ContactRunner.sessionFactory = sessionFactory;
    }


    public void mainMenu() throws IOException {
        System.out.println("1 - add");
        System.out.println("2 - edit");
        System.out.println("3 - delete");
        System.out.println("4 - show all");
        System.out.println("5 - exit");

        int a = scanner.nextInt();
        switch (a) {
            case 1:
                addContact();
                break;
            case 2:
                editContact();
                break;
            case 3:
                deleteContact();
                break;
            case 4:
                List contactsList = getAllContacts();
                for (Object contact : contactsList)
                    System.out.println(contact);
                break;
            case 5:
                isExit = true;
                break;
        }

    }


    public void start() throws IOException {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
        while (!isExit){
            mainMenu();
        }
    }


    public Contact createContact(){
        System.out.println("Input firstname");
        String first_name = scanner.next();

        System.out.println("Input lastname");
        String last_name = scanner.next();

        System.out.println("Input address");
        String address = scanner.next();

        System.out.println("Input telephone");
        String telephone = scanner.next();

        System.out.println("Input email");
        String email = scanner.next();

        return new Contact(first_name, last_name, address, telephone, email);
    }


    public void addContact(){

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Contact contact = createContact();

        session.save(contact);
        transaction.commit();
        session.close();
    }

    public void deleteContact(){
        System.out.println("1 - delete by id");
        System.out.println("2 - delete by first_name");
        System.out.println("3 - delete by last_name");
        int a = scanner.nextInt();
        switch (a){
            case 1:
                deleteById();
                break;
            case 2:
                deleteByFirstName();
                break;
            case 3:
                deleteByLastName();
                break;
        }
    }

    public void deleteById(){
        System.out.println("Input id");
        int id = scanner.nextInt();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createCriteria(Contact.class).add(Restrictions.eq("contact_id", id)).list().forEach(session::delete);// здест пример работы с критериями

        transaction.commit();
        session.close();

    }

    public void deleteByFirstName(){
        System.out.println("Input firstname");
        String name = scanner.next();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createCriteria(Contact.class).add(Restrictions.eq("first_name", name)).list().forEach(session::delete);

        transaction.commit();
        session.close();
    }

    public void deleteByLastName(){
        System.out.println("Input lastname");
        String name = scanner.next();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createCriteria(Contact.class).add(Restrictions.eq("last_name", name)).list().forEach(session::delete);

        transaction.commit();
        session.close();
    }

    public void editContact(){
        System.out.println("1 - edit by id");
        System.out.println("2 - edit by firstname");
        System.out.println("3 - edit by lastname");
        int a = scanner.nextInt();
        switch (a){
            case 1:
                editById();
                break;
            case 2:
                editByFirstName();
                break;
            case 3:
                editByLastName();
                break;
        }
    }

    public void editById(){
        System.out.println("Input id");
        int contactId = scanner.nextInt();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Contact contact = createContact();
        Query query = session.createQuery("UPDATE Contact SET first_name = :first_name, last_name = :last_name, address = :address, telephone = :telephone, email = :email WHERE contact_id = :contactId");
        query.setParameter("first_name", contact.getFirst_name());
        query.setParameter("last_name", contact.getLast_name());
        query.setParameter("address", contact.getAddress());
        query.setParameter("telephone", contact.getTelephone());
        query.setParameter("email", contact.getEmail());
        query.setParameter("contactId", contactId);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void editByFirstName(){
        System.out.println("Input name");
        String fname = scanner.next();

        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Contact contact = createContact();
        Query query = session.createQuery("UPDATE Contact SET first_name = :first_name, last_name = :last_name, address = :address, telephone = :telephone, email = :email WHERE first_name = :fname");
        query.setParameter("first_name", contact.getFirst_name());
        query.setParameter("last_name", contact.getLast_name());
        query.setParameter("address", contact.getAddress());
        query.setParameter("telephone", contact.getTelephone());
        query.setParameter("email", contact.getEmail());
        query.setParameter("fname", fname);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void editByLastName(){
        System.out.println("Input lastname");
        String lname = scanner.next();

        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Contact contact = createContact();
        Query query = session.createQuery("UPDATE Contact SET first_name = :first_name, last_name = :last_name, address = :address, telephone = :telephone, email = :email WHERE last_name = :lname");
        query.setParameter("first_name", contact.getFirst_name());
        query.setParameter("last_name", contact.getLast_name());
        query.setParameter("address", contact.getAddress());
        query.setParameter("telephone", contact.getTelephone());
        query.setParameter("email", contact.getEmail());
        query.setParameter("lname", lname);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public List getAllContacts(){
        Session session = sessionFactory.openSession();
        Transaction transaction =  session.beginTransaction();
        List contactsList = session.createQuery("FROM Contact").list();

        transaction.commit();
        session.close();

        return contactsList;

    }

}

