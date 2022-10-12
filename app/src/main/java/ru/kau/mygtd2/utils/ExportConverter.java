package ru.kau.mygtd2.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Device;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskContextJoin;
import ru.kau.mygtd2.objects.TaskTagJoin;

public class ExportConverter {

    public static void copyPlaylists() {
        File oldDir = new File(AppProfile.DOWNLOADS_DIR, "Librera/Playlist");
        File[] list = oldDir.listFiles();

        if (list != null) {
            AppProfile.syncPlaylist.mkdirs();
            for (File file : list) {
                LOG.d("copyPlaylists", file.getPath());
                IO.copyFile(file, new File(AppProfile.syncPlaylist, file.getName()));
            }
        }
    }

    /*public static void covertJSONtoNew(Context c, File file) throws Exception {
        LOG.d("covertJSONtoNew", file);


        String st = IO.readString(file);
        LinkedJSONObject obj = new LinkedJSONObject(st);


        IO.writeString(AppProfile.syncState, obj.getJSONObject("pdf").toString());
        IO.writeString(AppProfile.syncCSS, obj.getJSONObject("BookCSS").toString());

        JSONArray recent = obj.getJSONArray("Recent");
        long t = System.currentTimeMillis();
        for (int i = 0; i < recent.length(); i++) {
            AppData.get().addRecent(new SimpleMeta(recent.getString(i), t - i));
        }

        JSONArray favorites = obj.getJSONArray("StarsBook");
        for (int i = 0; i < favorites.length(); i++) {
            AppData.get().addFavorite(new SimpleMeta(favorites.getString(i), i));
        }

        JSONArray folders = obj.getJSONArray("StarsFolder");
        for (int i = 0; i < folders.length(); i++) {
            AppData.get().addFavorite(new SimpleMeta(folders.getString(i), i));
        }

        JSONArray tags = obj.getJSONArray("TAGS");
        for (int i = 0; i < tags.length(); i++) {
            LinkedJSONObject it = tags.getJSONObject(i);
            String path = it.getString("path");
            String tag = it.getString("tag");
            TagData.saveTags(path, tag);
        }

        TagData.restoreTags();

        LinkedJSONObject books = obj.getJSONObject("BOOKS");
        Iterator<String> keys = books.keys();
        Map<String, Integer> cache = new HashMap<>();

        LinkedJSONObject resObj = IO.readJsonObject(AppProfile.syncProgress);
        while (keys.hasNext()) {

            String stringObj = books.getString(keys.next());
            stringObj = stringObj.replace("\\\"", "").replace("\\", "");

            LOG.d(stringObj);

            LinkedJSONObject value = new LinkedJSONObject(stringObj);


            AppBook appBook = new AppBook(value.getString("fileName"));
            appBook.z = value.getInt("zoom");
            appBook.sp = value.getBoolean("splitPages");
            appBook.cp = value.getBoolean("cropPages");
            appBook.dp = value.getBoolean("doublePages");
            appBook.dc = value.getBoolean("doublePagesCover");
            appBook.setLock(value.getBoolean("isLocked"));
            appBook.s = value.getInt("speed");
            appBook.d = value.optInt("pageDelta", 0);

            LinkedJSONObject currentPage = value.getJSONObject("currentPage");
            int pages = value.optInt("pages", 0);
            final int docIndex = currentPage.getInt("docIndex") + 1;
            if (pages > 0) {
                appBook.p = (float) docIndex / pages;
            } else if (docIndex >= 2) {//old import support
                appBook.p = docIndex;
            }
            appBook.x = value.getInt("offsetX");
            appBook.y = value.getInt("offsetY");

            cache.put(appBook.path, pages);
            LOG.d("Export-PUT", appBook.path, pages);


            //SharedBooks.save(appBook, false);

            if (appBook.p > 1) {
                appBook.p = 0;
            }
            final String fileName = ExtUtils.getFileName(appBook.path);
            resObj.put(fileName, Objects.toJSONObject(appBook));
        }
        IO.writeObj(AppProfile.syncProgress, resObj);

        LinkedJSONObject bookmarks = obj.getJSONObject("ViewerPreferences");
        Iterator<String> bKeys = bookmarks.keys();

        LinkedJSONObject resObj2 = IO.readJsonObject(AppProfile.syncBookmarks);

        while (bKeys.hasNext()) {
            String value = bookmarks.getString(bKeys.next());
            LOG.d(value);
            String[] it = value.split("~");

            AppBookmark bookmark = new AppBookmark();
            final String path = it[0];
            bookmark.setPath(path);
            bookmark.text = it[1];
            try {
                bookmark.t = Long.parseLong(it[4]);
            } catch (Exception e) {
                LOG.d("Error covertJSONtoNew", value);
                LOG.e(e);
            }
            try {
                bookmark.t = Long.parseLong(it[4]);
                if (it.length > 5) {
                    bookmark.p = Float.parseFloat(it[5]);
                } else {
                    if (!cache.containsKey(path)) {
                        if (new File(path).isFile() && (BookType.PDF.is(path) || BookType.DJVU.is(path)) ) {
                            final CodecDocument doc = ImageExtractor.singleCodecContext(path, "", 0, 0);
                            int pageCount = doc.getPageCount();
                            cache.put(path, pageCount);
                            LOG.d("Page-counts update ", path, pageCount);
                            doc.recycle();
                        }else{
                            LOG.d("Page-counts not found", path);
                        }
                    }
                    if (cache.containsKey(path)) {
                        try {
                            bookmark.p = (float) Integer.parseInt(it[2]) / cache.get(path);
                            LOG.d("Page-counts percent", path, bookmark.p);
                        } catch (Exception e) {
                            LOG.e(e);
                        }
                    }
                }
            } catch (Exception e) {
                LOG.d("Error covertJSONtoNew", value);

                LOG.e(e);
            }

            if (bookmark.p > 1) {
                bookmark.p = 0;
            }

            resObj2.put("" + bookmark.t, Objects.toJSONObject(bookmark));
        }

        IO.writeObjAsync(AppProfile.syncBookmarks, resObj2);

        SharedBooks.cache.clear();

    }*/

    public static void backupAllDBtoZip(File input, File output) throws ZipException {
        LOG.d("backupAllDBtoZip", input, output);

        ZipFile zipFile = new ZipFile(output);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        parameters.setCompressionLevel(CompressionLevel.NORMAL);

        // Бэкапируем справочники

        // Справочник контекстов (contexts)
        parameters.setFileNameInZip("contexts.json");
        List<Contekst> lstContekst = MyApplication.getDatabase().contextDao().getAll();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonConteksts = gson.toJson(lstContekst);
        InputStream isConteksts = new ByteArrayInputStream(jsonConteksts.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isConteksts, parameters);

        // Справочник устройств (devices)
        parameters.setFileNameInZip("devices.json");
        List<Device> lstDevices = MyApplication.getDatabase().deviceDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonDevices = gson.toJson(lstDevices);
        InputStream isDevices = new ByteArrayInputStream(jsonDevices.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isDevices, parameters);

        // Справочник тэгов (tags)
        parameters.setFileNameInZip("tags.json");
        List<Tag> lstTags = MyApplication.getDatabase().tagDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonTags = gson.toJson(lstTags);
        InputStream isTags = new ByteArrayInputStream(jsonTags.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isTags, parameters);

        // Справочник целей (targets)
        parameters.setFileNameInZip("targets.json");
        List<Target> lstTargets = MyApplication.getDatabase().targetDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonTargets = gson.toJson(lstTargets);
        InputStream isTargets = new ByteArrayInputStream(jsonTargets.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isTargets, parameters);

        // Бэкапируем задачи (tasks)
        parameters.setFileNameInZip("tasks.json");
        List<Task> lstTasks = MyApplication.getDatabase().taskDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonTasks = gson.toJson(lstTasks);
        InputStream isTasks = new ByteArrayInputStream(jsonTasks.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isTasks, parameters);

        // Бэкапируем свякзу задач и контекстов (taskcontexts)
        parameters.setFileNameInZip("taskcontexts.json");
        List<TaskContextJoin> lstTaskContextJoins = MyApplication.getDatabase().taskContextJoinDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonTaskContextJoin = gson.toJson(lstTaskContextJoins);
        InputStream isTaskContextJoins = new ByteArrayInputStream(jsonTaskContextJoin.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isTaskContextJoins, parameters);

        // Бэкапируем свякзу задач и тэгов (tasktags)
        parameters.setFileNameInZip("tasktags.json");
        List<TaskTagJoin> lstTaskTagJoins = MyApplication.getDatabase().taskTagJoinDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonTaskTagJoin = gson.toJson(lstTaskTagJoins);
        InputStream isTaskTagJoins = new ByteArrayInputStream(jsonTaskTagJoin.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isTaskTagJoins, parameters);

        // Бэкапируем информацию (informations)
        parameters.setFileNameInZip("informations.json");
        List<Information> lstInformations = MyApplication.getDatabase().informationDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonInformations = gson.toJson(lstInformations);
        InputStream isInformations = new ByteArrayInputStream(jsonInformations.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isInformations, parameters);

    }

    public static void zipFolder(File input, File output) throws ZipException {
        LOG.d("ZipFolder", input, output);
        ZipFile zipFile = new ZipFile(output);

        ZipParameters parameters = new ZipParameters();

        //parameters.setIncludeRootFolder(false);
        //parameters.setCompressionMethod(1);
        //parameters.setCompressionLevel(5);
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        parameters.setCompressionLevel(CompressionLevel.NORMAL);
        parameters.setFileNameInZip("222.txt");

        //parameters.setSourceExternalStream(true);

        /*final File[] files = input.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().startsWith(AppProfile.PROFILE_PREFIX)) {
                    zipFile.addFolder(file, parameters);
                }
            }
        }*/

        Project project = MyApplication.getDatabase().projectDao().getProjectById(3);
        List<Project> lstPojects = MyApplication.getDatabase().projectDao().getAll();

        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //DataItems dataItems = new DataItems();
        String jsonString = gson.toJson(lstPojects);

        /*try(FileOutputStream fileOutputStream =
                    context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //JSONObject jObj = new JSONObject(project);

        InputStream is = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));

        parameters.setFileNameInZip("222.txt");
        zipFile.addStream(is, parameters);

        //Target target = MyApplication.getDatabase().projectDao().getProjectById(3);
        List<Target> lstTargets = MyApplication.getDatabase().targetDao().getAll();

        //Gson gson = new Gson();
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        //DataItems dataItems = new DataItems();
        String jsonString2 = gson.toJson(lstTargets);

        InputStream is2 = new ByteArrayInputStream(jsonString2.getBytes(StandardCharsets.UTF_8));

        parameters.setFileNameInZip("333.txt");
        zipFile.addStream(is2, parameters);

        //zipFile.addFolder(new File("/storage/emulated/0/222/333"), parameters);
        //zipFile.addStream(new ByteArrayInputStream("строка".getBytes(StandardCharsets.UTF_8)), parameters);
        //File file = new File("/storage/emulated/0/222/tst.txt");
        //zipFile.addFile(file);  // /storage/emulated/0/222/2022-10-08-11-23-40-export-backup.zip

        //new ZipFile(output).addStream(is, parameters);
        //zipFile.(input, parameters);
    }

    public static void unZipFolder(File input, File output) throws ZipException, FileNotFoundException {

        //zipFile.extractAll(output.getPath());
        LOG.d("UnZipFolder", input);
        ZipFile zipFile = new ZipFile(input);
        /*for( int i = 0; i < zipFile.getSplitZipFiles().size(); i++) {
            Log.d("FILENAME", zipFile.getSplitZipFiles().get(i).getAbsolutePath());
        }*/

        LocalFileHeader localFileHeader;
        int readLen;
        byte[] readBuffer = new byte[4096];

        InputStream inputStream = new FileInputStream(input);
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            while ((localFileHeader = zipInputStream.getNextEntry()) != null) {
                File extractedFile = new File(localFileHeader.getFileName());
                Log.d("FILENAME", extractedFile.getAbsolutePath());
                /*try (OutputStream outputStream = new FileOutputStream(extractedFile)) {
                    while ((readLen = zipInputStream.read(readBuffer)) != -1) {
                        outputStream.write(readBuffer, 0, readLen);
                    }
                }*/
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
