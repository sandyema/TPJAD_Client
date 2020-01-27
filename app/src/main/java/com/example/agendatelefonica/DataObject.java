package com.example.agendatelefonica;

import java.io.Serializable;

public class DataObject implements Serializable

    {
        Integer contactId;
        String contactNume;
       String poza;

    public DataObject() {
    }

    public DataObject(Integer contactId, String contactNume, String poza) {
        this.contactId = contactId;
        this.contactNume = contactNume;
        this.poza = poza;

    }

    public DataObject(String contactNume, String poza) {
        this.contactNume = contactNume;
        this.poza = poza;

    }

        public DataObject(Integer contactId, String contactNume) {
            this.contactId = contactId;
            this.contactNume = contactNume;
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

        public String getPoza() {
            return poza;
        }

        public void setPoza(String poza) {
            this.poza = poza;
        }
    }
