package com.manuel.fitness.viewmodel.event;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.fitness.view.adapter.GenericListAdapter;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
	private final OnItemClickListener listener;
	private final GestureDetector gestureDetector;
	private boolean selecting;

	public RecyclerItemClickListener(Context context, final RecyclerView recyclerView,
	                                 boolean selectable, boolean sortable, OnItemClickListener listener) {
		this.listener = listener;
		gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return true;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				if (listener != null && !selecting) {
					View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
					if (child == null) return;

					GenericListAdapter<?, ?> adapter =
							(GenericListAdapter<?, ?>) recyclerView.getAdapter();
					if (adapter == null) return;

					if (!sortable || !adapter.isSorting()) {
						int position = recyclerView.getChildAdapterPosition(child);
						listener.onLongItemClick(child, position);

						if (selectable) {
							listener.onSelect(child, position);
							selecting = true;
						}
					}
				}
			}
		});
	}

	@Override
	public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
		View childView = view.findChildViewUnder(e.getX(), e.getY());
		if (listener != null && childView != null && gestureDetector.onTouchEvent(e)) {
			int position = view.getChildAdapterPosition(childView);
			if (selecting) listener.onSelect(childView, position);
			else listener.onItemClick(childView, position);
			return true;
		}

		return false;
	}

	@Override
	public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {}

	@Override
	public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept) {}

	public void stopSelection() {
		selecting = false;
	}

	public interface OnItemClickListener {
		void onItemClick(View view, int position);
		void onLongItemClick(View view, int position);
		void onSelect(View view, int position);
	}
}