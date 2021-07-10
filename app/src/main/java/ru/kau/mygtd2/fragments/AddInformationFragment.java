package ru.kau.mygtd2.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.apg.mobile.roundtextview.RoundTextView;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.TypeOfInfo;
import ru.kau.mygtd2.dialogs.Dialogs;
import ru.kau.mygtd2.interfaces.DialogStatusOfInfoChoice;
import ru.kau.mygtd2.interfaces.DialogTypeOfInfoChoice;
import ru.kau.mygtd2.objects.InfoStatus;
import ru.kau.mygtd2.objects.InfoTypes;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.utils.Const;
import ru.kau.mygtd2.utils.Utils;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHSECONDS;
import static ru.kau.mygtd2.utils.Const.MYDEVICEID;
import static ru.kau.mygtd2.utils.Utils.getImageResourceInfoType;


//import android.support.v4.app.Fragment;

public class AddInformationFragment extends Fragment implements DialogTypeOfInfoChoice, DialogStatusOfInfoChoice {

    private Information infoUpdate = null;

    private LinearLayoutCompat linfotype;
    private TextView typeOfInfoTitle;
    private RoundTextView statusOfInfoTitle;
    private String infoGuid;

    private InfoTypes infoTypes = MyApplication.getDatabase().infoTypesDao().getById(3L);
    private InfoStatus infoStatus = MyApplication.getDatabase().infoStatusDao().getById(1);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.addinfo_fragment, null);

        Bundle arguments = getArguments();


        TextView inputInfoShort = (TextView)rootView.findViewById(R.id.inputInfoShort);
        TextView inputInfoFull = (TextView)rootView.findViewById(R.id.inputInfoFull);
        linfotype = (LinearLayoutCompat) rootView.findViewById(R.id.addinfotype);
        //typeOfInfoTitle = (TextView)rootView.findViewById(R.id.infoTypeTitle);
        statusOfInfoTitle = rootView.findViewById(R.id.infostatusTitle);

        infoTypes = MyApplication.getDatabase().infoTypesDao().getById(3L);
        infoStatus = MyApplication.getDatabase().infoStatusDao().getById(1);





        if (arguments != null && arguments.containsKey("information")) {
            infoUpdate = (Information) arguments.getSerializable("information");
            inputInfoShort.setText(infoUpdate.getTitle());
            inputInfoFull.setText(infoUpdate.getDescription());
            infoStatus = MyApplication.getDatabase().infoStatusDao().getById(infoUpdate.getIdstatus());
            infoTypes = MyApplication.getDatabase().infoTypesDao().getById(infoUpdate.getTypeOfInfo().Value);
            infoGuid = infoUpdate.getGuid();
            //getStatusOfInfo(infoStatus);
            //getTypeOfInfo(MyApplication.getDatabase().infoTypesDao().getById(infoUpdate.getTypeOfInfo().Value));

        }
        getTypeOfInfo(infoTypes);
        getStatusOfInfo(infoStatus);
        final ImageView typesofinfochoise = (ImageView) rootView.findViewById(R.id.typesofinfochoise);

        typesofinfochoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogs.showTypesInfoDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //TagDaoAbs.deleteTag(tagName);
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });

            }
        });

        final ImageView statusofinfochoise = (ImageView) rootView.findViewById(R.id.statusofinfochoise);
        statusofinfochoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogs.showStatusInfoDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //TagDaoAbs.deleteTag(tagName);
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });

            }
        });




        Button imgbtnsaveinfo = (Button) rootView.findViewById(R.id.btnsaveinfo);
        imgbtnsaveinfo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (arguments != null && arguments.containsKey("information")) {
                    infoUpdate.setTitle(inputInfoShort.getText().toString());
                    infoUpdate.setSearchtitle(infoUpdate.getTitle().toUpperCase());
                    infoUpdate.setDescription(inputInfoFull.getText().toString());
                    infoUpdate.setTypeOfInfo(TypeOfInfo.from(infoTypes.getId()));
                    infoUpdate.setIdstatus(infoStatus.getId());
                    infoUpdate.setGuid(infoGuid);
                    infoUpdate.setDeviceguid(infoUpdate.getDeviceguid());
                    try{
                        MyApplication.getDatabase().informationDao().update(infoUpdate);
                        Toasty.success(getContext(), getString(R.string.infoupdated), Toast.LENGTH_LONG, true).show();
                        //ViewUtils.viewPositiveToast(getContext(), getLayoutInflater(), String.valueOf(R.string.infoupdated), Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 0);
                    } catch (Exception e) {
                        Toasty.success(getContext(), getString(R.string.infoupdatederror), Toast.LENGTH_LONG, true).show();
                        //ViewUtils.viewNegativeToast(getContext(), getLayoutInflater(), String.valueOf(R.string.infoupdatederror), Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 0);

                    }

                } else {
                    Information information = new Information();
                    information.setId(Utils.getLastInfoId());
                    information.setTitle(inputInfoShort.getText().toString());
                    information.setSearchtitle(information.getTitle().toUpperCase());
                    information.setDescription(inputInfoFull.getText().toString());
                    information.setDateCreate(new Date());
                    information.setDateCreateStr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHSECONDS, information.getDateCreate()));
                    information.setIdstatus(infoStatus.getId());
                    information.setTypeOfInfo(TypeOfInfo.from(infoTypes.getId()));
                    information.setDeviceguid(MYDEVICEID);
                    information.setGuid(UUID.randomUUID().toString());
                    try {
                        MyApplication.getDatabase().informationDao().insert(information);
                        Toasty.success(getContext(), getString(R.string.infocreated), Toast.LENGTH_LONG, true).show();
                        //ViewUtils.viewPositiveToast(getContext(), getLayoutInflater(), (String) getResources().getText(R.string.infocreated), Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 0);
                    } catch (Exception e) {

                        Toasty.error(getContext(), getString(R.string.infocreatederror), Toast.LENGTH_LONG, true).show();
                        //ViewUtils.viewNegativeToast(getContext(), getLayoutInflater(), (String) getResources().getText(R.string.infocreatederror), Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 0);

                    }
                }

                closeFragment();
                FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                fm.popBackStack();

            }
        });

        Button imgbtncancelinfo = (Button) rootView.findViewById(R.id.btncancelinfo);
        imgbtncancelinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        return rootView;
    }

    private void closeFragment(){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public void getTypeOfInfo(InfoTypes infotype) {



        linfotype.removeAllViews();


        LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);

        linfotype.setLayoutParams(lParams);
        //ltasktype.setGravity(Gravity.CENTER | Gravity.BOTTOM);

        ImageView iv = new ImageView(getActivity());
        //iv.setImageResource(R.drawable.pririty);
        iv.setImageResource(getImageResourceInfoType(TypeOfInfo.from(infotype.getId())));
        iv.setScaleType(ImageView.ScaleType.FIT_XY);

        //iv.setMinimumWidth(25);
        //iv.setMinimumHeight(25);
        //iv.getLayoutParams().width = 25;
        //iv.getLayoutParams().height = 25;
        //iv.setMaxWidth(25);
        //iv.setMaxHeight(25);
        //iv.setla



        //int color = Color.parseColor("#fafafa");
        //iv.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);
        //iv.setColorFilter(color);
        //iv.set

        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH + 10, Const.DEFAULT_ICON_HEIGHT2 + 10);
        iv.setLayoutParams(lParams);
        //iv.setForegroundGravity(Gravity.FILL);
        //iv.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        linfotype.addView(iv);

        TextView tv = new TextView(getActivity());

        tv.setText(infotype.getTitle());
        tv.setTextSize(16);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        linfotype.addView(tv);

        //infoTypes.setId(infotype.getId());
        infoTypes = MyApplication.getDatabase().infoTypesDao().getById(infotype.getId());
    }

    @Override
    public void getStatusOfInfo(InfoStatus infoStat) {



        //linfost.removeAllViews();


        //LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);

        //linfotype.setLayoutParams(lParams);

        statusOfInfoTitle.setCorner(20);
        statusOfInfoTitle.setBgColor(Color.parseColor(infoStat.getColor()));

        statusOfInfoTitle.setText(infoStat.getTitle());
        //statusOfInfoTitle.setTextSize(16);
        statusOfInfoTitle.setTextSize(16);
        statusOfInfoTitle.setTypeface(Typeface.DEFAULT_BOLD);

        //infoStatus.setId(infoStat.getId());
        infoStatus = MyApplication.getDatabase().infoStatusDao().getById(infoStat.getId());
    }
}
