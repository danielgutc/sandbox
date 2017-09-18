package org.dgc.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Closures
{
    private static Logger logger = LoggerFactory.getLogger(Closures.class);

    public void testClosures()
    {
        final Map<String, String> values  = new HashMap<>();
        List<String> toMap = Arrays.asList("A", "B", "C");

        int n = 0;
        //toMap.stream().forEach(s -> values.put(n++, s));// Java closures only accept final parameters. I can modify objects but not native parameters.
        toMap.stream().forEach(s -> values.put(s, s));
        values.values().forEach(s -> logger.info(s));
    }

    public void testReturn()
    {
        List<Integer> numbers = Arrays.asList(0,1,2,3,4,5,6,7,8,9);

        numbers.stream().filter(n -> {return n %2 == 0;}).map(n -> String.valueOf(n)).forEach(logger::info);
    }
}
