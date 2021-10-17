package com.ruoyi.web.netty.hj212.segmentParser.core;



import com.ruoyi.web.netty.hj212.segmentParser.cfger.Configurator;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Configured;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created on 2018/2/24.
 */
public class SegmentGenerator
        implements Closeable, Configured<SegmentGenerator> {

//    private ParseContext context;

    protected Writer writer;
    private int generatorFeature;
    private SegmentToken nextToken;
    private LinkedList<String> previousKeys = new LinkedList<String>();

    public SegmentGenerator(Writer writer){
        this.writer = writer;
    }


    /**
     * 写入KEY
     * @return KEY
     * @throws IOException
     */
    public void writeKey(String key) throws IOException {
        writePathKey(false,key);
    }

    /**
     * 写入一部分KEY
     * @return 部分KEY
     * @throws IOException
     */
    public void writePathKey(String key) throws IOException {
        writePathKey(true,key);
    }

    /**
     * 写入KEY
     * @param supportSubKey true：遇到‘-’结束
     * @param key Key
     * @return KEY
     * @throws IOException
     */
    private void writePathKey(boolean supportSubKey, String key) throws IOException {
        //确定分隔符
        switch (nextToken){
            case END_KEY:
            case END_PART_KEY:
                writer.write(SegmentToken.END_PART_KEY.start());
                break;
            case END_OBJECT_VALUE:
            case START_OBJECT_VALUE:
                throw new IOException("Cant write key after " + nextToken.name() + " token!");
            case END_SUB_ENTRY:
                if(supportSubKey){
                    writer.write(SegmentToken.END_SUB_ENTRY.start());
                    writer.write(previousKeys
                            .subList(0,previousKeys.size() - 1)
                            .stream()
                            .collect(Collectors.joining("-")));
                    writer.write(SegmentToken.END_PART_KEY.start());
                    break;
                }
            case END_ENTRY:
                writer.write(SegmentToken.END_ENTRY.start());
                previousKeys.clear();
                break;
        }

        writer.write(key);
        previousKeys.add(key);

        //预期
        if(supportSubKey){
            nextToken = SegmentToken.END_PART_KEY;
        }else{
            nextToken = SegmentToken.END_KEY;
        }
    }

    /**
     * 写入VALUE
     * @param value
     * @return VALUE
     * @throws IOException
     */
    public void writeValue(String value) throws IOException {
        writeValue(false,value);
    }

    /**
     * 写入对象VALUE
     * @param value
     * @return 对象VALUE
     * @throws IOException
     */
    public void writeObjectValue(String value) throws IOException {
        writeValue(true,value);
    }


    /**
     * 写入VALUE
     * @param flagObject true：标识‘&&’值
     * @param value
     * @return VALUE
     * @throws IOException
     */
    private void writeValue(boolean flagObject, String value) throws IOException {
        //当前Token
        switch (nextToken){
            case END_ENTRY:
            case END_SUB_ENTRY:
            case END_OBJECT_VALUE:
                throw new IOException("Cant write value after " + nextToken.name() + " token!");
            case END_KEY:
            case END_PART_KEY:
                //确定分隔符
                writer.write(SegmentToken.END_KEY.start());
                break;
        }

        if(value != null){
            if(flagObject){
                writer.write(SegmentToken.START_OBJECT_VALUE.start());
                writer.write(SegmentToken.START_OBJECT_VALUE.start());
                writer.write(value);
                writer.write(SegmentToken.END_OBJECT_VALUE.start());
                writer.write(SegmentToken.END_OBJECT_VALUE.start());
            }else{
                writer.write(value);
            }
        }

        //预期
        if(previousKeys.isEmpty() || previousKeys.size() == 1){
            nextToken = SegmentToken.END_ENTRY;
        }else{
            nextToken = SegmentToken.END_SUB_ENTRY;
        }
    }

    public SegmentGenerator writeObjectStart() throws IOException {
        //当前Token
        switch (nextToken){
            case END_ENTRY:
            case END_SUB_ENTRY:
            case END_OBJECT_VALUE:
                throw new IOException("Cant write value after " + nextToken.name() + " token!");
            case END_KEY:
            case END_PART_KEY:
                //确定分隔符
                writer.write(SegmentToken.END_KEY.start());
                break;
        }

        writer.write(SegmentToken.START_OBJECT_VALUE.start());
        writer.write(SegmentToken.START_OBJECT_VALUE.start());

        //预期
        nextToken = SegmentToken.END_OBJECT_VALUE;

        SegmentGenerator generator = new SegmentGenerator(this.writer);
        generator.initToken();
        return generator;
    }

    public void writeObjectEnd() throws IOException {
        writer.write(SegmentToken.END_OBJECT_VALUE.start());
        writer.write(SegmentToken.END_OBJECT_VALUE.start());
        //预期
        if(previousKeys.isEmpty()){
            nextToken = SegmentToken.END_ENTRY;
        }else{
            nextToken = SegmentToken.END_SUB_ENTRY;
        }
    }


    /**
     * 预期Token
     * @return SegmentToken
     */
    public SegmentToken nextToken(){
        return nextToken;
    }

    public void initToken(){
        nextToken = SegmentToken.NOT_AVAILABLE;
    }

    public void setGeneratorFeature(int generatorFeature) {
        this.generatorFeature = generatorFeature;
    }

    @Override
    public void close(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void configured(Configurator<SegmentGenerator> by) {
        by.config(this);
    }
}
