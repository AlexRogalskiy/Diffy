package com.wildbeeslabs.sensiblemetrics.diffy.common.executor.iface;

import java.io.IOException;

public interface Task<T> {

    void run(final T parameter) throws IOException;
}
