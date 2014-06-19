package edu.wpi.smartcoach.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import edu.wpi.smartcoach.R;

public class SetReminderFragment extends Fragment {
	
				int position;
				int total;
				String reminder;
				OnClickListener listener;
				public void setPosition(int p, int total){
					position = p;
					this.total = total;
				}
				
				public void setReminder(String reminder){
					this.reminder = reminder;
				}
				
				public void setListener(View.OnClickListener l){
					listener = l;
				}
				
				public View onCreateView(LayoutInflater inflater, ViewGroup container,
						Bundle savedInstanceState){
					View root = inflater.inflate(R.layout.fragment_set_reminder, null);
					
					
					TextView rt = (TextView)root.findViewById(R.id.reminderText);
					rt.setText("Set up reminder : "+reminder);
					Button finish = (Button)root.findViewById(R.id.finish);
					if(position != total-1){
						finish.setText("Next");
					}
					finish.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if(listener != null)
								listener.onClick(v);
						}
					});
					return root;
					
				}
				
				
			}