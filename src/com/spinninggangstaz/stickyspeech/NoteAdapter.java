package com.spinninggangstaz.stickyspeech;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    }
