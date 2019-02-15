package ru.job4j.sockets.bot;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class ClientTest {
    private static final String LS = System.lineSeparator();

    @Test
    public void whenSentByeThenOk() throws IOException {
        String request = new StringBuilder()
                .append("пока")
                .append(LS)
                .toString();
        String response = "";
        doTest(request, response);
    }

    @Test
    public void whenSentSomeAndByeThenGetStringAndOk() throws IOException {
        String request = Joiner.on(LS).join(
                "привет",
                "пока");
        String response = Joiner.on(LS + LS).join(
                "Some string.",
                "");
        doTest(request, response);
    }

    @Test(expected = NullPointerException.class)
    public void whenDoNotSentByeThenExpectStringWithTwoLineSeparators() throws IOException {
        String request = String.format("привет%n");
        String response = String.format("Some string.%nSome string.%nSome string.%n");
        doTest(request, response);
    }

    private void doTest(String request, String response) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(response.getBytes());
        when(socket.getOutputStream()).thenReturn(out);
        when(socket.getInputStream()).thenReturn(in);

        ByteArrayInputStream consoleIn = new ByteArrayInputStream(request.getBytes());
        System.setIn(consoleIn);

        Client client = new Client("0.0.0.0", 0);
        client.start(socket);
//        assertEquals("", ""); ???
    }

}