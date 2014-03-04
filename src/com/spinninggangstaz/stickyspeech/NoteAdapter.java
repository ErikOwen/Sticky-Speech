package com.spinninggangstaz.stickyspeech;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<Note> {
	private List<Note> origItems;
	private List<Note> fItems;
	private NoteDateFormatter dateFormatter = new HoursAgoDateFormatter();
	private Context context;

	public NoteAdapter(Context context, int textViewResourceId, List<Note> items) {
		super(context, textViewResourceId, items);
		this.origItems = new ArrayList<Note>(items);
		this.fItems = new ArrayList<Note>(items);
		this.context = context;
	}

	public void resetDataSet(List<Note> items) {
		this.origItems = new ArrayList<Note>(items);
		this.fItems = new ArrayList<Note>(items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflaterService = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflaterService.inflate(R.layout.row, null);
		}

		Note note = null;
		if(position >= 0 && position < fItems.size()) {
			note = fItems.get(position);
		}
		if (note != null) {
			TextView topText = (TextView)convertView.findViewById(R.id.toptext);
			TextView bottomText = (TextView)convertView.findViewById(R.id.bottomtext);
			if (topText != null) {
				topText.setText(formatTitle(note.getTitle()));
			}
			if(bottomText != null){
				bottomText.setText(dateFormatter.getFormattedDate(note));
			}
		}

		return convertView;
	}

	private String formatTitle(String title) {
		final int maxTitleLength = 50;

		if(title.contains("\n")) {
			title = title.substring(0, title.indexOf('\n'));
		}

		if(title.length() < maxTitleLength) {
			return title;
		}
		else {
			return title.substring(0, maxTitleLength) + "...";
		}
	}

	@Override
	public int getPosition(Note note) {
		return origItems.indexOf(note);
	}

	@Override
	public Note getItem(int position) {
		return fItems.get(position);
	}

	@Override
	public Filter getFilter() {

		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				ArrayList<Note> temp = (ArrayList<Note>)results.values;
				if(temp.size() > 0) {
					fItems = (ArrayList<Note>)results.values;

					notifyDataSetChanged();
					clear();

					for(int iter = 0; iter < fItems.size(); iter++) {
						add(fItems.get(iter));
						//notifyDataSetInvalidated();
					}
				}
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {

				FilterResults results = new FilterResults();

				// perform your search here using the searchConstraint String.

				String prefix = constraint.toString().toLowerCase();

				if(prefix == null || prefix.length() == 0) {
					ArrayList<Note> list = new ArrayList<Note>(origItems);
					results.values = list;
					results.count = list.size();
				}
				else {
					ArrayList<Note> nList = new ArrayList<Note>();

					for(Note note : origItems) {
						if(note.getTitle().toLowerCase().contains(prefix) ||
								note.getText().toLowerCase().contains(prefix)) {
							nList.add(note);
						}
					}
					
					results.values = nList;
					results.count = nList.size();
				}

				return results;
			}
		};

		return filter;
	}

}