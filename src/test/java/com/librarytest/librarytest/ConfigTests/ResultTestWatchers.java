package com.librarytest.librarytest.ConfigTests;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class ResultTestWatchers implements TestWatcher {
    @Override
    public void testSuccessful(ExtensionContext context){
        System.out.println("✅ TEST SUCCESSEFUL: "+context.getDisplayName());

    }
    @Override
    public void testFailed(ExtensionContext context,Throwable cause){
        System.out.println("❌ TEST FAILED: "+context.getDisplayName());
        System.out.println("CAUSE: "+cause.getMessage());
    }

}
