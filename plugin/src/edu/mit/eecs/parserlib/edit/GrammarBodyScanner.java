package edu.mit.eecs.parserlib.edit;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;

public class GrammarBodyScanner extends RuleBasedScanner {

    public GrammarBodyScanner(ColorManager manager) {
        final IToken string = new Token(new TextAttribute(manager.getColor(ColorManager.STRING)));
        final IToken keyword = new Token(new TextAttribute(manager.getColor(ColorManager.KEYWORD)));
        final IToken regex = new Token(new TextAttribute(manager.getColor(ColorManager.REGEX)));

        final WordRule operators = new WordRule(new IWordDetector() {
            public boolean isWordStart(char c) { return isOperator(c); }
            public boolean isWordPart(char c) { return false; }
        });
        operators.addWord(".", keyword);
        operators.addWord("|", keyword);
        operators.addWord("*", keyword);
        operators.addWord("+", keyword);
        operators.addWord("?", keyword);

        final WordRule keywords = new WordRule(new IWordDetector() {
            public boolean isWordStart(char c) { return ! isWhitespace(c); }
            public boolean isWordPart(char c) { return ! (isWhitespace(c) || isOperator(c)); }
        });
        keywords.addWord("@skip", keyword);
        keywords.addWord("::=", keyword);
        keywords.addWord("\\d", keyword);
        keywords.addWord("\\s", keyword);
        keywords.addWord("\\w", keyword);

        setRules(new IRule[] {
                new SingleLineRule("'", "'", string, '\\'),
                new SingleLineRule("\"", "\"", string, '\\'),
                new SingleLineRule("[^", "]", regex, '\\'),
                new SingleLineRule("[", "]", string, '\\'),
                new SingleLineRule("{", "}", keyword, '\\'),
                operators,
                keywords,
                new WhitespaceRule(this::isWhitespace),
        });
    }

    private boolean isOperator(char c) {
        return c == '.' || c == '|' || c == '*' || c == '+' || c == '?' || c == '(' || c == ')';
    }

    private boolean isWhitespace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }
}
