package com.example.dominik.uberpaczka.registration.registration_usable;

import com.example.dominik.uberpaczka.util.HasActionButtons;
import com.example.dominik.uberpaczka.util.HasValidation;

/**
 * Created by marek on 29.12.2018.
 */

public interface RegistrationFragmentInterface extends HasValidation, HasActionButtons {

    void setInformation() throws Exception;

}
