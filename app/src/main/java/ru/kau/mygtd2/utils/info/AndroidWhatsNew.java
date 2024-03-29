package ru.kau.mygtd2.utils.info;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

//import ru.kau.mygtd2.BuildConfig;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.dialogs.AlertDialogs;
import ru.kau.mygtd2.utils.AppProfile;
import ru.kau.mygtd2.utils.AppState;
import ru.kau.mygtd2.utils.Apps;
import ru.kau.mygtd2.utils.ExtUtils;
import ru.kau.mygtd2.utils.LOG;
import ru.kau.mygtd2.utils.OPDS;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.Urls;

import static android.os.Build.VERSION.SDK_INT;

public class AndroidWhatsNew {

    public static final String DETAIL_URL_RU = (SDK_INT >= 24 ? "https" : "http") + "://librera.mobi/wiki";
    private static final String BETA_TXT = "changelog.txt";
    private static final String BETA = "beta-";
    private static final String WIKI_URL = (SDK_INT >= 24 ? "https" : "http") + "://librera.mobi/wiki/what-is-new/%s/";

    public static String getLangUrl(Context c) {
        String versionName = Apps.getVersionName(c);
        String shortVersion = versionName.substring(0, versionName.lastIndexOf("."));
        String url = String.format(WIKI_URL, shortVersion);

        List<String> lns = Arrays.asList("ar", "de", "es", "fr", "it", "pt", "ru", "zh");
        String appLang = AppState.get().appLang;
        if (appLang.equals(AppState.MY_SYSTEM_LANG)) {
            appLang = Urls.getLangCode();
        }

        if (lns.contains(appLang)) {
            url += appLang;
        }

        // url += "?utm_p=" + Apps.getPackageName(c);
        // url += "&utm_v=" + Apps.getVersionName(c);
        // url += "&utm_ln=" + appLang;
        // url += "&utm_beta=" + AppsConfig.IS_BETA;

        url += "#" + shortVersion.replace(".", "");

        LOG.d("getLangUrl", url);
        return url;

    }

    public static String getLangUrl1(Context c) {

        String versionName = Apps.getVersionName(c);
        String shortVersion = versionName.substring(0, versionName.lastIndexOf("."));
        String url = String.format(WIKI_URL, shortVersion);

        List<String> lns = Arrays.asList("ar", "de", "es", "fr", "it", "pt", "ru", "zh");
        String appLang = AppState.get().appLang;
        if (appLang.equals(AppState.MY_SYSTEM_LANG)) {
            appLang = Urls.getLangCode();
        }

        if (lns.contains(appLang)) {
            url += appLang;
        }

        url += "?utm_p=" + Apps.getPackageName(c);
        url += "&utm_v=" + Apps.getVersionName(c);
        url += "&utm_ln=" + appLang;
        //url += "&utm_beta=" + BuildConfig.IS_BETA;

        url += "#" + shortVersion.replace(".", "");

        LOG.d("getLangUrl", url);
        return url;

    }

    public static void show2(final Context c) {

        /*if (BuildConfig.IS_FDROID) {
            Urls.open(c, getLangUrl(c));
            return;
        }*/

        View inflate = LayoutInflater.from(c).inflate(R.layout.whatsnew2, null, false);

        final WebView wv = inflate.findViewById(R.id.webView2);
        wv.getSettings().setUserAgentString(OPDS.USER_AGENT);
        wv.getSettings().setJavaScriptEnabled(true);


        wv.loadUrl(getLangUrl(c));

        wv.setFocusable(true);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

        TextView wiki = (TextView) inflate.findViewById(R.id.wiki);
        TxtUtils.underlineTextView(wiki);
        wiki.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Urls.open(c, getLangUrl(c));
                } catch (Exception e) {
                    LOG.e(e);
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setView(inflate);
        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.rate_us, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                rateIT(c);
            }
        });

        builder.show();

    }

    private static void show1(final Context c) {
        if (true) {
            show2(c);
            return;
        }
        String versionName = Apps.getVersionName(c);
        View inflate = LayoutInflater.from(c).inflate(R.layout.whatsnew, null, false);

        final TextView notes = (TextView) inflate.findViewById(R.id.textNotes);
        View fontSectionDivider = inflate.findViewById(R.id.fontSectionDivider);
        TextView textRateIt = (TextView) inflate.findViewById(R.id.textRateIt);
        TxtUtils.underlineTextView(textRateIt);
        textRateIt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rateIT(c);
            }
        });

        TextView detailedChangelog = (TextView) inflate.findViewById(R.id.detailedChangelog);
        TxtUtils.underlineTextView(detailedChangelog);
        detailedChangelog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Urls.open(c, DETAIL_URL_RU);
                } catch (Exception e) {
                    LOG.e(e);
                }
            }
        });

        ((View) textRateIt.getParent()).setVisibility(View.GONE);
        fontSectionDivider.setVisibility(View.GONE);

        String textNotes = "loading...";
        try {
            String langCode = AppState.get().appLang;
            textNotes = getWhatsNew(c, langCode);
        } catch (Exception e) {
            try {
                textNotes = getWhatsNew(c, Urls.getLangCode());
            } catch (Exception e1) {
                textNotes = "erorr...";
            }
            LOG.d(e);
        }

        notes.setText(textNotes);

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(c.getString(R.string.what_is_new_in) + " " + Apps.getApplicationName(c) + " " + versionName);
        builder.setView(inflate);
        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        if (false) {
            builder.setPositiveButton(R.string.write_feedback, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    rateIT(c);
                }
            });
        }

        builder.show();
    }

    public static String getWhatsNew(Context c, String langCode) throws IOException {
        InputStream input = c.getResources().getAssets().open("whatsnew/" + langCode + ".txt");
        return steamToString(input);

    }

    public static void checkForNewBeta(final Activity c) {
        /*if (!BuildConfig.IS_BETA) {
            return;
        }*/


        final String url = "https://t.me/s/LibreraBeta";


        LOG.d("checkForNewBeta");
        new Thread() {
            @Override
            public void run() {

                try {
                    final String resultHTTP = OPDS.getHttpResponseNoException(url);
                    String result = Jsoup.parse(resultHTTP).select("div[class=tgme_widget_message_document_title]").last().text();
                    LOG.d("checkForNewBeta result 2", result);
                    if (!result.contains(".apk")) {
                        return;
                    }

                    result = result.replace("Librera Beta-","");
                    result = result.replace("Librera Alpha-","");
                    result = result.replace("-arm64.apk","");
                    result = result.replace("-arm.apk","");

                    final String resultFinal = result;
                    LOG.d("checkForNewBeta result 3", result);


                    c.runOnUiThread(() -> {
                        try {


                            if (resultFinal == null || TxtUtils.isEmpty("" + resultFinal)) {
                                return;
                            }

                            final String my = Apps.getVersionName(c);
                            if (my.equals(resultFinal)) {
                                return;
                            }

                            AlertDialogs.showDialog(c, c.getString(R.string.new_beta_version_available) + "\n" + resultFinal, c.getString(R.string.download), new Runnable() {

                                @Override
                                public void run() {
                                    Urls.open(c, "https://t.me/LibreraBeta/");
                                }
                            });
                        } catch (Exception e) {
                            LOG.e(e);
                        }
                    });
                } catch (Exception e) {
                    LOG.e(e);
                }
            }
        }.start();


    }

    public static void checkWhatsNew(final Context c) {
        if (!AppState.get().isShowWhatIsNewDialog) {
            return;
        }
        String currentVersion = Apps.getVersionName(c);
        String oldVersion = AppState.get().versionNew;

        if (TxtUtils.isEmpty(oldVersion) || !isEqualsFirstSecondDigit(currentVersion, oldVersion)) {
            show2(c);
            AppState.get().versionNew = currentVersion;
            AppProfile.save(c);
        }
    }

    public static void rateIT(Context c) {
        try {
            Urls.open(c, "market://details?id=" + c.getPackageName());
        } catch (Exception e) {
            Urls.open(c, "https://play.google.com/store/apps/details?id=" + c.getPackageName());
            LOG.e(e);
        }
    }

    public static String steamToString(InputStream input) {
        StringBuilder content = new StringBuilder();
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(ExtUtils.upperCaseFirst(line) + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            LOG.e(e);
        }
        return content.toString();
    }

    public static boolean isEqualsFirstSecondDigit(String first, String second) {
        try {
            LOG.d("Compare first second", first, second);
            first = first.replace(BETA, "");
            second = second.replace(BETA, "");
            String[] splitFist = first.split(Pattern.quote("."));
            String[] splitSecond = second.split(Pattern.quote("."));

            if (splitFist[0].equals(splitSecond[0]) && splitFist[1].equals(splitSecond[1])) {
                LOG.d("Compare first second", true);
                return true;
            }

            LOG.d("Compare first second", false);

            return false;
        } catch (Exception e) {
            LOG.e(e);
            return true;
        }
    }

}

