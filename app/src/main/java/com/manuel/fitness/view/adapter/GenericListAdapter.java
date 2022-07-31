package com.manuel.fitness.view.adapter;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.fitness.R;
import com.manuel.fitness.model.entity.Entity;
import com.manuel.fitness.view.ClickableImageView;
import com.manuel.fitness.view.activity.ListActivity;
import com.manuel.fitness.view.adapter.GenericListAdapter.ViewHolder;

import java.util.LinkedList;

public abstract class GenericListAdapter<T extends Entity, V extends ViewHolder> extends RecyclerView.Adapter<V> {
    protected final ListActivity<T, V> activity;
    protected LinkedList<T> list;
    private final int resId;

    private final LinkedList<T> selectedItems;
    private final boolean selectable, sortable;

    public GenericListAdapter(ListActivity<T, V> activity, LinkedList<T> list, int resId,
                              boolean selectable, boolean sortable) {
        this.activity = activity;
        this.list = list;
        this.resId = resId;
        this.selectable = selectable;
        this.sortable = sortable;
        selectedItems = new LinkedList<>();
    }

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(resId, parent, false);
        return getViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        if (selectable) {
            T t = list.get(holder.getAdapterPosition());
            holder.getSelector().setOnCheckedChangeListener(null);
            holder.getSelector().setChecked(false);
            holder.getSelector().setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) selectedItems.add(t);
                else selectedItems.remove(t);
            });
        }

        if (sortable) {
            holder.getHandler().setVisibility(View.VISIBLE);
            holder.getHandler().setOnTouchListener((v, event) -> {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    activity.getTouchHelper().startDrag(holder);
                    v.performClick();
                    return true;
                }

                return false;
            });
        }
    }

    public void moveItem(int fromPos, int toPos) {
        T o1 = list.get(fromPos);
        T o2 = list.get(toPos);
        list.set(fromPos, o2);
        list.set(toPos, o1);

        notifyItemMoved(fromPos, toPos);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public LinkedList<T> getList() {
        return list;
    }

    public void setList(LinkedList<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public LinkedList<T> getSelectedItems() {
        return selectedItems;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public boolean isSortable() {
        return sortable;
    }

    public int getSelectedRows() {
        return selectedItems.size();
    }

    protected abstract V getViewHolder(View v);

    public abstract static class ViewHolder extends RecyclerView.ViewHolder {
        protected final CheckBox selector;
        protected final ClickableImageView handler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selector = itemView.findViewById(R.id.selector);
            handler = itemView.findViewById(R.id.handler);
        }

        public CheckBox getSelector() {
            return selector;
        }

        public ClickableImageView getHandler() {
            return handler;
        }
    }
}