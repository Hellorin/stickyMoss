package com.hellorin.stickyMoss.companies.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by hellorin on 25.06.17.
 */
@RunWith(JUnit4.class)
public class ServicesCompanyTest {

    @Test(expected = NullPointerException.class)
    public void testConstructorNullName() {
        new ServicesCompany(null, CompanySize.UNKNOWN);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullCompanySize() {
        new ServicesCompany("Sony", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEmptyName() {
        new ServicesCompany("", CompanySize.UNKNOWN);
    }

    @Test
    public void testConstructor() {
        final String company1name = "IBM";
        final ServicesCompany company1 = new ServicesCompany(company1name, CompanySize.UNKNOWN);

        final String company2name = "Google";
        final ServicesCompany company2 = new ServicesCompany(company2name, CompanySize.SMALL);

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
        final ServicesCompany company1 = new ServicesCompany("IBM", CompanySize.UNKNOWN);
        final ServicesCompany company2 = new ServicesCompany("IBM", CompanySize.UNKNOWN);
        final ServicesCompany company3 = new ServicesCompany("IBM", CompanySize.UNKNOWN);
        final ServicesCompany company4 = new ServicesCompany("IBM", CompanySize.SMALL);
        final ServicesCompany company5 = new ServicesCompany("Google", CompanySize.SMALL);

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
