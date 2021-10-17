package com.ruoyi.web.netty.Test.streamReader.core;


import com.ruoyi.web.netty.hj212.streamReader.base.lambda.RunnableWithThrowable;
import com.ruoyi.web.netty.hj212.streamReader.base.lambda.SupplierWithThrowable;

import java.io.IOException;
import java.util.Optional;

/**
 * 字符读取匹配
 * @param <THIS>
 * @param <ParentStream>
 * @param <T>
 */
public interface ReaderMatch<THIS extends ReaderMatch,ParentStream extends ReaderStream,T> {

//    THIS when(Predicate<T> predicate);
//
//    THIS then(RunnableWithThrowable<IOException> runnable);

    /**
     * 字符读取流
     * @return ParentStream
     */
    ParentStream done();

    /**
     * 匹配
     * @return 匹配到
     * @throws IOException
     */
    Optional<T> match() throws IOException;


    static SupplierWithThrowable<Optional<Object>,IOException> alwaysReturnPresent(){
        return () -> Optional.of(true);
    }

    static SupplierWithThrowable<Optional<Object>,IOException> alwaysReturnNotPresent(){
        return Optional::empty;
    }

    static SupplierWithThrowable<Optional<Object>,IOException> alwaysReturnPresent(
        RunnableWithThrowable<IOException> runnable){
        return () -> {
            runnable.run();
            return Optional.of(true);
        };
    }

    static SupplierWithThrowable<Optional<Object>,IOException> alwaysReturnNotPresent(
        RunnableWithThrowable<IOException> runnable){
        return () -> {
            runnable.run();
            return Optional.empty();
        };
    }
}
