package com.fabienit.biblioapi.manager.impl.unit;


import com.fabienit.biblioapi.manager.impl.UtilsManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UtilsManagerImplTest {

    private UtilsManagerImpl classUnderTest;

    /**
     *
     */
    @BeforeEach
    public void init() {
        classUnderTest = new UtilsManagerImpl();
    }

    /**
     *
     */
    public void undef() {
        classUnderTest  = null;
    }

    @Test
    public void Given_queryPommeOrange_When_splitQueryString_Then_shouldReturnPommeOnIndex0() {
        // GIVEN

        // WHEN
        final String[] result = classUnderTest.splitQueryString("pomme orange");
        // THEN
        assertThat(result[0]).isEqualTo("pomme");
    }

    @Test
    public void Given_queryPommeOrange_When_splitQueryString_Then_shouldReturnOrangeOnIndex1() {
        // GIVEN

        // WHEN
        final String[] result = classUnderTest.splitQueryString("pomme orange");
        // THEN
        assertThat(result[1]).isEqualTo("orange");
    }
}
