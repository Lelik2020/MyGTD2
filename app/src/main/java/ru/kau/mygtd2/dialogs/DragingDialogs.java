package ru.kau.mygtd2.dialogs;

public class DragingDialogs {


    /*
    public static DragingPopup projectChoice() {



    }
    */
    /*

    public static DragingPopup showContent(final FrameLayout anchor, final DocumentController controller) {

        final OnItemClickListener onClickContent = new OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                final OutlineLinkWrapper link = (OutlineLinkWrapper) parent.getItemAtPosition(position);
                // if (true) {
                // int linkPage = MuPdfLinks.getLinkPageWrapper(link.docHandle, link.linkUri) +
                // 1;
                // LOG.d("targetUrl page", linkPage, link.linkUri);
                // controller.onGoToPage(linkPage);
                //
                // return;
                // }

                if (link.targetPage != -1) {
                    int pageCount = controller.getPageCount();
                    if (link.targetPage < 1 || link.targetPage > pageCount) {
                        Toast.makeText(anchor.getContext(), "no", Toast.LENGTH_SHORT).show();
                    } else {
                        controller.onGoToPage(link.targetPage);
                        // ((ListView) parent).requestFocusFromTouch();
                        // ((ListView) parent).setSelection(position);

                    }
                    return;
                }

            }
        };
        DragingPopup dragingPopup = new DragingPopup(anchor.getContext().getString(R.string.content_of_book), anchor, 300, 400) {

            @Override
            public View getContentView(LayoutInflater inflater) {
                View view = inflater.inflate(R.layout.dialog_recent_books, null, false);

                LinearLayout attachemnts = (LinearLayout) view.findViewById(R.id.mediaAttachments);
                List<String> mediaAttachments = controller.getMediaAttachments();
                if (mediaAttachments != null && !mediaAttachments.isEmpty()) {
                    view.findViewById(R.id.mediaAttachmentsScroll).setVisibility(View.VISIBLE);
                    for (final String fname : mediaAttachments) {
                        String[] split = fname.split(",");
                        final String nameFull = split[0];
                        String name = nameFull;
                        if (name.contains("/")) {
                            name = name.substring(name.lastIndexOf("/") + 1);
                        }
                        long size = Long.parseLong(split[1]);

                        TextView t = new TextView(anchor.getContext());
                        t.setText(TxtUtils.underline(name + " (" + ExtUtils.readableFileSize(size) + ")"));
                        t.setPadding(Dips.dpToPx(2), Dips.dpToPx(2), Dips.dpToPx(2), Dips.dpToPx(2));
                        t.setBackgroundResource(R.drawable.bg_clickable);
                        attachemnts.addView(t);
                        t.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                new AsyncTask<Void, Void, File>() {
                                    ProgressDialog dialog;

                                    @Override
                                    protected void onPreExecute() {
                                        dialog = ProgressDialog.show(controller.getActivity(), "", controller.getString(R.string.msg_loading));
                                    };

                                    @Override
                                    protected File doInBackground(Void... params) {
                                        return EpubExtractor.extractAttachment(controller.getCurrentBook(), nameFull);
                                    }

                                    @Override
                                    protected void onPostExecute(File aPath) {
                                        try {
                                            dialog.dismiss();
                                            if (aPath != null && aPath.isFile()) {
                                                LOG.d("Try to open path", aPath);
                                                if (ExtUtils.isAudioContent(aPath.getPath())) {
                                                    TTSEngine.get().mp3Destroy();
                                                    AppState.get().mp3BookPath = aPath.getPath();
                                                    AppState.get().mp3seek = 0;
                                                    TTSService.playBookPage(controller.getCurentPageFirst1() - 1, controller.getCurrentBook().getPath(), "", controller.getBookWidth(), controller.getBookHeight(), AppState.get().fontSizeSp, controller.getTitle());
                                                } else {
                                                    ExtUtils.openWith(anchor.getContext(), aPath);
                                                }

                                            } else {
                                                Toast.makeText(controller.getActivity(), R.string.msg_unexpected_error, Toast.LENGTH_LONG).show();
                                            }
                                        } catch (Exception e) {
                                            LOG.e(e);
                                        }

                                    };
                                }.execute();

                            }
                        });
                    }
                } else {
                    view.findViewById(R.id.mediaAttachmentsScroll).setVisibility(View.GONE);
                }

                final ListView contentList = (ListView) view.findViewById(R.id.contentList);
                contentList.setSelector(android.R.color.transparent);
                contentList.setVerticalScrollBarEnabled(false);

                final Runnable showOutline = new Runnable() {

                    @Override
                    public void run() {
                        controller.getOutline(new ResultResponse<List<OutlineLinkWrapper>>() {
                            @Override
                            public boolean onResultRecive(final List<OutlineLinkWrapper> outline) {
                                contentList.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        if (outline != null && outline.size() > 0) {
                                            contentList.clearChoices();
                                            OutlineLinkWrapper currentByPageNumber = OutlineHelper.getCurrentChapter(controller);
                                            final OutlineAdapter adapter = new OutlineAdapter(controller.getActivity(), outline, currentByPageNumber, controller.getPageCount());
                                            contentList.setAdapter(adapter);
                                            contentList.setOnItemClickListener(onClickContent);
                                            contentList.setSelection(adapter.getItemPosition(currentByPageNumber) - 3);
                                        }
                                    }
                                });
                                return false;
                            }
                        }, true);

                    }
                };
                contentList.postDelayed(showOutline, 50);

                if (false && BookType.FB2.is(controller.getCurrentBook().getPath())) {
                    setTitlePopupIcon(AppState.get().outlineMode == AppState.OUTLINE_ONLY_HEADERS ? R.drawable.glyphicons_114_justify : R.drawable.glyphicons_114_justify_sub);
                    titlePopupMenu = new MyPopupMenu(controller.getActivity(), null);

                    List<Integer> names = Arrays.asList(R.string.headings_only, R.string.heading_and_subheadings);
                    final List<Integer> icons = Arrays.asList(R.drawable.glyphicons_114_justify, R.drawable.glyphicons_114_justify_sub);
                    final List<Integer> actions = Arrays.asList(AppState.OUTLINE_ONLY_HEADERS, AppState.OUTLINE_HEADERS_AND_SUBHEADERES);

                    for (int i = 0; i < names.size(); i++) {
                        final int index = i;
                        titlePopupMenu.getMenu().add(names.get(i)).setIcon(icons.get(i)).setOnMenuItemClickListener(new OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                AppState.get().outlineMode = actions.get(index);
                                setTitlePopupIcon(icons.get(index));
                                showOutline.run();
                                return false;
                            }
                        });
                    }
                }

                return view;
            }

        }.show("showContent", false, true);
        return dragingPopup;

    }
    */

}
