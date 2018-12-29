package com.example.dominik.uberpaczka.validators_patterns;

import android.support.design.widget.TextInputLayout;

/**
 * Created by marek on 21.12.2018.
 */

public class DateValidator extends AbstractValidator {

    private String patternString="[0-3]\\d\\.(0\\d|1[0-2])\\.[1-2]\\d{3}";

    public DateValidator() {
    }

    public DateValidator(TextInputLayout textInputLayout, String message, String messageIfBlank) {
        super(textInputLayout, message, messageIfBlank);
        compilePattern(patternString);
    }
}
