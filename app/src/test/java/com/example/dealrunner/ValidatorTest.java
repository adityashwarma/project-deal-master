package com.example.dealrunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import utilities.Validator;

public class ValidatorTest {

    @Test
    public void testEmailValidation(){
        assertTrue(Validator.validateEmail("dummy@test.com"));
        assertFalse("Incorrect Email format", Validator.validateEmail("dummy@test"));
        assertFalse("Incorrect Email format", Validator.validateEmail("dummy"));
        assertFalse("Incorrect Email format", Validator.validateEmail(""));
        assertFalse("Incorrect Email format", Validator.validateEmail("dummy@@test.com"));
    }

    @Test
    public void testFirstLastNameValidation(){
        assertTrue(Validator.validateFirstName("Deal"));
        assertFalse("Incorrect first name", Validator.validateFirstName("Deal123"));
        assertFalse("Incorrect first name", Validator.validateFirstName("123Deal"));
        assertFalse("Incorrect first name", Validator.validateFirstName("123"));
        assertFalse("Incorrect first name", Validator.validateFirstName("de@l"));
        assertFalse("Incorrect first name", Validator.validateFirstName(""));

        assertTrue(Validator.validateLastName("Runner"));
        assertFalse("Incorrect Last name", Validator.validateLastName("Runner123"));
        assertFalse("Incorrect Last name", Validator.validateLastName("123Runner"));
        assertFalse("Incorrect Last name", Validator.validateLastName("123"));
        assertFalse("Incorrect Last name", Validator.validateLastName("Runnér"));
        assertFalse("Incorrect Last name", Validator.validateLastName(""));
    }

    @Test
    public void testConfirmPasswordValidation(){
        assertTrue("Password do not match or Password too short", Validator.validateConfirmPassword("Deal1234", "Deal1234"));
        assertFalse("Passwords do not match", Validator.validateConfirmPassword("Deal1234", "deal1234"));
        assertFalse("Passwords do not match", Validator.validateConfirmPassword("Deal1234", "deal1234 "));
    }
}
