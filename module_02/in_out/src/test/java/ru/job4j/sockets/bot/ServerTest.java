package ru.job4j.sockets.bot;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerTest {
    private static final String LS = System.lineSeparator();

    @Test
    public void whenAskByeThenShutdown() throws IOException {
        doTest("пока", "");
    }

    @Test
    public void whenAskHelloThenGreeting() throws IOException {
        String request = new StringBuilder()
                .append("привет")
                .append(LS)
                .append("пока")
                .toString();
        String response = new StringBuilder()
                .append("Hello, dear friend, I'm a oracle.")
                .append(LS)
                .append(LS)
                .toString();
        doTest(request, response);
    }

    @Test
    public void whenAskSomethingElseThenStandardResponseGuava() throws IOException {
        String request = Joiner.on(LS).join(
                "что-то спросить",
                "уточнить",
                "пока");
        String response = Joiner.on(LS + LS).join(
                "Bla, bla, bla.",
                "Bla, bla, bla.",
                "");
        doTest(request, response);
    }

    @Test
    public void whenAskSomethingElseThenStandardResponseFormat() throws IOException {
        String request = String.format("что-то спросить%nуточнить%nпока");
        String response = String.format("Bla, bla, bla.%n%nBla, bla, bla.%n%n");
        doTest(request, response);
    }

    private void doTest(String request, String response) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(request.getBytes());
        when(socket.getOutputStream()).thenReturn(out);
        when(socket.getInputStream()).thenReturn(in);
        Server server = new Server(0);
        server.start(socket);
        assertEquals(out.toString(), response);
    }

}