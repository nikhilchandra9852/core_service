package com.akp.core_service.utils;

public class GeneratePromptUtil {


    public static String generatePrompt(String question,String Content,String memoryHistory){

        return """
                
                Answer the question only on below context.
                
                
                ---context
                %s
                
                ---question
                %s
                
               --- history conversion
               %s
                
                """.formatted(Content,question,memoryHistory);



    }
}
