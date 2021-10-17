package com.ruoyi.web.netty.hj212.parser.base.parser;

import java.io.IOException;
import java.io.PushbackReader;

/**
 * 字符流解析器
 * Created on 2018/1/3.
 */
public abstract class PushBackReaderParser<Target>
        implements Parser<Target> {

    protected PushbackReader reader;

    public PushBackReaderParser(PushbackReader reader){
        this.reader = reader;
    }


    @Override
    public void close(){
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
