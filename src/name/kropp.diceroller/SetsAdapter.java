package name.kropp.diceroller;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class SetsAdapter extends ArrayAdapter<String> {
    private SetsManager mySetsManager;
    private Context myContext;
    private SharedPreferences myPreferences;

    public SetsAdapter(Context context, SetsManager setsManager) {
        super(context, R.layout.simple_list_item_single_choice);

        myContext = context;
        myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mySetsManager = setsManager;
    }

    @Override
    public int getCount() {
        return mySetsManager.getGames().size();
    }

    @Override
    public String getItem(int position) {
        return mySetsManager.getGames().get(position).getName();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView) inflater.inflate(R.layout.simple_list_item_1, parent, false);

        textView.setText(mySetsManager.getGames().get(position).getName());
        textView.setTag(position);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = (Integer) view.getTag();
                mySetsManager.setSelected(index);
                SharedPreferences.Editor editor = myPreferences.edit();
                editor.putString("selected_set_id", mySetsManager.getSelectedSet().getId());
                editor.commit();
                myContext.startActivity(new Intent(myContext, MyMainActivity.class));
            }
        });

        return textView;
    }
}