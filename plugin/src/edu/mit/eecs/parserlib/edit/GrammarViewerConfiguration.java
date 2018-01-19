package edu.mit.eecs.parserlib.edit;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class GrammarViewerConfiguration extends SourceViewerConfiguration {

    private final ColorManager colors;
    private final GrammarBodyScanner scanner;

    public GrammarViewerConfiguration(ColorManager colors) {
        this.colors = colors;
        this.scanner = new GrammarBodyScanner(colors);
    }

    @Override public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
        return new String[] {
                IDocument.DEFAULT_CONTENT_TYPE,
                GrammarPartitionScanner.COMMENT,
        };
    }

    @Override public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
        final PresentationReconciler reconciler = new PresentationReconciler();

        final DefaultDamagerRepairer drGrammar = new DefaultDamagerRepairer(scanner);
        reconciler.setDamager(drGrammar, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(drGrammar, IDocument.DEFAULT_CONTENT_TYPE);

        final PartitionDamagerRepairer drComment = new PartitionDamagerRepairer(
                new TextAttribute(colors.getColor(ColorManager.COMMENT)));
        reconciler.setDamager(drComment, GrammarPartitionScanner.COMMENT);
        reconciler.setRepairer(drComment, GrammarPartitionScanner.COMMENT);

        return reconciler;
    }
}
