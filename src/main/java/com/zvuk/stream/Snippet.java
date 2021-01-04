package com.zvuk.stream;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Snippet {
    public Snippet() throws IOException {

//        stream();
//        random();
    }


    public static void random() throws IOException {
        File file = new ClassPathResource("doc.txt").getFile();

        RandomAccessFile raf = new RandomAccessFile(file, "r");
        byte[] bytes = new byte[(int) file.length()];

        int length = 3586;
        int start = 10;
        int end = 30;

        int byteCount = end - start;

        raf.seek(start);

        raf.read(bytes, start, byteCount + 1);

        ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream();

        byte[] result = new byte[(end - start) + 1];

        System.arraycopy(bytes, start, result, 0, result.length);

//        bufferedOutputStream.write(bytes, start, result);

        String content = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(content);
        System.out.println(bytes.length);
    }


    public static void stream() throws IOException {
        File file = new ClassPathResource("doc.txt").getFile();
        String content = null;
        try {
            try (InputStream in = new FileInputStream(file)) {
                byte[] bytes = new byte[(int) file.length()];

                int offset = 1000;
                while (offset < bytes.length) {
                    int result = in.read(bytes, offset, bytes.length - offset);
                    if (result == -1) {
                        break;
                    }
                    offset += result;
                }
                content = new String(bytes, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(content);
    }


}
