package ru.kau.mygtd2.utils.sys;

import net.lingala.zip4j.model.FileHeader;

import ru.kau.mygtd2.utils.TxtUtils;

public class ArchiveEntry {

    private FileHeader header;

    public ArchiveEntry(FileHeader header) {
        this.header = header;
    }

    public long getSize() {
        return header.getUncompressedSize();
    }

    public long getCompressedSize() {
        return header.getCompressedSize();
    }

    public boolean isDirectory() {
        return header.isDirectory();
    }

    public String getName() {
        return TxtUtils.encode1251(header.getFileName());
    }

}
