package com.hellorin.stickyMoss.companies.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by hellorin on 25.06.17.
 */
@RunWith(JUnit4.class)
public class CompanyTest {

    @Test(expected = NullPointerException.class)
    public void testConstructorNullName() {
        new Company(null, CompanySize.UNKNOWN);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullCompanySize() {
        new Company("Sony", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEmptyName() {
        new Company("", CompanySize.UNKNOWN);
    }

    @Test
    public void testConstructor() {
        final String company1name = "IBM";
        final Company company1 = new Company(company1name, CompanySize.UNKNOWN);

        final String company2name = "Google";
        final Company company2 = new Company(company2name, CompanySize.SMALL);

        assertNotNull(company1);
        assertNull(company1.getId());
        assertEquals(company1name, company1.getName());
        assertEquals(CompanySize.UNKNOWN, company1.getCompanySize());

        assertNotNull(company2);
        assertNull(company2.getId());
        assertEquals(company2name, company2.getName());
        assertEquals(CompanySize.SMALL, company2.getCompanySize());
    }

    @Test
    public void testEquals() {
        final Company company1 = new Company("IBM", CompanySize.UNKNOWN);
        final Company company2 = new Company("IBM", CompanySize.UNKNOWN);
        final Company company3 = new Company("IBM", CompanySize.UNKNOWN);
        final Company company4 = new Company("IBM", CompanySize.SMALL);
        final Company company5 = new Company("Google", CompanySize.SMALL);

        assertFalse(company1.equals(null));
        assertFalse(company1.equals(false));

        assertTrue(company1.equals(company1));
        assertEquals(company1.hashCode(), company1.hashCode());

        assertTrue(company1.equals(company2));
        assertTrue(company2.equals(company3));
        assertEquals(company1.hashCode(), company3.hashCode());

        assertFalse(company1.equals(company4));
        assertNotEquals(company1.hashCode(), company4.hashCode());
        assertFalse(company4.equals(company5));
        assertNotEquals(company4.hashCode(), company5.hashCode());
    }

}
