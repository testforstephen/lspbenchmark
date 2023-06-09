package com.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.services.LanguageClient;

public final class BenchmarkMain {
    private static long startAt = System.currentTimeMillis();
    private static final int OCCURENCE = 10;

    private BenchmarkMain() {
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String lspRequests = buildLSPRequests();
        InputStream inputStream = new ByteArrayInputStream(lspRequests.getBytes(StandardCharsets.UTF_8.name()));
        OutputStream outputStream = new ByteArrayOutputStream();
        Function<MessageConsumer, MessageConsumer> wrapper = it -> it;
        Launcher<LanguageClient> launcher = Launcher.createLauncher(new JavaLanguageServer(), LanguageClient.class, inputStream, outputStream, Executors.newCachedThreadPool(), wrapper);
        launcher.startListening();

        while (true) {
            // Sleep for a period of time to avoid consuming too much CPU
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }

    private static String buildLSPRequests() {
        StringBuffer buffer = new StringBuffer();
        int id = 1;
        // Build initialize request
        String init = "{\"jsonrpc\":\"2.0\",\"id\":" + (id++) + ",\"method\":\"initialize\",\"params\":{}}\r\n";
        buffer.append("Content-Length:");
        buffer.append(init.getBytes().length);
        buffer.append("\r\n\r\n");
        buffer.append(init);

        // Build completion requests
        for (int i = 1; i <= OCCURENCE; i++) {
            String completion = "{\"jsonrpc\":\"2.0\",\"id\":" + (id++) + ",\"method\":\"textDocument/completion\",\"params\":{\"textDocument\":{\"uri\":\"uri\"},\"position\":{\"line\":0,\"character\":0},\"context\": {\"triggerKind\": 1}}}\r\n";
            buffer.append("Content-Length:");
            buffer.append(completion.getBytes().length);
            buffer.append("\r\n\r\n");
            buffer.append(completion);
        }

        // Build exit request
        String exit = "{\"jsonrpc\":\"2.0\",\"id\":" + (id++) + ",\"method\":\"exit\"}\r\n";
        buffer.append("Content-Length:");
        buffer.append(exit.getBytes().length);
        buffer.append("\r\n\r\n");
        buffer.append(exit);

        return buffer.toString();
    }

    public static void initialize() {
        startAt = System.currentTimeMillis();
    }

    public static void exit() {
        long endAt = System.currentTimeMillis();
        System.out.println("Total completion request: " + OCCURENCE);
        System.out.println("Total time: " + (endAt - startAt) + "ms");
        System.out.println("Average time: " + (endAt - startAt) / OCCURENCE + "ms");
        // try {
        //     Thread.sleep(1000000);
        // } catch (InterruptedException e) {
        //     // Ignore
        // }
        System.exit(0);
    }
}
