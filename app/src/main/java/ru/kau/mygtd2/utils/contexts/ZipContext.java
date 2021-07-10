package ru.kau.mygtd2.utils.contexts;

import androidx.core.util.Pair;

import java.io.File;

import ru.kau.mygtd2.enums.BookType;
import ru.kau.mygtd2.utils.LOG;
import ru.kau.mygtd2.utils.core.codec.CodecContext;
import ru.kau.mygtd2.utils.core.codec.CodecDocument;
import ru.kau.mygtd2.utils.core.codec.pdf.PdfContext;
import ru.kau.mygtd2.utils.zip.CacheZipUtils;

public class ZipContext extends PdfContext {

    @Override
    public CodecDocument openDocumentInner(String fileName, String password) {
        LOG.d("ZipContext begin", fileName);

        Pair<Boolean, String> pack = CacheZipUtils.isSingleAndSupportEntry(fileName);
        if (pack.first) {
            LOG.d("ZipContext", "Singe archive entry");
            Fb2Context fb2Context = new Fb2Context();
            String etryPath = getFileNameSalt(fileName) + pack.second;
            String path = new File(CacheZipUtils.CacheDir.ZipApp.getDir(), etryPath).getPath();

            File cacheFileName = fb2Context.getCacheFileName(path + getFileNameSalt(path));
            LOG.d("ZipContext", etryPath, cacheFileName.getName());
            if (cacheFileName.exists()) {
                LOG.d("ZipContext", "FB2 cache exists");
                return fb2Context.openDocumentInner(etryPath, password);
            }
        }


        String path = CacheZipUtils.extracIfNeed(fileName, CacheZipUtils.CacheDir.ZipApp, getFileNameSalt(fileName)).unZipPath;
        if (path.endsWith("zip")) {
            return null;
        }

        CodecContext ctx = BookType.getCodecContextByPath(path);
        LOG.d("ZipContext", "open", path);
        return ctx.openDocument(path, password);
    }

}
