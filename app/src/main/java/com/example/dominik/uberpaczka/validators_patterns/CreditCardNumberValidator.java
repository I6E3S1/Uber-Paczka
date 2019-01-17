package com.example.dominik.uberpaczka.validators_patterns;

import android.support.design.widget.TextInputLayout;

/**
 * Created by marek on 21.12.2018.
 */

public class CreditCardNumberValidator extends AbstractValidator {

    private String patternString="\\d{16}";

    public CreditCardNumberValidator() {
    }

    public CreditCardNumberValidator(TextInputLayout textInputLayout, String message, String messageIfBlank) {
        super(textInputLayout, message, messageIfBlank);
        compilePattern(patternString);
    }
}
