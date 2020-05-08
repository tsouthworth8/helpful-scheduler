package com.techelevator.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import com.techelevator.model.UserDao;
import com.techelevator.pojo.Users;

import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * RequestAuthProviderTest
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RequestAuthProviderTest {
    private RequestAuthProvider sut;
    private HttpServletRequest mockedRequest;
    private UserDao mockedDao;

    @Before
    public void before() {
        mockedRequest = mock(HttpServletRequest.class);
        mockedDao = mock(UserDao.class);
        sut = new RequestAuthProvider(mockedRequest, mockedDao);
    }

    @Test
    public void testLogOut() {
        sut.logOff();

        verify(mockedRequest).removeAttribute(RequestAuthProvider.USER_KEY);
    }

    @Test
    public void isLoggedInSuccessTest() {
        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(new Users());

        assertTrue(sut.isLoggedIn());
    }

    @Test
    public void isLoggedInFailTest() {
        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(null);

        assertFalse(sut.isLoggedIn());
    }

    @Test
    public void getCurrentUserWithUserTest() {
        Users mockedUser = new Users();
        mockedUser.setId(0);
        mockedUser.setUsername("TEST");

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(mockedUser);

        Users fromSut = sut.getCurrentUser();

        assertEquals(mockedUser.getId(), fromSut.getId());
        assertEquals(mockedUser.getUsername(), fromSut.getUsername());
    }

    @Test
    public void getCurrentUserWithNullTest() {
        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(null);

        Users fromSut = sut.getCurrentUser();

        assertNull(fromSut);
    }

    @Test
    public void registerTest() {
        sut.register("TEST", "TESTEMAIL", "TESTPASS", true);

        verify(mockedDao).saveUser("TEST", "TESTEMAIL", "TESTPASS", true);
    }

    @Test
    public void signInSuccessTest() {
        Users testUser = new Users();
        testUser.setId(0);
        testUser.setUsername("TEST");
        when(mockedDao.getValidUserWithPassword("TEST", "TEST")).thenReturn(testUser);

        assertTrue(sut.signIn("TEST", "TEST"));
        verify(mockedRequest).setAttribute(RequestAuthProvider.USER_KEY, testUser);
    }

    @Test
    public void signInFailTest() {
        when(mockedDao.getValidUserWithPassword("TEST", "TEST")).thenReturn(null);

        assertFalse(sut.signIn("TEST", "TEST"));
    }

    @Test
    public void changePasswordSuccessTest() {
        Users testUser = new Users();
        testUser.setId(0);
        testUser.setUsername("TEST");

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);
        when(mockedDao.getValidUserWithPassword("TEST", "TEST")).thenReturn(testUser);

        assertTrue(sut.changePassword("TEST", "NEWVALUE"));
        verify(mockedDao).changePassword(testUser, "NEWVALUE");
    }

    @Test
    public void changePasswordBadPasswordTest() {
        Users testUser = new Users();
        testUser.setId(0);
        testUser.setUsername("TEST");

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);
        when(mockedDao.getValidUserWithPassword("TEST", "TEST")).thenReturn(null);

        assertFalse(sut.changePassword("TEST", "NEWVALUE"));
        verify(mockedDao, times(0)).changePassword(testUser, "NEWVALUE");
    }

    @Test
    public void changePasswordNoOneLoggedInTest() {
        Users testUser = new Users();
        testUser.setId(0);
        testUser.setUsername("TEST");

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(null);
        when(mockedDao.getValidUserWithPassword("TEST", "TEST")).thenReturn(testUser);

        assertFalse(sut.changePassword("TEST", "NEWVALUE"));
        verify(mockedDao, times(0)).changePassword(testUser, "NEWVALUE");
    }

    @Test
    public void hasRoleSuccessTest() {
        Users testUser = new Users();
        testUser.setId(0);
        testUser.setUsername("TEST");
        testUser.setManager(true);

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertTrue(sut.userHasRole(new String[] { "user" }));
    }

    @Test
    public void hasRoleFailTest() {
        Users testUser = new Users();
        testUser.setId(0);
        testUser.setUsername("TEST");
        testUser.setManager(true);

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertFalse(sut.userHasRole(new String[] { "admin" }));
    }

    @Test
    public void hasRoleMultipleSuccessTest() {
        Users testUser = new Users();
        testUser.setId(0);
        testUser.setUsername("TEST");
        testUser.setManager(true);

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertTrue(sut.userHasRole(new String[] { "admin", "user", "editor" }));
    }

    @Test
    public void hasRoleMultipleFailTest() {
        Users testUser = new Users();
        testUser.setId(0);
        testUser.setUsername("TEST");
        testUser.setManager(true);

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertFalse(sut.userHasRole(new String[] { "admin", "manager", "editor" }));
    }

    @Test
    public void hasRoleNullTest() {
        Users testUser = new Users();
        testUser.setId(0);
        testUser.setUsername("TEST");
        testUser.setManager(true);

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertFalse(sut.userHasRole(null));
    }

    @Test
    public void hasRoleEmptyTest() {
        Users testUser = new Users();
        testUser.setId(0);
        testUser.setUsername("TEST");
        testUser.setManager(true);

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertFalse(sut.userHasRole(new String[] {}));
    }
}