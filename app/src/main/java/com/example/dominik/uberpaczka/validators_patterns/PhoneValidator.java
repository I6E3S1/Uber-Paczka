package com.example.dominik.uberpaczka.validators_patterns;

import android.support.design.widget.TextInputLayout;

/**
 * Created by marek on 21.12.2018.
 */

public class PhoneValidator extends AbstractValidator {

    private String patternString="\\d{9}";


    public PhoneValidator() {
    }

    public PhoneValidator(TextInputLayout textInputLayout, String message, String messageIfBlank) {
        super(textInputLayout, message, messageIfBlank);
        compilePattern(patternString);
    }
}
