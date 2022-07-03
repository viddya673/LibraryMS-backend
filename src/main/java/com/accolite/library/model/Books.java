package com.accolite.library.model;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EnableJpaRepositories
@Entity
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int bid;
    private String bname;
    private String author;
    private String publisher;
    private int noOfCopies;


    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(int noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public Books() {
    }

    public Books(int bid, String bname, String author, String publisher, int noOfCopies) {
        this.bid = bid;
        this.bname = bname;
        this.author = author;
        this.publisher = publisher;
        this.noOfCopies = noOfCopies;
    }

//    @Override
//    public String toString() {
//        return "Books{" +
//                "bid=" + bid +
//                ", bname='" + bname + '\'' +
//                ", author='" + author + '\'' +
//                ", publisher='" + publisher + '\'' +
//                ", noOfCopies=" + noOfCopies +
//                '}';
//    }
}
