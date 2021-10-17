package com.ruoyi.web.netty.hj212.streamReader.core;

import java.io.IOException;
import java.util.Optional;

public class NoneReadMatch implements ReaderMatch {
    @Override
    public Optional match() throws IOException {
        return Optional.empty();
    }

    @Override
    public ReaderStream done() {
        return null;
    }
}
