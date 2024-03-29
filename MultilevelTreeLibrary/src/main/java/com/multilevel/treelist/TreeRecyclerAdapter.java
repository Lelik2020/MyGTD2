package com.multilevel.treelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangke on 2017-1-14.
 */
public abstract class TreeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    protected Context mContext;

    protected List<Node> mNodes = new ArrayList<>();
    protected LayoutInflater mInflater;


    protected List<Node> mAllNodes = new ArrayList<>();


    private OnTreeNodeClickListener onTreeNodeClickListener;

    private int defaultExpandLevel = 0;


    private int iconExpand = -1,iconNoExpand = -1;

    public void setOnTreeNodeClickListener(
            OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    public TreeRecyclerAdapter(RecyclerView mTree, Context context, List<Node> datas,
                           int defaultExpandLevel, int iconExpand, int iconNoExpand) {

        this.iconExpand = iconExpand;
        this.iconNoExpand = iconNoExpand;

        for (Node node:datas){
            node.getChildren().clear();
            node.iconExpand = iconExpand;
            node.iconNoExpand = iconNoExpand;
        }
        this.defaultExpandLevel = defaultExpandLevel;
        mContext = context;

        mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);

        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        mInflater = LayoutInflater.from(context);
    }


    public TreeRecyclerAdapter(RecyclerView mTree, Context context, List<Node> datas,
                           int defaultExpandLevel) {
        this(mTree,context,datas,defaultExpandLevel,-1,-1);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        Node node = mNodes.get(position);


        holder.itemView.setPadding(node.getLevel() * 30, 3, 3, 3);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onClick(mNodes.get(position),
                            position);
                }
            }
        });
        onBindViewHolder(node,holder,position);
    }

    @Override
    public int getItemCount() {
        return mNodes.size();
    }


    public void addDataAll(List<Node> mlists,int defaultExpandLevel){
        mAllNodes.clear();
        addData(-1,mlists,defaultExpandLevel);
    }


    public void addData(int index,List<Node> mlists,int defaultExpandLevel){
        this.defaultExpandLevel = defaultExpandLevel;
        notifyData(index,mlists);
    }


    public void addData(int index,List<Node> mlists){
        notifyData(index,mlists);
    }


    public void addData(List<Node> mlists){
        addData(mlists,defaultExpandLevel);
    }


    public void addData(List<Node> mlists,int defaultExpandLevel){
        this.defaultExpandLevel = defaultExpandLevel;
        notifyData(-1,mlists);
    }


    public void addData(Node node){
        addData(node,defaultExpandLevel);
    }


    public void addData(Node node,int defaultExpandLevel){
        List<Node> nodes = new ArrayList<>();
        nodes.add(node);
        this.defaultExpandLevel = defaultExpandLevel;
        notifyData(-1,nodes);
    }


    public void removeData(Node node) {
        if (node == null){
            return;
        }
        removeDeleteNode(node);
        for (Node n:mAllNodes){
            n.getChildren().clear();
        }
        mAllNodes = TreeHelper.getSortedNodes(mAllNodes, defaultExpandLevel);
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);

        notifyDataSetChanged();
    }


    public void removeData(List<Node> nodes) {
        if (nodes == null || nodes.isEmpty()){
            return;
        }
        for (Node node:nodes){
            removeDeleteNode(node);
        }
        for (Node n:mAllNodes){
            n.getChildren().clear();
        }
        mAllNodes = TreeHelper.getSortedNodes(mAllNodes, defaultExpandLevel);
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);

        notifyDataSetChanged();
    }

    private void removeDeleteNode(Node node){
        if (node == null){
            return;
        }
        List<Node> childrens = node.getChildren();
        if (childrens != null && !childrens.isEmpty()){
            for (Node n:childrens){
                removeDeleteNode(n);
            }
        }
         mAllNodes.remove(node);
    }


    private void notifyData(int index,List<Node> mListNodes){
        for (int i = 0; i < mListNodes.size(); i++) {
            Node node = mListNodes.get(i);
            node.getChildren().clear();
            node.iconExpand = iconExpand;
            node.iconNoExpand = iconNoExpand;
        }
        for (int i = 0; i < mAllNodes.size(); i++) {
            Node node = mAllNodes.get(i);
            node.getChildren().clear();
            node.isNewAdd = false;
        }
        if (index != -1){
            mAllNodes.addAll(index,mListNodes);
        }else {
            mAllNodes.addAll(mListNodes);
        }

        mAllNodes = TreeHelper.getSortedNodes(mAllNodes, defaultExpandLevel);

        mNodes = TreeHelper.filterVisibleNode(mAllNodes);

        notifyDataSetChanged();
    }


    public List<Node> getAllNodes(){
        if(mAllNodes == null)
            mAllNodes = new ArrayList<Node>();
        return mAllNodes;
    }


    public void expandOrCollapse(int position) {
        Node n = mNodes.get(position);

        if (n != null) {// 排除传入参数错误异常
            if (!n.isLeaf())
            {
                n.setExpand(!n.isExpand());
                mNodes = TreeHelper.filterVisibleNode(mAllNodes);
                notifyDataSetChanged();// 刷新视图
            }
        }
    }

    protected void setChecked(final Node node, boolean checked) {
        node.setChecked(checked);
        //setChildChecked(node, checked);
        if(node.getParent()!=null)
            //setNodeParentChecked(node.getParent(), checked);
        notifyDataSetChanged();
    }

    public <T,B>void setChildChecked(Node<T,B> node,boolean checked){
        if(!node.isLeaf()){
            node.setChecked(checked);
            for (Node childrenNode : node.getChildren()) {
                //setChildChecked(childrenNode, checked);
            }
        }else{
            node.setChecked(checked);
        }
    }

    private void setNodeParentChecked(Node node,boolean checked){
        if(checked){
            node.setChecked(checked);
            if(node.getParent()!=null)
                setNodeParentChecked(node.getParent(), checked);
        }else{
            List<Node> childrens = node.getChildren();
            boolean isChecked = false;
            for (Node children : childrens) {
                if(children.isChecked()){
                    isChecked = true;
                }
            }

            if(!isChecked){
                node.setChecked(checked);
            }
            if(node.getParent()!=null)
                setNodeParentChecked(node.getParent(), checked);
        }
    }

    public abstract void onBindViewHolder(Node node,RecyclerView.ViewHolder holder,final int position);
}
