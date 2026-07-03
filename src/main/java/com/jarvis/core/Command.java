package com.jarvis.core;

public interface Command {
    String execute(String[] args);
    String getName();
    String getDescription();
}
