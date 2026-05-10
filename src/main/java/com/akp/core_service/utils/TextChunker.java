package com.akp.core_service.utils;

import java.util.ArrayList;
import java.util.List;

public class TextChunker {


    public static List<String> chuckData(
            String content,
            int chuckSize,
            int overlap
    ) {

        List<String> result = new ArrayList<>();

        int start = 0;

        while(start < content.length()) {

            String chunkContent = content.substring(start,Math.min(start+chuckSize, content.length()));

            start = start+chuckSize-overlap;

            result.add(chunkContent);
        }
        return result;
    }
}
