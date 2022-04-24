package sample.Model;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

// Using the passay library for suggesting strong password
public final class SuggestStrongPassword {
    private final CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
    private final CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
    private final CharacterRule special = new CharacterRule(EnglishCharacterData.Special);
    private final PasswordGenerator passwordGenerator = new PasswordGenerator();

    public String generatePassword(){
        return this.passwordGenerator.generatePassword(32, this.alphabets, this.digits, this.special);
    }
}
