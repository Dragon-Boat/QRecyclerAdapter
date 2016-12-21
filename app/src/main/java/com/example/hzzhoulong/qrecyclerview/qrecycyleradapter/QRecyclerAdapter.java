package com.example.hzzhoulong.qrecyclerview.qrecycyleradapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持添加多个头部
 * 自带空状态时候的管理
 * Created by @author hzzhoulong
 * on 2016/12/20.
 */

public abstract class QRecyclerAdapter<T, V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE_HEAD = Integer.MAX_VALUE + 1;
    private static final int ITEM_TYPE_NORMAL = ITEM_TYPE_HEAD + 2 << 10;
    private static final int ITEM_TYPE_STATE = ITEM_TYPE_NORMAL + 1;
    protected List<T> data = new ArrayList<>();
    private List<View> headViewList = new ArrayList<>();
    //状态页的VH
    private StateVH mStateVH;
    private OnItemClickListener listener;
    //状态页的接口
    private StateHelpInterface stateHelpInterface;

    public QRecyclerAdapter(Context context) {
        //初始化状态管理器
        stateHelpInterface = getStateHelpInterface();
        onInitStateVH(context);
    }

    /**
     * 状态页应交由具体的业务逻辑去实现
     * @return
     */
    public abstract StateHelpInterface getStateHelpInterface();

    private void onInitStateVH(Context context) {
        StateLayout view = new StateLayout(context);
        view.setStateHelper(stateHelpInterface);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.mStateVH = new StateVH(view);
    }

    /**
     * 实现各种layoutmanageer下头部占满一行的效果
     * @see QRecyclerAdapter#onViewAttachedToWindow(RecyclerView.ViewHolder)
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        if (lm instanceof GridLayoutManager) {
            final GridLayoutManager manager = ((GridLayoutManager) lm);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == ITEM_TYPE_HEAD ? manager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getHeadSize()) {
            return ITEM_TYPE_HEAD + position;
        }
        return getDataSize() != 0 ? ITEM_TYPE_NORMAL : ITEM_TYPE_STATE;
    }


    public abstract V onCreateItemViewHolder(ViewGroup parent);

    public abstract void onBindItemViewHolder(V holder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < ITEM_TYPE_HEAD + getHeadSize()) {
            View view = headViewList.get(viewType - ITEM_TYPE_HEAD);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new HeadVH(view);
        }
        if (viewType == ITEM_TYPE_STATE) {
            return mStateVH;
        }
        return onCreateItemViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == ITEM_TYPE_NORMAL) {
            final int dataPos = posInData(position);
            onBindItemViewHolder(((V) holder), dataPos);
            if (listener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(dataPos, view);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        listener.onLongClick(dataPos, view);
                        return true;
                    }
                });
            }
        } else if (getItemViewType(position) == ITEM_TYPE_STATE) {
            mStateVH.stateLayout.onEmpty();
        }
    }

    @Override
    public int getItemCount() {
        return getDataSize() != 0 ? getHeadSize() + getDataSize() : getHeadSize() + 1;
    }

    public StateVH getStateVH() {
        return mStateVH;
    }

    public void setData(List<T> data) {
        this.data.clear();
        if (data != null) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addItem(T t) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        data.add(t);
        notifyItemInserted(data.size() + getHeadSize());

    }
    public void addHead(int pos, View view) {
        if (view != null && !headViewList.contains(view)) {
            headViewList.add(pos, view);
            notifyItemInserted(headViewList.indexOf(pos));
        }
    }

    public void addHead(View view) {
        addHead(getHeadSize(), view);
    }

    public void removeData(int pos) {
        if (data != null && data.size() > pos) {
            data.remove(pos);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public boolean isHead(int pos) {
        return pos < getHeadSize();
    }

    public int getHeadSize() {
        return headViewList != null ? headViewList.size() : 0;
    }

    public int getDataSize() {
        return data != null ? data.size() : 0;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 通过一个viewholder在recyclerview中的位置，计算其在datalist中的对应位置；
     *
     * @param position
     * @return
     */
    private int posInData(int position) {
        return position - getHeadSize();
    }

    public interface OnItemClickListener {
        void onClick(int pos, View view);

        void onLongClick(int pos, View view);
    }

    /**
     * 设为public是为了让外部能够通过adapter来操作stateVH显示不同的状态，
     * @see StateLayout#onNetworkError() 等
     * 考虑除了空状态可以由列表自己处理外，其他的状态应该由外部来决定何时如何展示
     * 因此后续使用接口来完成这个功能
     */
    public class StateVH extends RecyclerView.ViewHolder {
        StateLayout stateLayout;

        public StateVH(View itemView) {
            super(itemView);
            this.stateLayout = ((StateLayout) itemView);
        }

        public StateLayout getStateLayout() {
            return stateLayout;
        }
    }

}
