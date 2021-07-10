package ru.kau.mygtd2.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.apg.mobile.roundtextview.RoundTextView;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.PrStatus;
import ru.kau.mygtd2.dialogs.Dialogs;
import ru.kau.mygtd2.interfaces.DialogProjectChoice;
import ru.kau.mygtd2.interfaces.DialogProjectStatusChoice;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.ProjectStatus;

public class AddProjectFragment extends Fragment implements DialogProjectChoice, DialogProjectStatusChoice {

    private TextView parentProjectTitle;
    private RoundTextView rtvProjectStatus;
    private long parentProjectId = 0L;
    private ProjectStatus projectStatus;
    Project parentProject = null;
    Project editProject = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.addproject_fragment, null);

        final TextView txtProjectTitle = (TextView) rootView.findViewById(R.id.inputProjectTitle);
        final TextView txtProjectInfoTitle = (TextView) rootView.findViewById(R.id.inputProjectinfoTitle);
        final ImageView ivchoiceProjectStatus = (ImageView) rootView.findViewById(R.id.statuschoise);
        rtvProjectStatus = (RoundTextView) rootView.findViewById(R.id.statusProject);
        getProjectStatus(MyApplication.getDatabase().projectStatusDao().getById(1));

        Bundle arguments = getArguments();

        parentProjectTitle = (TextView) rootView.findViewById(R.id.parentProject);
        if (arguments != null && arguments.containsKey("editproject")) {
            editProject = (Project) arguments.getSerializable("editproject");
            txtProjectTitle.setText(editProject.getTitle());
            txtProjectInfoTitle.setText(editProject.getDescription());
            getProjectStatus(MyApplication.getDatabase().projectStatusDao().getById(editProject.getPrStatus().Value));
            parentProject = MyApplication.getDatabase().projectDao().getProjectById(editProject.getParentid());
            if (parentProject != null) {
                parentProjectTitle.setText(parentProject.getTitle());
            }
        }

        if (arguments != null && arguments.containsKey("parentproject")) {

            parentProject = (Project) arguments.getSerializable("parentproject");

            //parentProjectTitle = (TextView) rootView.findViewById(R.id.parentProject);

            if (parentProject != null) {
                txtProjectTitle.setText(parentProject.getTitle());
                txtProjectInfoTitle.setText(parentProject.getDescription());
                parentProjectTitle.setText(MyApplication.getDatabase().projectDao().getProjectById(parentProject.getParentid()).getTitle());
                getProjectStatus(MyApplication.getDatabase().projectStatusDao().getById(parentProject.getPrStatus().Value));
            }
        }



        final ImageView projectchoise = (ImageView) rootView.findViewById(R.id.parentprojectchoise);

        projectchoise.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Dialogs.choiseProjectDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
            }
        });

        ivchoiceProjectStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.showProjectStatusDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //TagDaoAbs.deleteTag(tagName);
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
            }
        });

        /*Dialogs.showTypesTaskDialog(getActivity(), new Runnable() {

            @Override
            public void run() {
                //TagDaoAbs.deleteTag(tagName);
                //tagsRunnable.run();
                //EventBus.getDefault().post(new NotifyAllFragments());
            }
        });*/

        Button imgbtnsaveproject = (Button) rootView.findViewById(R.id.btnsaveproject);
        Project finalParentProject = parentProject;
        imgbtnsaveproject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (editProject == null) {
                    Project project = new Project();
                    project.setTitle(txtProjectTitle.getText().toString());
                    project.setSearchtitle(project.getTitle().toUpperCase());
                    project.setDescription(txtProjectInfoTitle.getText().toString());
                    project.setParentid(parentProject.getId());
                    project.setPrStatus(projectStatus);
                    try {
                        MyApplication.getDatabase().projectDao().insert(project);
                        //ViewUtils.viewPositiveToast(getContext(), getLayoutInflater(), String.valueOf(R.string.projectcreated), Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 0);
                        Toasty.success(getContext(), getString(R.string.projectcreated), Toast.LENGTH_LONG, true).show();
                    } catch (Exception e) {
                        Toasty.error(getContext(), getString(R.string.projectcreatederror), Toast.LENGTH_LONG, true).show();
                        //ViewUtils.viewNegativeToast(getContext(), getLayoutInflater(), String.valueOf(R.string.projectcreatederror), Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 0);
                    }
                } else {

                    editProject.setTitle(txtProjectTitle.getText().toString());
                    editProject.setSearchtitle(editProject.getTitle().toUpperCase());
                    editProject.setDescription(txtProjectInfoTitle.getText().toString());
                    editProject.setParentid(parentProject.getId());
                    editProject.setPrStatus(PrStatus.ACTIVE);
                    try {
                        MyApplication.getDatabase().projectDao().update(editProject);
                        //ViewUtils.viewPositiveToast(getContext(), getLayoutInflater(), String.valueOf(R.string.projectcreated), Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 0);
                        Toasty.success(getContext(), getString(R.string.projectedited), Toast.LENGTH_LONG, true).show();
                    } catch (Exception e) {
                        Toasty.error(getContext(), getString(R.string.projecteditederror), Toast.LENGTH_LONG, true).show();
                        //ViewUtils.viewNegativeToast(getContext(), getLayoutInflater(), String.valueOf(R.string.projectcreatederror), Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 0);
                    }
                }
                closeFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        }
        );

        Button imgbtncancel = (Button) rootView.findViewById(R.id.btncancelproject);
        //Project finalParentProject = parentProject;
        imgbtncancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });



        return rootView;
    }

    @Override
    public void getProject(Project project) {

        parentProjectTitle.setText(project.getTitle());
        parentProjectId = project.getId();
        parentProject = MyApplication.getDatabase().projectDao().getProjectById(parentProjectId);
        //Log.e("444444444", node.getName());
    }

    private void closeFragment(){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public void getProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
        rtvProjectStatus.setText(projectStatus.getTitle());
        rtvProjectStatus.setTextSize(18);
        rtvProjectStatus.setCorner(18, 18, 18, 18);
        rtvProjectStatus.setBgColor(Color.parseColor(projectStatus.getColor()));
    }
}
