package com.kmu.diary;

        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.TextView;

        import org.w3c.dom.Text;

        import java.util.ArrayList;

public class searchFragment extends Fragment {

    View v;

    DB_schedule db_schedule;
    DB_todo db_todo;

    SQLiteDatabase db1;

    SQLiteDatabase db2;

    EditText editText;

    Button button;

//    TextView textView_schedule;
//    TextView textView_todo;

    ListView ListViewSchedule;
    ListView ListViewTodo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_search, container,false);

        db_schedule = new DB_schedule(getActivity());
        db_todo = new DB_todo(getActivity());

        db1 = db_schedule.getReadableDatabase();
        db2 = db_todo.getReadableDatabase();

        editText = (EditText) v.findViewById(R.id.editText);
        button = (Button) v.findViewById(R.id.button);

        ListViewSchedule = (ListView) v.findViewById(R.id.listViewSchedule);
        ListViewTodo = (ListView) v.findViewById(R.id.listViewToDo);

//        textView_schedule = (TextView) v.findViewById(R.id.textView_schedule);
//        textView_todo = (TextView) v.findViewById(R.id.textView_todo);


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String date = editText.getText().toString();

                Cursor cursor1;
                cursor1 = db1.rawQuery("SELECT id, date, content FROM schedule WHERE date='"
                        + date + "';", null);

                ArrayList<String> listSchedule = new ArrayList<>();

                Cursor cursor2;
                cursor2 = db2.rawQuery("SELECT id, date, content FROM todo WHERE date='"
                        + date + "';", null);

                ArrayList<String> listToDo = new ArrayList<>();


                while (cursor1.moveToNext()) {
                    listSchedule.add(cursor1.getString(0)+". Date: "+cursor1.getString(1)+"\n    Content: "+
                            cursor1.getString(2));
                }

                while (cursor2.moveToNext()) {

                    listToDo.add(cursor2.getString(0)+". Date: "+cursor2.getString(1)+"\n    Content: "+
                            cursor2.getString(2));
                }

                //create the list adapter and set the adapter
                ListAdapter adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,listSchedule);
                ListViewSchedule.setAdapter(adapter1);

                ListAdapter adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,listToDo);
                ListViewTodo.setAdapter(adapter2);


            }
        });


        return v;
    }

}
