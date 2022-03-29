package sample.Model;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

public class SuggestStrongPassword {
    CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
    CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
    CharacterRule special = new CharacterRule(EnglishCharacterData.Special);
    PasswordGenerator passwordGenerator = new PasswordGenerator();

    public String generatePassword(){
        return this.passwordGenerator.generatePassword(32, this.alphabets, this.digits, this.special);
    }
}
