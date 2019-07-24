package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.CharacterValidator;

public class AtomCharValidator implements CharacterValidator {

    @Override
    public boolean isValid(char chr) {
        return this.isCHAR(chr)
            && !this.isAtomSpecial(chr)
            && !this.isListWildcard(chr)
            && !this.isQuotedSpecial(chr);
    }

    private boolean isAtomSpecial(char chr) {
        return chr == '(' ||
            chr == ')' ||
            chr == '{' ||
            chr == ' ' ||
            chr == Character.CONTROL;
    }

    private boolean isCHAR(char chr) {
        return chr >= 0x01 && chr <= 0x7f;
    }

    protected boolean isListWildcard(char chr) {
        return chr == '*' || chr == '%';
    }

    private boolean isQuotedSpecial(char chr) {
        return chr == '"' || chr == '\\';
    }
}
