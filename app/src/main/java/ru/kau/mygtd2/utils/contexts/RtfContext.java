package ru.kau.mygtd2.utils.contexts;

import java.io.File;

import ru.kau.mygtd2.utils.AppSP;
import ru.kau.mygtd2.utils.BookCSS;
import ru.kau.mygtd2.utils.LOG;
import ru.kau.mygtd2.utils.core.codec.CodecDocument;
import ru.kau.mygtd2.utils.core.codec.pdf.MuPdfDocument;
import ru.kau.mygtd2.utils.core.codec.pdf.PdfContext;
import ru.kau.mygtd2.utils.ext.RtfExtract;
import ru.kau.mygtd2.utils.zip.CacheZipUtils;

public class RtfContext extends PdfContext {

    File cacheFile;

    @Override
    public File getCacheFileName(String fileNameOriginal) {
        fileNameOriginal = fileNameOriginal + BookCSS.get().isAutoHypens + AppSP.get().hypenLang;
        cacheFile = new File(CacheZipUtils.CACHE_BOOK_DIR, fileNameOriginal.hashCode() + ".html");
        return cacheFile;
    }

    @Override
    public CodecDocument openDocumentInner(String fileName, String password) {
        if (cacheFile == null) {
            getCacheFileName(fileName);
        }
        if (!cacheFile.isFile()) {
            try {
                RtfExtract.extract(fileName, CacheZipUtils.CACHE_BOOK_DIR.getPath(), cacheFile.getName());
            } catch (Exception e) {
                LOG.e(e);
            }
        }

        MuPdfDocument muPdfDocument = new MuPdfDocument(this, MuPdfDocument.FORMAT_PDF, cacheFile.getPath(), password);
        return muPdfDocument;
    }
}

