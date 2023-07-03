package ru.kau.mygtd2.utils;

import static ru.kau.mygtd2.common.MyApplication.context;

import android.util.Log;

import com.cloudrail.si.servicecode.commands.json.jsonsimple.JSONArray;
import com.cloudrail.si.servicecode.commands.json.jsonsimple.JSONObject;
import com.cloudrail.si.servicecode.commands.json.jsonsimple.parser.JSONParser;
import com.cloudrail.si.servicecode.commands.json.jsonsimple.parser.ParseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;

import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.Status;
import ru.kau.mygtd2.common.enums.TypeOfTask;
import ru.kau.mygtd2.jsonconvert.DateConverter;
import ru.kau.mygtd2.jsonconvert.StatusConverter;
import ru.kau.mygtd2.jsonconvert.TypeOfTaskConverter;
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
        parameters.setFileNameInZip("2contexts.json");
        List<Contekst> lstContekst = MyApplication.getDatabase().contextDao().getAll();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonConteksts = gson.toJson(lstContekst);
        InputStream isConteksts = new ByteArrayInputStream(jsonConteksts.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isConteksts, parameters);

        // Справочник устройств (devices)
        parameters.setFileNameInZip("1devices.json");
        List<Device> lstDevices = MyApplication.getDatabase().deviceDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonDevices = gson.toJson(lstDevices);
        InputStream isDevices = new ByteArrayInputStream(jsonDevices.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isDevices, parameters);

        // Справочник тэгов (tags)
        parameters.setFileNameInZip("3tags.json");
        List<Tag> lstTags = MyApplication.getDatabase().tagDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonTags = gson.toJson(lstTags);
        InputStream isTags = new ByteArrayInputStream(jsonTags.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isTags, parameters);

        // Справочник целей (targets)
        parameters.setFileNameInZip("4targets.json");
        List<Target> lstTargets = MyApplication.getDatabase().targetDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonTargets = gson.toJson(lstTargets);
        InputStream isTargets = new ByteArrayInputStream(jsonTargets.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isTargets, parameters);

        // Справочник проектов (projects)
        parameters.setFileNameInZip("5projects.json");
        List<Project> lstProjects = MyApplication.getDatabase().projectDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonProjects = gson.toJson(lstProjects);
        InputStream isProjects = new ByteArrayInputStream(jsonProjects.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isProjects, parameters);

        // Бэкапируем задачи (tasks)
        parameters.setFileNameInZip("6tasks.json");
        List<Task> lstTasks = MyApplication.getDatabase().taskDao().getAll();
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Date.class, new DateConverter())
                .registerTypeAdapter(Status.class, new StatusConverter())
                .registerTypeAdapter(TypeOfTask.class, new TypeOfTaskConverter())
                .create();
        String jsonTasks = gson.toJson(lstTasks);
        InputStream isTasks = new ByteArrayInputStream(jsonTasks.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isTasks, parameters);

        // Бэкапируем свякзу задач и контекстов (taskcontexts)
        parameters.setFileNameInZip("7taskcontexts.json");
        List<TaskContextJoin> lstTaskContextJoins = MyApplication.getDatabase().taskContextJoinDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonTaskContextJoin = gson.toJson(lstTaskContextJoins);
        InputStream isTaskContextJoins = new ByteArrayInputStream(jsonTaskContextJoin.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isTaskContextJoins, parameters);

        // Бэкапируем свякзу задач и тэгов (tasktags)
        parameters.setFileNameInZip("8tasktags.json");
        List<TaskTagJoin> lstTaskTagJoins = MyApplication.getDatabase().taskTagJoinDao().getAll();
        gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonTaskTagJoin = gson.toJson(lstTaskTagJoins);
        InputStream isTaskTagJoins = new ByteArrayInputStream(jsonTaskTagJoin.getBytes(StandardCharsets.UTF_8));
        zipFile.addStream(isTaskTagJoins, parameters);

        // Бэкапируем информацию (informations)
        parameters.setFileNameInZip("9informations.json");
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
        LOG.d("UnZipFolder", input);
        java.util.zip.ZipFile zf;
        try {
            zf = new java.util.zip.ZipFile(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileInputStream fis = new FileInputStream(input);
             BufferedInputStream bis = new BufferedInputStream(fis);
             java.util.zip.ZipInputStream zis = new java.util.zip.ZipInputStream(bis)) {

            ZipEntry ze;

            while ((ze = zis.getNextEntry()) != null) {

                //FileReader reader = new FileReader(context.getCacheDir() + "/" + "contexts.json");
                //InputStreamReader reader = new InputStreamReader(ze.);
                //String jsonString = reader.;
                InputStream is = zf.getInputStream(ze);
                Object obj = new JSONParser().parse(new InputStreamReader(is));
                //JSONArray obj = new JSONParser().parse(new InputStreamReader(is));
                if (obj instanceof JSONArray){
                    //System.out.println("!!!!!!!!!!!!!!!!!");
                    switch (ze.getName()){
                        case "2contexts.json":
                            //List<Contekst> lstContext = new ArrayList<>();
                            for (int i = 0; i < ((JSONArray)obj).size(); i++){
                                Gson gson = new GsonBuilder().create();
                                Contekst contekst = gson.fromJson(((JSONArray)obj).get(i).toString(), Contekst.class);
                                // Обновляем в базе данных
                                MyApplication.getDatabase().contextDao().insert(contekst);
                                // --------------------------------------------------
                            }
                            break;

                        case "1devices.json":


                            for (int i = 0; i < ((JSONArray)obj).size(); i++){
                                Gson gson = new GsonBuilder().create();
                                Device device = gson.fromJson(((JSONArray)obj).get(i).toString(), Device.class);

                                Device currentDevice = MyApplication.getDatabase().deviceDao().getCurrentDevice();
                                currentDevice.setGuid(device.getGuid());


                                MyApplication.getDatabase().deviceDao().delete(currentDevice);

                                MyApplication.getDatabase().deviceDao().insert(device);

                            }

                            break;

                        case "8informations.json":
                            //List<Contekst> lstContext = new ArrayList<>();
                            for (int i = 0; i < ((JSONArray)obj).size(); i++){
                                Gson gson = new GsonBuilder().create();
                                Information information = gson.fromJson(((JSONArray)obj).get(i).toString(), Information.class);
                                // Обновляем в базе данных
                                //Device currentDevice = MyApplication.getDatabase().deviceDao().getCurrentDevice();
                                //currentDevice.setGuid(device.getGuid());
                                // Обновляем в базе данных
                                MyApplication.getDatabase().informationDao().insert(information);
                                // --------------------------------------------------
                            }
                            break;

                        case "3tags.json":
                            //List<Contekst> lstContext = new ArrayList<>();
                            for (int i = 0; i < ((JSONArray)obj).size(); i++){
                                Gson gson = new GsonBuilder().create();
                                Tag tag = gson.fromJson(((JSONArray)obj).get(i).toString(), Tag.class);
                                // Обновляем в базе данных
                                //Device currentDevice = MyApplication.getDatabase().deviceDao().getCurrentDevice();
                                //currentDevice.setGuid(device.getGuid());
                                // Обновляем в базе данных
                                MyApplication.getDatabase().tagDao().insert(tag);
                                // --------------------------------------------------
                            }
                            break;

                        case "4targets.json":
                            //List<Contekst> lstContext = new ArrayList<>();
                            for (int i = 0; i < ((JSONArray)obj).size(); i++){
                                Gson gson = new GsonBuilder().create();
                                Target target = gson.fromJson(((JSONArray)obj).get(i).toString(), Target.class);
                                // Обновляем в базе данных
                                //Device currentDevice = MyApplication.getDatabase().deviceDao().getCurrentDevice();
                                //currentDevice.setGuid(device.getGuid());
                                // Обновляем в базе данных
                                MyApplication.getDatabase().targetDao().insert(target);
                                // --------------------------------------------------
                            }
                            break;

                        case "5projects.json":
                            //List<Contekst> lstContext = new ArrayList<>();
                            for (int i = 0; i < ((JSONArray)obj).size(); i++){
                                Gson gson = new GsonBuilder().create();
                                Project project = gson.fromJson(((JSONArray)obj).get(i).toString(), Project.class);
                                // Обновляем в базе данных
                                //Device currentDevice = MyApplication.getDatabase().deviceDao().getCurrentDevice();
                                //currentDevice.setGuid(device.getGuid());
                                // Обновляем в базе данных
                                MyApplication.getDatabase().projectDao().insert(project);
                                // --------------------------------------------------
                            }
                            break;

                        case "7taskcontexts.json":
                            //List<Contekst> lstContext = new ArrayList<>();
                            for (int i = 0; i < ((JSONArray)obj).size(); i++){
                                Gson gson = new GsonBuilder().create();
                                TaskContextJoin taskContextJoin = gson.fromJson(((JSONArray)obj).get(i).toString(), TaskContextJoin.class);
                                // Обновляем в базе данных
                                //Device currentDevice = MyApplication.getDatabase().deviceDao().getCurrentDevice();
                                //currentDevice.setGuid(device.getGuid());
                                // Обновляем в базе данных
                                MyApplication.getDatabase().taskContextJoinDao().insert(taskContextJoin);
                                // --------------------------------------------------
                            }
                            break;

                        case "8tasktags.json":
                            //List<Contekst> lstContext = new ArrayList<>();
                            for (int i = 0; i < ((JSONArray)obj).size(); i++){
                                Gson gson = new GsonBuilder().create();
                                TaskTagJoin taskTagJoin = gson.fromJson(((JSONArray)obj).get(i).toString(), TaskTagJoin.class);
                                // Обновляем в базе данных
                                //Device currentDevice = MyApplication.getDatabase().deviceDao().getCurrentDevice();
                                //currentDevice.setGuid(device.getGuid());
                                // Обновляем в базе данных
                                MyApplication.getDatabase().taskTagJoinDao().insert(taskTagJoin);
                                // --------------------------------------------------
                            }
                            break;

                        case "6tasks.json":
                            //List<Contekst> lstContext = new ArrayList<>();
                            for (int i = 0; i < ((JSONArray)obj).size(); i++){
                                Gson gson = new GsonBuilder()
                                        .registerTypeAdapter(Date.class, new DateConverter())
                                        .registerTypeAdapter(Status.class, new StatusConverter())
                                        .registerTypeAdapter(TypeOfTask.class, new TypeOfTaskConverter())
                                        .create();
                                Task task = gson.fromJson(((JSONArray)obj).get(i).toString(), Task.class);
                                // Обновляем в базе данных
                                //Device currentDevice = MyApplication.getDatabase().deviceDao().getCurrentDevice();
                                //currentDevice.setGuid(device.getGuid());
                                // Обновляем в базе данных
                                MyApplication.getDatabase().taskDao().insert(task);
                                // --------------------------------------------------
                            }
                            System.out.format("Конец импорта задач");
                            break;

                    }
                }
                /*for(Iterator iterator = ((JSONObject)obj).keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    System.out.println(((JSONObject)obj).get(key));
                }*/

                //JSONObject jsonObject = (JSONObject) obj;
                //JSONArray jsonarr = (JSONArray) obj;
                //JSONArray jsonarr = (JSONArray) new JSONParser().parse(new InputStreamReader(is));
                //for (int i = 0; i)

                System.out.format("FILENAME AND SIZE: ",
                        ze.getName(), ze.getSize());
                        /*,
                        LocalDate.ofEpochDay(ze.getTime() / MILLS_IN_DAY));*/
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static void unZipFolder_old(File input, File output) throws ZipException, FileNotFoundException {

        //zipFile.extractAll(output.getPath());
        LOG.d("UnZipFolder", input);

        LocalFileHeader localFileHeader;
        int readLen;
        byte[] readBuffer = new byte[4096];

        InputStream inputStream = new FileInputStream(input);
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            while ((localFileHeader = zipInputStream.getNextEntry()) != null) {
                File extractedFile = new File(localFileHeader.getFileName());
                Log.d("FILENAME", extractedFile.getPath());

                //JSONArray jsonarr = (JSONArray) new JSONParser().parse();

                try (OutputStream outputStream = new FileOutputStream(context.getCacheDir() + "/" + extractedFile)) {
                    while ((readLen = zipInputStream.read(readBuffer)) != -1) {
                        outputStream.write(readBuffer, 0, readLen);
                        //System.out.println(readBuffer);
                    }
                }

                //InputStreamReader isr = new InputStreamReader( localFileHeader.getFileName());
                //ZipEntry zipEntry = zipFile.getEntry("fileName.txt");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            //InputStream is = new FileInputStream(context.getCacheDir() + "/" + "contexts.json");
            FileReader reader = new FileReader(context.getCacheDir() + "/" + "contexts.json");
            //String jsonString = reader.;
            Object obj = new JSONParser().parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            //JSONArray jsonarr = (JSONArray) new JSONParser().parse(reader);
            System.out.println("123123123");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*ZipFile zipFile = new ZipFile(input);
        for( int i = 0; i < zipFile.getSplitZipFiles().size(); i++) {
            Log.d("FILENAME", zipFile.getSplitZipFiles().get(i).getAbsolutePath());
            Log.d("FILENAME", zipFile.getSplitZipFiles().get(i).getName());
        }

        LocalFileHeader localFileHeader;
        int readLen;
        byte[] readBuffer = new byte[4096];*/

        /*InputStream inputStream = new FileInputStream(input);
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            while ((localFileHeader = zipInputStream.getNextEntry()) != null) {
                File extractedFile = new File(localFileHeader.getFileName());
                Log.d("FILENAME", extractedFile.getAbsolutePath());
                Log.d("FILENAME", extractedFile.getPath() + "  999  " + extractedFile.getName());
                //InputStream is = new FileInputStream(extractedFile);
                //JSONArray jsonarr = (JSONArray) new JSONParser().parse(new InputStreamReader(is));
                try (OutputStream outputStream = new FileOutputStream(extractedFile)) {
                    while ((readLen = zipInputStream.read(readBuffer)) != -1) {
                        //outputStream.write(readBuffer, 0, readLen);
                        System.out.println(readBuffer);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        /*catch (ParseException e) {
            throw new RuntimeException(e);
        }*/


    }

}
