package com.lk;

import io.micronaut.runtime.Micronaut;
import org.jooq.codegen.GenerationTool;

import java.nio.file.Files;
import java.nio.file.Path;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}