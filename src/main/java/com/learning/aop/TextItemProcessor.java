package com.learning.aop;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author vickyaa
 * @date 4/17/25
 * @file TextItemProcessor
 */

@Component
public class TextItemProcessor implements ItemProcessor<String, String> {

    @Override
    public String process(String message) throws Exception {
        String maskedMessage = message.replaceAll("\\d", "*");
        return maskedMessage;
    }


}
