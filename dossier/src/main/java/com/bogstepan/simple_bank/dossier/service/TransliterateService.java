package com.bogstepan.simple_bank.dossier.service;

import com.ibm.icu.text.Transliterator;
import org.springframework.stereotype.Service;

@Service
public class TransliterateService {

    private final static String LATIN_TO_CYRILLIC = "Latin-Russian/BGN";

    public String transliterate(String text) {
        Transliterator toCyrillicTrans = Transliterator.getInstance(LATIN_TO_CYRILLIC);
        var rsl = toCyrillicTrans.transliterate(text);
        if (!rsl.isEmpty()) {
            rsl = Character.toUpperCase(rsl.charAt(0)) + rsl.substring(1);
        }
        return rsl;
    }
}
