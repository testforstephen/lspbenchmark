package com.example;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

import com.google.gson.Gson;

public class JavaLanguageServer implements LanguageServer, TextDocumentService, WorkspaceService {
    private CompletionList completionResponse = null;

    public JavaLanguageServer() {
        String completionData = readCompletionResponse();
        Gson gson = new Gson();
        completionResponse = gson.fromJson(completionData, CompletionList.class);
    }

    private static String readCompletionResponse() {
        InputStream sampleResponse = BenchmarkMain.class.getResourceAsStream("/response.txt");
        try (Scanner scanner = new Scanner(sampleResponse, StandardCharsets.UTF_8.name())) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        throw new UnsupportedOperationException("Unimplemented method 'shutdown'");
    }

    @Override
    public void exit() {
        BenchmarkMain.exit();
    }

    @Override
    public TextDocumentService getTextDocumentService() {
        return this;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return this;
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        throw new UnsupportedOperationException("Unimplemented method 'didOpen'");
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        throw new UnsupportedOperationException("Unimplemented method 'didChange'");
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        throw new UnsupportedOperationException("Unimplemented method 'didClose'");
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
        throw new UnsupportedOperationException("Unimplemented method 'didSave'");
    }

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {
        throw new UnsupportedOperationException("Unimplemented method 'didChangeConfiguration'");
    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
        throw new UnsupportedOperationException("Unimplemented method 'didChangeWatchedFiles'");
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams position) {
        return CompletableFuture.completedFuture(Either.forRight(completionResponse));
    }
}
