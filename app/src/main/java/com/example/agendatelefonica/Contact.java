package com.example.agendatelefonica;

import java.io.Serializable;

public class Contact implements Serializable {
    Integer contactId;
    String contactNume;
    String contactEmail;
    String numartelefon1;
    String numartelefon2;
    String ziNastere;

    public Contact() {
    }

    public Contact(Integer contactId, String contactNume, String contactEmail, String numartelefon1, String numartelefon2,String ziNastere) {
        this.contactId = contactId;
        this.contactNume = contactNume;
        this.contactEmail = contactEmail;
        this.numartelefon1 = numartelefon1;
        this.numartelefon2 = numartelefon2;
        this.ziNastere=ziNastere;
    }

    public Contact(String contactNume, String contactEmail, String numartelefon1, String numartelefon2,String ziNastere) {
        this.contactNume = contactNume;
        this.contactEmail = contactEmail;
        this.numartelefon1 = numartelefon1;
        this.numartelefon2 = numartelefon2;
        this.ziNastere=ziNastere;
    }

    @Override
    public String toString() {
        return contactNume;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getContactNume() {
        return contactNume;
    }

    public void setContactNume(String contactNume) {
        this.contactNume = contactNume;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getNumartelefon1() {
        return numartelefon1;
    }

    public void setNumartelefon1(String numartelefon1) {
        this.numartelefon1 = numartelefon1;
    }

    public String getNumartelefon2() {
        return numartelefon2;
    }

    public void setNumartelefon2(String numartelefon2) {
        this.numartelefon2 = numartelefon2;
    }

    public String getZiNastere() {
        return ziNastere;
    }

    public void setZiNastere(String ziNastere) {
        this.ziNastere = ziNastere;
    }
}
