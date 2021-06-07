package com.mhss.gomed;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mhss.gomed.other.NotesDTO;
import com.mhss.gomed.other.DBAdapter;
import com.mhss.gomed.other.NotesTable;
import com.mhss.gomed.other.Utils;


public class AEDNotesActivity extends AppCompatActivity {

    private EditText _nameText;

    private Button _submitButton;

    private Toolbar mToolbar;

    private NotesDTO notesDTO;

    private int flag;  // 0 -add ; 1 - edit ; 2 - delete
    private String id = "0";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aed_notes);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
    }

    public void initView() {

        notesDTO = (NotesDTO) getIntent().getSerializableExtra("data");
        _nameText = (EditText) findViewById(R.id.input_name);
        _submitButton = (Button) findViewById(R.id.btn_submit);

        if (notesDTO != null) {
            id = notesDTO.getId();
            _nameText.setText(notesDTO.getName());
            _nameText.setEnabled(false);
            _submitButton.setVisibility(View.INVISIBLE);
        } else {
            flag = 0;
            _submitButton.setVisibility(View.VISIBLE);
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Notes");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_edit:
                if (notesDTO != null && notesDTO.getId().length() > 0) {
                    flag = 1;
                    _nameText.setEnabled(true);
                    _submitButton.setVisibility(View.VISIBLE);
                    _submitButton.setText("Update Notes");
                } else {
                    Toast.makeText(getApplicationContext(), "You can't perform these action", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_delete:
                if (notesDTO != null && notesDTO.getId().length() > 0) {
                    show_customized_popup();
                } else {
                    Toast.makeText(getApplicationContext(), "You can't perform these action", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            login(true);
        }
    }

    public void login(boolean status) {
        if (status) {
            if (!validate()) {
                onLoginFailed();
                return;
            }
        }
        _submitButton.setEnabled(false);
        String name = _nameText.getText().toString();
        String message = "";
        DBAdapter dba = new DBAdapter(getApplicationContext());
        dba.open();
        if (flag == 0) {
            message = "Notes Added Succesfully...";
            dba.insertNotes(new NotesTable(name, Utils.getDatetime()));
        } else if (flag == 1) {
            message = "Notes Updated Succesfully...";
            dba.updateNotes(notesDTO.getId(),name,Utils.getDatetime());
        } else if (flag == 2) {
            message = "Notes Deleted Succesfully...";
            dba.deleteNotes(notesDTO.getId());
        }
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        dba.close();
        onSuccess();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onSuccess() {
        Intent i = new Intent("android.intent.action.notes");
        getApplicationContext().sendBroadcast(i);
        finish();
    }

    public void onLoginFailed() {
        _submitButton.setEnabled(true);
    }

    public boolean validate() {

        String name = _nameText.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter Notes", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void show_customized_popup() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_decigion);
        ImageView icon = (ImageView) dialog.findViewById(R.id.iv_decigion_icon);

        TextView tv_header = (TextView) dialog
                .findViewById(R.id.tv_decigion_heading);
        tv_header.setText(getResources().getString(R.string.delete));

        TextView tv_title = (TextView) dialog
                .findViewById(R.id.tv_decigion_title);
        tv_title.setText(getResources().getString(R.string.delete_app));

        Button bt_yes = (Button) dialog.findViewById(R.id.bt_yes);
        bt_yes.setText(getResources().getString(R.string.yes));
        bt_yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                flag = 2;
                login(false);
                dialog.dismiss();
            }
        });
        Button bt_no = (Button) dialog.findViewById(R.id.bt_no);
        bt_no.setText(getResources().getString(R.string.no));
        bt_no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
