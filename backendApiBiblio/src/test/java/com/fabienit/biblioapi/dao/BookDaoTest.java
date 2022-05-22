package com.fabienit.biblioapi.dao;

import com.fabienit.biblioapi.model.beans.Book;
import com.fabienit.biblioapi.model.beans.RegisteredUser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class BookDaoTest {

    @Autowired
    BookDao bookDao;

    @Autowired
    RegisteredUserDao registeredUserDao;

    @Test
    void testSaveBook() {
        // unit parameter Book
        Book myBook = new Book();
        myBook.setAuthorFirstName("Jean");
        myBook.setAuthorLastName("Jean2");
        myBook.setPublicationDate(LocalDate.parse("1949-06-08"));
        myBook.setPictureURL("/covers/la_prophetie_des_abeilles.jpg");
        myBook.setSynopsis("il etait une fois...");
        myBook.setTitle("JeanJean");
        //call function with parameter
        Book responseBook = bookDao.save(myBook);


        //stock la réponse dans une variable


        //verifier avec assert
        Assert.assertEquals("Jean", responseBook.getAuthorFirstName());
        Assert.assertEquals("Jean2", responseBook.getAuthorLastName());
        Assert.assertEquals("il etait une fois...", responseBook.getSynopsis());
        Assert.assertEquals("JeanJean", responseBook.getTitle());
    }

    @Test
    void testFindBookByEmail() {
        RegisteredUser registeredUser = this.registeredUserDao.findByEmail("fabien@gmail.com");
        Assert.assertNotNull(registeredUser);
    }

    @Test
    void testDeleteByEmail(){
        RegisteredUser registeredUser = this.registeredUserDao.findByEmail("fabien@gmail.com");
        this.registeredUserDao.delete(registeredUser);
        Assert.assertNotNull(registeredUser);
    }
}
