package edu.mit.eecs.parserlib.edit;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class GrammarPartitionScanner extends RuleBasedPartitionScanner {

    public static final String COMMENT = "__parserlib_comment";

    public GrammarPartitionScanner() {
        setPredicateRules(new IPredicateRule[] {
                new MultiLineRule("/*", "*/", new Token(COMMENT)),
                new EndOfLineRule("//", new Token(COMMENT)),
        });
    }
}
