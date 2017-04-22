package com.robsafety.hesms;

import com.google.common.base.Splitter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Jijeesh on 4/15/2017.
 */
@Component("QueryPropertyMapper")
public class QueryPropertyMapper {

    public Map<String, String> map(String property) {
        return this.map(property, ";");
    }

    private Map<String, String> map(String property, String splitter) {
        return Splitter.on(splitter).omitEmptyStrings().trimResults().withKeyValueSeparator("::").split(property);
    }

}
