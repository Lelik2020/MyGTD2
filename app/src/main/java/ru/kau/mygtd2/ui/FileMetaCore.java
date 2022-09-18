package ru.kau.mygtd2.ui;

import java.io.File;

import ru.kau.mygtd2.AppDB;
import ru.kau.mygtd2.enums.BookType;
import ru.kau.mygtd2.objects.FileMeta;
import ru.kau.mygtd2.utils.ExtUtils;
import ru.kau.mygtd2.utils.LOG;
import ru.kau.mygtd2.utils.TxtUtils;

public class FileMetaCore {

    public static int STATE_NONE = 0;
    public static int STATE_BASIC = 1;
    public static int STATE_FULL = 2;

    private static FileMetaCore in = new FileMetaCore();

    public static FileMetaCore get() {
        return in;
    }

    public static FileMeta createMetaIfNeed(String path, final boolean isSearhcBook) {


        FileMeta fileMeta = AppDB.get().getOrCreate(path);

        LOG.d("BooksService-createMetaIfNeed", path, fileMeta.getState());

        /*try {
            if (FileMetaCore.STATE_FULL != fileMeta.getState()) {
                EbookMeta ebookMeta = FileMetaCore.get().getEbookMeta(path, CacheDir.ZipApp, true);

                FileMetaCore.get().upadteBasicMeta(fileMeta, new File(path));
                FileMetaCore.get().udpateFullMeta(fileMeta, ebookMeta);



                if (isSearhcBook) {
                    fileMeta.setIsSearchBook(isSearhcBook);
                }

                AppDB.get().update(fileMeta);
                LOG.d("BooksService checkOrCreateMetaInfo", "UPDATE", path);
            }
        }catch (Exception e){
            LOG.e(e);
        }*/
        return fileMeta;

    }


    public static String replaceComma(String input) {
        if (TxtUtils.isEmpty(input)) {
            return input;
        }
        input = input.replace("; ", ",").replace(";", ",");

        if (!input.endsWith(",")) {
            input = input + ",";
        }

        if (!input.startsWith(",")) {
            input = "," + input;
        }

        return input;
    }









    public void upadteBasicMeta(FileMeta fileMeta, File file) {
        fileMeta.setTitle(file.getName());// temp

        fileMeta.setSize(file.length());
        fileMeta.setDate(file.lastModified());
        fileMeta.setParentPath(file.getParent());

        fileMeta.setExt(ExtUtils.getFileExtension(file));
        fileMeta.setSizeTxt(ExtUtils.readableFileSize(file.length()));


        if (BookType.FB2.is(file.getName())) {
            fileMeta.setPathTxt(TxtUtils.encode1251(file.getName()));
        } else {
            fileMeta.setPathTxt(file.getName());

        }
        fileMeta.setState(STATE_BASIC);
    }


}
