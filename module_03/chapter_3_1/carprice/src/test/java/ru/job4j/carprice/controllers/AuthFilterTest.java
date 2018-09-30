package ru.job4j.carprice.controllers;

import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AuthFilterTest {

    @Test
    public void authFilterTest() throws IOException, ServletException {
        AuthFilter filter = spy(new AuthFilter());
        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        // sign in URL
        when(request.getRequestURI()).thenReturn("/signin");
        doThrow(new IOException()).when(filterChain).doFilter(request, response);
        try {
            filter.doFilter(request, response, filterChain);
        } catch (IOException e) {
            e.printStackTrace();
        }
        verify(filterChain, times(1)).doFilter(request, response);
        verify(request, never()).getSession();
        // not sign in URL
        when(request.getRequestURI()).thenReturn("/");
        // LoginUser is in the session
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("loginUser")).thenReturn(null);
        try {
            filter.doFilter(request, response, filterChain);
        } catch (IOException e) {
            e.printStackTrace();
        }
        verify(filterChain, times(1)).doFilter(request, response);
        verify(request, times(1)).getSession();
        // LoginUser is not in the session
        when(session.getAttribute("loginUser")).thenReturn(new Object());
        try {
            filter.doFilter(request, response, filterChain);
        } catch (IOException e) {
            e.printStackTrace();
        }
        verify(filterChain, times(2)).doFilter(request, response);
        verify(request, times(2)).getSession();
    }
}