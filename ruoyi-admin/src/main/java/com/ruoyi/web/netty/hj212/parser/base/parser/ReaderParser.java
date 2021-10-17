package com.ruoyi.web.netty.hj212.parser.base.parser;

import java.io.IOException;
import java.io.Reader;

/**
 * 字符流解析器
 * Created on 2018/1/3.
 */
public abstract class ReaderParser<Target>
        implements Parser<Target> {

    protected Reader reader;

    public ReaderParser(Reader reader){
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
