package com.fabienit.biblioapi.dao;

import com.fabienit.biblioapi.model.beans.Borrow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "/data.sql")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BorrowDaoIntegrationTest {

    @Autowired
    private BorrowDao classUnderTest;

    @Test
    public void Given_1BorrowRecordedInDatabase_When_findAllByBookIdAndLibraryId_Then_borrowListSizeIs1() {
        // GIVEN

        // WHEN
        final List<Borrow> result = classUnderTest.findAllByBookIdAndLibraryId(1,1);
        // THEN
        assertThat(result.size()).isEqualTo(1);
    }
}
