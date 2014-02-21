package com.spinninggangstaz.stickyspeech;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<Note> {
	private List<Note> items;
    private NoteDateFormatter dateFormatter = new HoursAgoDateFormatter();
    private Context context;
    
    public NoteAdapter(Context context, int textViewResourceId, List<Note> items) {
    	super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }
        
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	if (convertView == null) {
    		LayoutInflater inflaterService = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		convertView = inflaterService.inflate(R.layout.row, null);
    	}
            
    	Note note = null;
    	if(position >= 0 && position < items.size()) {
    		note = items.get(position);
    	}
    	if (note != null) {
    		TextView topText = (TextView)convertView.findViewById(R.id.toptext);
    		TextView bottomText = (TextView)convertView.findViewById(R.id.bottomtext);
    		if (topText != null) {
    			topText.setText(note.getTitle());
    		}
    		if(bottomText != null){
    			bottomText.setText(dateFormatter.getFormattedDate(note));
    		}
    	}
                
    	return convertView;
	}
    
//    @Override
//    public int getCount()
//    {
//    	return items.size();
//    }

//    @Override
//    public Filter getFilter() {
//
//        Filter filter = new Filter() {
//
//            @SuppressWarnings("unchecked")
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//            	List<Note> resultItems;
//                resultItems = (List<Note>) results.values;
//                notifyDataSetChanged();
//            }
//
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//
//                FilterResults results = new FilterResults();
//                ArrayList<Note> FilteredArrayNames = new ArrayList<Note>();
//
//                // perform your search here using the searchConstraint String.
//
//                constraint = constraint.toString().toLowerCase();
//                
//                for(Note note : items) {
//                	if(note.getTitle().toLowerCase().contains(constraint) ||
//                			note.toString().toLowerCase().contains(constraint) ||
//                			note.getDate().toString().contains(constraint)) {
//                		FilteredArrayNames.add(note);
//                	}
//                }
//                
////                for (int i = 0; i < items.size(); i++) {
////                    String dataNames = mDatabaseOfNames.get(i);
////                    if (dataNames.toLowerCase().startsWith(constraint.toString()))  {
////                        FilteredArrayNames.add(dataNames);
////                    }
////                }
//
//                results.count = FilteredArrayNames.size();
//                results.values = FilteredArrayNames;
//                //Log.e("VALUES", results.values.toString());
//
//                return results;
//            }
//        };
//
//        return filter;
//    }
    
}
