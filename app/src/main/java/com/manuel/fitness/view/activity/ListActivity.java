package com.manuel.fitness.view.activity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.fitness.R;
import com.manuel.fitness.model.entity.Entity;
import com.manuel.fitness.view.adapter.GenericListAdapter;
import com.manuel.fitness.viewmodel.event.RecyclerItemClickListener;

import java.util.LinkedList;

public abstract class ListActivity<T extends Entity, V extends GenericListAdapter.ViewHolder>
		extends GenericActivity {
	protected RecyclerView list;
	protected GenericListAdapter<T, V> adapter;
	private ItemTouchHelper touchHelper;
	private RecyclerItemClickListener itemClickListener;
	private Class<? extends GenericActivity> itemCreator;

	protected void createList(int resId, GenericListAdapter<T, V> adapter,
	                          Class<? extends GenericActivity> itemCreator,
	                          Class<? extends GenericActivity> itemOpener) {
		this.adapter = adapter;
		this.itemCreator = itemCreator;

		list = findViewById(resId);
		list.setLayoutManager(new LinearLayoutManager(this));
		list.setAdapter(adapter);

		if (adapter.isSortable())
			addItemSorter();

		itemClickListener = new RecyclerItemClickListener(this, list,
				adapter.isSelectable(), adapter.isSortable(),
				new RecyclerItemClickListener.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				if (itemOpener != null) {
					T t = adapter.getList().get(position);
					openActivity(itemOpener, t);
				}
			}

			@Override
			public void onLongItemClick(View view, int position) {
				beginSelection();
			}

			@Override
			public void onSelect(View view, int position) {
				CheckBox selector = getViewHolderForAdapterPosition(position).getSelector();
				boolean check = !selector.isChecked();
				getViewHolderForAdapterPosition(position).getSelector().setChecked(check);
				if (adapter.getSelectedRows() == 0)
					endSelection();
			}
		});
		list.addOnItemTouchListener(itemClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.addBtn)
			openActivity(itemCreator);
		else if (item.getItemId() == R.id.selectAll) {
			boolean allSelected = adapter.getSelectedRows() == adapter.getItemCount();
			if (!allSelected)
				for (int i = 0; i < adapter.getItemCount(); i++) {
					GenericListAdapter.ViewHolder holder = getViewHolderForAdapterPosition(i);
					holder.getSelector().setChecked(true);
				}
			else endSelection();
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

			endSelection();
			onDeleteFinished();
		}

		return super.onOptionsItemSelected(item);
	}

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

	public void updateList(LinkedList<T> adapterList) {
		adapter.setList(adapterList);
	}

	public void addElementToList(T t) {
		adapter.getList().add(t);
		adapter.notifyItemInserted(adapter.getItemCount()-1);
	}

	protected void beginSelection() {
		menu.setGroupVisible(R.id.selectOptions, true);
		setVisibilityToAllSelectors(View.VISIBLE);
	}

	protected void endSelection() {
		menu.setGroupVisible(R.id.selectOptions, false);
		adapter.getSelectedItems().clear();
		setVisibilityToAllSelectors(View.GONE);
		itemClickListener.stopSelection();
		adapter.notifyItemRangeChanged(0, adapter.getItemCount());
	}

	private void setVisibilityToAllSelectors(int visibility) {
		for (int i = 0; i < adapter.getItemCount(); i++) {
			GenericListAdapter.ViewHolder holder = getViewHolderForAdapterPosition(i);
			holder.getSelector().setVisibility(visibility);
		}
	}

	protected GenericListAdapter.ViewHolder getViewHolderForAdapterPosition(int position) {
		return (GenericListAdapter.ViewHolder) list.findViewHolderForAdapterPosition(position);
	}

	protected abstract void onMoveItem(int fromPos, int toPos);
	protected abstract void onDeleteItem(T t);
	protected abstract void onDeleteFinished();

	public RecyclerView getList() {
		return list;
	}

	public ItemTouchHelper getTouchHelper() {
		return touchHelper;
	}
}