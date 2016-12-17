package com.dwalldorf.owbackend.event;

import com.dwalldorf.owbackend.model.Demo;

public class DemoAnalyzedEvent {

    private final Demo demo;

    public DemoAnalyzedEvent(Demo demo) {
        this.demo = demo;
    }

    public Demo getDemo() {
        return demo;
    }
}
