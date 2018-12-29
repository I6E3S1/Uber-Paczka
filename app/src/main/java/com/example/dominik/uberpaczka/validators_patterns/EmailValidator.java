package com.example.dominik.uberpaczka.validators_patterns;

import android.support.design.widget.TextInputLayout;

/**
 * Created by marek on 21.12.2018.
 */

public class EmailValidator extends AbstractValidator {

    private String patternString="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}";

    public EmailValidator() {
    }

    public EmailValidator(TextInputLayout textInputLayout, String message, String messageIfBlank) {
        super(textInputLayout, message, messageIfBlank);
        compilePattern(patternString);
    }
}
