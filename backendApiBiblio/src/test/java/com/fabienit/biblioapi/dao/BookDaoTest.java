package com.fabienit.biblioapi.dao;

import com.fabienit.biblioapi.manager.impl.BookManagerImpl;
import com.fabienit.biblioapi.model.beans.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoTest {



    private Book bookToSave;

    @MockBean
    BookManagerImpl classUnderTest;

    @Autowired
    BookDao bookDao;

    @MockBean
    List<Book> bookList;

    @BeforeEach
    public void init() {
        bookToSave = new Book();
        bookToSave.setId(6);
        bookToSave.setAuthorFirstName("Fabien");
        bookToSave.setAuthorLastName("Chapeau");
        bookToSave.setPictureURL("toto");
        bookToSave.setPublicationDate(LocalDate.of(2022,3,20));
        bookToSave.setSynopsis("toto");
        bookToSave.setTitle("toto2041");

        bookDao.save(bookToSave);
    }


   @Test
    public void testExistsBookByTitle() {

        // WHEN

        // GIVEN
        boolean result = bookDao.existsBookByTitle("toto2041");

        // THEN
       assertTrue(result);

    }

    @Test
    public void testFindByTitleIgnoreCaseContainingOrAuthorLastNameIgnoreCaseContaining(){

        // GIVEN


        // WHEN
        List<Book> listBook = bookDao.findByTitleIgnoreCaseContainingOrAuthorLastNameIgnoreCaseContaining("toto2041", "Chapeau");

        //THEN
        assertEquals("toto2041", listBook.get(0).getTitle());
        assertEquals("Chapeau", listBook.get(0).getAuthorLastName());

    }



/*    @Test

    public void Given_bookList_When_findAll_Then_shouldReturn(){

        // WHEN
        final List<Book> result = bookDao.findAll();

        //THEN
        assertThat(result).asList();
    }

    @Test
    public void Given_book_When_findById_Then_shouldReturnResult(){

        // GIVEN

        // WHEN
        final Optional<Book> result = classUnderTest.findById(1);

        //THEN
        assertThat(result.get().getId()).isEqualTo(1);

    }

    @Test
    public void Given_book_When_deleteById_Then_shouldReturnResult(){

        // GIVEN

        // WHEN
        Book result = classUnderTest.deleteById(1);

        //THEN
        assertThat(result).isEqualTo(null);

    }

    @Test
    public void Given_book_When_save_Then_shouldReturnResult(){

        // GIVEN
        Book book = new Book();

        // WHEN

        book.setAuthorFirstName("Fabien");
        book.setAuthorLastName("Chapeau");
        book.setPictureURL("toto");
        book.setPublicationDate(LocalDate.of(2022,5,20));
        book.setSynopsis("toto");
        book.setTitle("toto");

        Book result = bookDao.save(book);

        //THEN
        assertThat(result).isEqualTo(book);

    }

    @Test
    public void Given_existsBookByTitleAndPublicationDate_When_existsBookByTittleAndPublicationDate_Then_shouldReturnResult(){

        // GIVEN
        boolean result = false;

        // WHEN
        if(bookDao.existsBookByTitle("1984")) {
            result = true;

            //THEN
            assertThat(result).isTrue();
        }else {
            assertThat(result).isTrue();
        }

    }*/

}
