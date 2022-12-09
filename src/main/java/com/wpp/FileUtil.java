package com.wpp;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * Function:
 * Author: wpp
 * Date: 2022/8/6 20:44
 */
public class FileUtil {
    public static void saveBytesToFile(byte[] bytes, String filename) {
        try (
                FileChannel out = new FileOutputStream(filename).getChannel();
                ReadableByteChannel in = Channels.newChannel(new ByteArrayInputStream(bytes));
        ) {
            out.transferFrom(in, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
