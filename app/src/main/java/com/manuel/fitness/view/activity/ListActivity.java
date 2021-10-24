package com.manuel.fitness.view.activity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.fitness.R;
import com.manuel.fitness.model.entity.Entity;
import com.manuel.fitness.view.adapter.GenericListAdapter;

import java.util.LinkedList;

public abstract class ListActivity<T extends Entity, V extends GenericListAdapter.ViewHolder> extends GenericActivity {
    protected RecyclerView list;
    protected GenericListAdapter<T, V> adapter;
    private ItemTouchHelper touchHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (adapter.isSelectable()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.list_menu, menu);

            if (adapter.getItemCount() > 0)
                menu.findItem(R.id.selectAll).setVisible(true);

            adapter.setOnRowSelectionChange(isChecked -> {
                MenuItem delete = menu.findItem(R.id.deleteSelected);
                if (isChecked) delete.setVisible(true);
                else if (adapter.getSelectedRows() == 0)
                    delete.setVisible(false);
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addBtn) createItem();
        else if (item.getItemId() == R.id.selectAll) {
            boolean allSelected = adapter.getSelectedRows() == adapter.getItemCount();
            adapter.getSelectedItems().clear();
            if (!allSelected)
                adapter.getSelectedItems().addAll(adapter.getList());
            menu.findItem(R.id.deleteSelected).setVisible(!allSelected);
            adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        } else if (item.getItemId() == R.id.deleteSelected) {
            adapter.getList().removeIf(t -> {
                if (adapter.getSelectedItems().contains(t)) {
                    adapter.getSelectedItems().remove(t);
                    onDeleteItem(t);

                    int index = adapter.getList().indexOf(t);
                    adapter.notifyItemRemoved(index);
                    return true;
                }

                return false;
            });

            item.setVisible(false);
            if (adapter.getItemCount() == 0)
                menu.findItem(R.id.selectAll).setVisible(false);

            onDeleteFinished();
        }

        return super.onOptionsItemSelected(item);
    }

    protected abstract void createItem();
    protected abstract void onMoveItem(int fromPos, int toPos);
    protected abstract void onDeleteItem(T t);
    protected abstract void onDeleteFinished();

    protected void addItemSorter() {
        ItemTouchHelper.SimpleCallback touchCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = target.getAdapterPosition();
                adapter.moveItem(fromPos, toPos);
                onMoveItem(fromPos, toPos);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {}
        };
        touchHelper = new ItemTouchHelper(touchCallback);
        touchHelper.attachToRecyclerView(list);
    }

    protected void updateList(LinkedList<T> adapterList) {
        adapter.setList(adapterList);
        if (menu != null && adapter.isSelectable())
            menu.findItem(R.id.selectAll).setVisible(adapterList.size() > 0);
    }

    protected void addExerciseToList(T t) {
        adapter.getList().add(t);
        adapter.notifyItemInserted(adapter.getItemCount()-1);
        if (menu != null && adapter.isSelectable())
            menu.findItem(R.id.selectAll).setVisible(true);
    }

    public RecyclerView getList() {
        return list;
    }

    public ItemTouchHelper getTouchHelper() {
        return touchHelper;
    }
}