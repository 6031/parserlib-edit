package edu.mit.eecs.parserlib.edit;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.presentation.IPresentationDamager;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.custom.StyleRange;

/**
 * NonRuleBasedDamagerRepairer provided by PDE editor template.
 */
public class PartitionDamagerRepairer implements IPresentationDamager, IPresentationRepairer {

    private final TextAttribute defaultAttribute;
    private IDocument document;

    public PartitionDamagerRepairer(TextAttribute defaultAttribute) {
        Assert.isNotNull(defaultAttribute);
        this.defaultAttribute = defaultAttribute;
    }

    @Override public void setDocument(IDocument document) {
        this.document = document;
    }

    @Override public IRegion getDamageRegion(ITypedRegion partition, DocumentEvent event, boolean documentPartitioningChanged) {
        if ( ! documentPartitioningChanged) {
            try {
                final IRegion info = document.getLineInformationOfOffset(event.getOffset());
                final int start = Math.max(partition.getOffset(), info.getOffset());

                int end = event.getOffset() + (event.getText() == null ? event.getLength() : event.getText().length());

                if (info.getOffset() <= end && end <= info.getOffset() + info.getLength()) {
                    end = info.getOffset() + info.getLength();
                } else {
                    end = endOfLineOf(end);
                }

                end = Math.min(partition.getOffset() + partition.getLength(), end);
                return new Region(start, end - start);
            } catch (BadLocationException ble) {
            }
        }

        return partition;
    }

    /**
     * Returns the end offset of the line that contains the specified offset or
     * if the offset is inside a line delimiter, the end offset of the next line.
     *
     * @param offset the offset whose line end offset must be computed
     * @return the line end offset for the given offset
     * @exception BadLocationException if offset is invalid in the current document
     */
    private int endOfLineOf(int offset) throws BadLocationException {
        final IRegion info = document.getLineInformationOfOffset(offset);
        if (offset <= info.getOffset() + info.getLength()) {
            return info.getOffset() + info.getLength();
        }

        final int line = document.getLineOfOffset(offset);
        try {
            final IRegion nextInfo = document.getLineInformation(line + 1);
            return nextInfo.getOffset() + nextInfo.getLength();
        } catch (BadLocationException ble) {
            return document.getLength();
        }
    }

    @Override public void createPresentation(TextPresentation presentation, ITypedRegion region) {
        addRange(presentation, region.getOffset(), region.getLength(), defaultAttribute);
    }

    private void addRange(TextPresentation presentation, int offset, int length, TextAttribute attr) {
        presentation.addStyleRange(
                new StyleRange(offset, length, attr.getForeground(), attr.getBackground(), attr.getStyle()));
    }
}
