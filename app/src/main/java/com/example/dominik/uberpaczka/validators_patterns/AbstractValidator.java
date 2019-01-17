package com.example.dominik.uberpaczka.validators_patterns;

import android.support.design.widget.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by marek on 21.12.2018.
 */

public abstract class AbstractValidator  {

    private Pattern pattern;
    private TextInputLayout textInputLayout;
    private String message;
    private String messageIfBlank;


    public AbstractValidator() {
    }

    public AbstractValidator(TextInputLayout textInputLayout, String message, String messageIfBlank) {

        this.textInputLayout=textInputLayout;
        this.message=message;
        this.messageIfBlank=messageIfBlank;
    }

    public boolean validate(String input) {
        Matcher matcher=pattern.matcher(input);
        return matcher.matches();
    }

    public boolean validate() {
        if(textInputLayout==null) return false;
        boolean result=true;
        String input=textInputLayout.getEditText().getText().toString();
        Matcher matcher=pattern.matcher(input);
        if(input.length()!=0){
            if(!matcher.matches()){
                textInputLayout.setError(message);
                result=false;
            }

        }
        else
        {
            textInputLayout.setError(messageIfBlank);
            result=false;
        }

        return result;
    }

    public void compilePattern(String patternString){
        this.pattern=Pattern.compile(patternString);
    }


}
