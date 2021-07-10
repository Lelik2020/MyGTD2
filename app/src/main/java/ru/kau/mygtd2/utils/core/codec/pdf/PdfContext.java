package ru.kau.mygtd2.utils.core.codec.pdf;

import ru.kau.mygtd2.utils.core.codec.CodecDocument;

public class PdfContext extends MuPdfContext {

    @Override
    public CodecDocument openDocumentInner(String fileName, final String password) {
        MuPdfDocument muPdfDocument = new MuPdfDocument(this, MuPdfDocument.FORMAT_PDF, fileName, password);
        return muPdfDocument;
    }


}
