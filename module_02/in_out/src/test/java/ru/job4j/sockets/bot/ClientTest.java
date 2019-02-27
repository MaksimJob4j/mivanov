package ru.job4j.sockets.bot;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ClientTest {
    private static final String LS = System.lineSeparator();

    @Test
    public void whenSentByeThenOk() throws IOException {
        String consoleIn = new StringBuilder()
                .append("пока")
                .append(LS)
                .toString();
        String response = "";
        doTest(consoleIn, response);
    }

    @Test
    public void whenSentSomeAndByeThenGetStringAndOk() throws IOException {
        String consoleIn = Joiner.on(LS).join(
                "привет",
                "пока");
        String response = Joiner.on(LS + LS).join(
                "Some string.",
                "");
        doTest(consoleIn, response);
    }

    @Test
    public void whenSentTwoSomeAndByeThenGetTwoStringAndOk() throws IOException {
        String consoleIn = Joiner.on(LS).join(
                "привет",
                "как дела",
                "пока");
        String response = new StringBuilder()
                .append("Some string.")
                .append(LS)
                .append(LS)
                .append("First string from second mes.")
                .append(LS)
                .append("Second string from second mes.")
                .append(LS)
                .append(LS)
                .toString();
        doTest(consoleIn, response);
    }

    @Test(expected = NullPointerException.class)
    public void whenDoNotSentByeThenExpectStringWithTwoLineSeparators() throws IOException {
        String consoleIn = String.format("привет%n");
        String response = String.format("Some string.%nSome string.%nSome string.%n");
        doTest(consoleIn, response);
    }

    private void doTest(String consoleIn, String response) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(response.getBytes());
        when(socket.getOutputStream()).thenReturn(out);
        when(socket.getInputStream()).thenReturn(in);

        InputStream systemIn = System.in;
        PrintStream systemOut = System.out;

        try {
            System.setIn(new ByteArrayInputStream(consoleIn.getBytes()));
            ByteArrayOutputStream testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
            Client client = new Client("0.0.0.0", 0);
            client.start(socket);
            String consoleOut = response.replaceAll(LS + LS, LS);
            assertEquals(consoleOut, testOut.toString());
        } finally {
            System.setIn(systemIn);
            System.setOut(systemOut);
        }
    }

}