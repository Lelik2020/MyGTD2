package ru.kau.mygtd2.enums;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ru.kau.mygtd2.utils.LOG;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.contexts.DjvuContext;
import ru.kau.mygtd2.utils.contexts.DocContext;
import ru.kau.mygtd2.utils.contexts.DocxContext;
import ru.kau.mygtd2.utils.contexts.EpubContext;
import ru.kau.mygtd2.utils.contexts.Fb2Context;
import ru.kau.mygtd2.utils.contexts.FolderContext;
import ru.kau.mygtd2.utils.contexts.RtfContext;
import ru.kau.mygtd2.utils.contexts.ZipContext;
import ru.kau.mygtd2.utils.core.codec.CodecContext;
import ru.kau.mygtd2.utils.core.codec.pdf.PdfContext;
import ru.kau.mygtd2.utils.info.AppsConfig;

public enum BookType {

    PDF(PdfContext.class, false, Arrays.asList("pdf", "xps"), Arrays.asList("application/pdf","application/oxps","application/vnd.ms-xpsdocument")),
    TIFF(PdfContext.class, false, Arrays.asList("tiff", "tif"), Arrays.asList("image/tiff")),

    CBZ(PdfContext.class, false, Arrays.asList("cbz"), Arrays.asList("application/x-cbz")),
    //CBR(CbrContext.class, false, Arrays.asList("cbr"), Arrays.asList("application/x-cbr")),
    //ODT(OdtContext.class, true, Arrays.asList("odt"), Arrays.asList("application/vnd.oasis.opendocument.text")),

    //FOLDER(FolderContext.class, false, Arrays.asList(FolderContext.LXML), Arrays.asList("application/lxml")),

    EPUB(EpubContext.class, true, Arrays.asList("epub"), Arrays.asList("application/epub+zip")),


    FB2(Fb2Context.class, true, Arrays.asList("fb2"),
            Arrays.asList("application/fb2", "application/x-fictionbook", "application/x-fictionbook+xml", "application/x-fb2", "application/fb2+zip", "application/fb2.zip", "application/x-zip-compressed-fb2")),

    //MOBI(MobiContext.class, true, Arrays.asList("mobi", "azw", "azw3", "azw4", "pdb", "prc"), Arrays.asList("application/x-mobipocket-ebook", "application/x-palm-database")),

    //TXT(TxtContext.class, true, Arrays.asList("txt", "playlist","log"), Arrays.asList("text/plain","text/x-log")),

    //JSON(TxtContext.class, true, Arrays.asList("json"), Arrays.asList("application/json")),

    //HTML(HtmlContext.class, true, Arrays.asList("html", "htm", "xhtml", "xhtm", "mht", "mhtml", "xml"), Arrays.asList("text/html", "text/xml")),

    //MHT(MhtContext.class, true, Arrays.asList("mht", "mhtml"), Arrays.asList("message/rfc822")),

    DOCX(DocxContext.class, true, Arrays.asList(AppsConfig.isDOCXSupported ? "docx" : "xxx"), Arrays.asList("application/vnd.openxmlformats-officedocument.wordprocessingml.document")),

    DOC(DocContext.class, true, Arrays.asList("doc"), Arrays.asList("application/msword")),

    //MD(MdContext.class, true, Arrays.asList("md"), Arrays.asList("text/markdown","text/x-markdown")),

    RTF(RtfContext.class, true, Arrays.asList("rtf"), Arrays.asList("application/rtf", "application/x-rtf", "text/rtf", "text/richtext")),

    DJVU(DjvuContext.class, false, Arrays.asList("djvu"), Arrays.asList("image/vnd.djvu", "image/djvu", "image/x-djvu")),

    ZIP(ZipContext.class, true, Arrays.asList("zip"), Arrays.asList("application/zip", "application/x-compressed", "application/x-compressed-zip", "application/x-zip-compressed"));

    private final static Map<String, BookType> extensionToActivity;

    private final static Map<String, BookType> mimeTypesToActivity;

    static {
        extensionToActivity = new HashMap<String, BookType>();
        for (final BookType a : values()) {
            for (final String ext : a.extensions) {
                extensionToActivity.put(ext.toLowerCase(Locale.US), a);
            }
        }
        mimeTypesToActivity = new HashMap<String, BookType>();
        for (final BookType a : values()) {
            for (final String type : a.mimeTypes) {
                mimeTypesToActivity.put(type.toLowerCase(Locale.US), a);
            }
        }
    }

    private final Class<? extends CodecContext> contextClass;

    private final List<String> extensions;

    private final List<String> mimeTypes;
    private boolean isTextFormat;

    private BookType(final Class<? extends CodecContext> contextClass, boolean isTextFormat, final List<String> extensions, final List<String> mimeTypes) {
        this.contextClass = contextClass;
        this.extensions = extensions;
        this.mimeTypes = mimeTypes;
        this.isTextFormat = isTextFormat;
    }

    public boolean is(String path) {
        if (path == null) {
            return false;
        }

        path = path.toLowerCase(Locale.US);
        for (final String ext : extensions) {
            if (path.endsWith(ext) || path.endsWith("." + ext + ".zip")) {
                return true;
            }
        }
        return false;
    }

    public String getExt() {
        return extensions.get(0);
    }

    public boolean is(Intent intent) {
        try {
            return is(intent.getData().getPath());
        } catch (Exception e) {
            return false;
        }
    }

    public static List<String> getAllSupportedExtensions() {
        List<String> list = new ArrayList<String>();

        for (final BookType a : values()) {
            for (final String ext : a.extensions) {
                list.add(ext);
            }
        }
        list.remove(FolderContext.LXML);
        return list;
    }

    public static boolean isTextFormat(String path) {
        List<String> list = new ArrayList<String>();

        for (final BookType a : values()) {
            if (!a.isTextFormat) {
                continue;
            }
            for (final String ext : a.extensions) {
                if (path.endsWith(ext)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static CodecContext getCodecContextByType(BookType activityType) {
        try {
            return activityType.contextClass.newInstance();
        } catch (Exception e) {
            LOG.e(e);
        }
        return null;
    }

    public static CodecContext getCodecContextByPath(String path) {
        CodecContext ctx = null;
        try {
            ctx = getByUri(path).contextClass.newInstance();
        } catch (Exception e) {
            LOG.e(e);
        }
        return ctx;
    }

    public String getFirstMimeTime() {
        return mimeTypes.get(0);
    }

    public static boolean isSupportedExtByPath(String path) {
        if (path == null) {
            return false;
        }
        path = path.toLowerCase(Locale.US);

        for (final BookType a : values()) {
            for (final String ext : a.extensions) {
                if (TxtUtils.isEmpty(ext)) {
                    continue;
                }
                if (path.endsWith(ext)) {
                    return true;
                }

            }
        }
        return false;
    }

    public static BookType getByUri(String uri) {
        if (uri == null) {
            return null;
        }

        uri = uri.toLowerCase(Locale.US);

        for (final String ext : extensionToActivity.keySet()) {
            if (uri.endsWith("." + ext)) {
                return extensionToActivity.get(ext);
            }
        }
        return null;
    }

    public static BookType getByMimeType(final String type) {
        if (type == null) {
            return null;
        }
        return mimeTypesToActivity.get(type.toLowerCase(Locale.US));
    }

}
