package org.dgc.sandbox.yamlparser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.File;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        App app = new App();
        app.test();
    }

    public void test()
    {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {

            InputStream is = getClass().getResourceAsStream("/application.yml");

            Map<String, Object> config = mapper.readValue(is, TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Object.class));


            Map<String, Object> result = new HashMap<>();
            flatProperties(null, config, result);

            //config.entrySet().stream().flatMap(this::flatMap).collect(Collectors.toList());

            System.out.println(ReflectionToStringBuilder.toString(config, ToStringStyle.MULTI_LINE_STYLE));

        } catch (Exception e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }
    }


    /*public Stream<AbstractMap.Entry<String, Object>> flatMap(Map.Entry<String, Object> e)
    {
        if (e.getValue() instanceof String)
        {
            Map<String, Object> res = new HashMap<>();
            res.put(propertyName, value);
        }
        else if (value instanceof Integer)
        {
            result.put(propertyName, value);
        }
        else
        {
            Map<String, Object> valueMap = (Map<String, Object>) value;
            for(Map.Entry<String, Object> entry: valueMap.entrySet())
            {
                String propName = StringUtils.isEmpty(propertyName) ? entry.getKey() : propertyName + "." + entry.getKey();
                flatProperties(propName, entry.getValue(), result);
            }
        }
    }*/


    public void flatProperties(String propertyName, Object value, Map<String, Object> result)
    {
        if (value instanceof HashMap)
        {
            Map<String, Object> valueMap = (Map<String, Object>) value;
            for(Map.Entry<String, Object> entry: valueMap.entrySet())
            {
                String propName = StringUtils.isEmpty(propertyName) ? entry.getKey() : propertyName + "." + entry.getKey();
                flatProperties(propName, entry.getValue(), result);
            }
        }
        else
        {
            result.put(propertyName, value);
        }
    }
}
