import * as _passwordGenerator from 'generate-password-browser';

export class PasswordGenerator{

    static generatePassword(){
        let length = Math.floor(Math.random() * (18 - 12 + 1)) + 12;

        let options = {
            length: length,
            numbers: true,
            uppercase: true,
            symbols: true,
            lowercase: true,
            excludeSimilarCharacters: true,
            strict: true
        }

        return _passwordGenerator.generate(options);
    }
}