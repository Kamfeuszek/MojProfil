package com.example.mojprofil;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView emailText;
    TextView passwordText;
    Button editProfileButton;
    TextView notifiView;
    int switchState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        editProfileButton = findViewById(R.id.editProfileButton);
        notifiView = findViewById(R.id.notifiView);

        notifiView.setTextColor(Color.GRAY);
        notifiView.setText("Witaj! Aplikacja wykonana przez: Kamil Florczak");

        editProfileButton.setOnClickListener(v -> {
            showDialog();
        });
    }
    public void showDialog() {
        TextView newPasswordText = new TextView(this);
        newPasswordText.setText("Podaj nowe hasło: ");
        TextView repeatPasswordText = new TextView(this);
        repeatPasswordText.setText("Powtórz hasło: ");

        EditText changeEmailText = new EditText(this);
        EditText changePasswordText = new EditText(this);
        EditText changePasswordRepeatText = new EditText(this);
        changePasswordText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        changePasswordRepeatText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        changePasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        changePasswordRepeatText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        LinearLayout changePasswordLayout = new LinearLayout(this);
        changePasswordLayout.addView(newPasswordText);
        changePasswordLayout.addView(changePasswordText);
        changePasswordLayout.addView(repeatPasswordText);
        changePasswordLayout.addView(changePasswordRepeatText);
        changePasswordLayout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);

        builder2.setTitle("Zmień hasło")
                .setView(changePasswordLayout)
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switchState = 0;
                        String emailChangeText = changeEmailText.getText().toString().trim();
                        String passwordChangeText = changePasswordText.getText().toString().trim();
                        String passwordRepeatChangeText = changePasswordRepeatText.getText().toString().trim();
                        if(!emailChangeText.contains("@")) {
                            notifiView.setTextColor(Color.RED);
                            notifiView.setText("Błąd: Nieprawidłowy format emaila.");
                            return;
                        }
                        if(!passwordChangeText.equals(passwordRepeatChangeText)) {
                            notifiView.setTextColor(Color.RED);
                            notifiView.setText("Błąd: Hasła nie pasują do siebie.");
                            return;
                        }
                        emailText.setText(emailChangeText);
                        passwordText.setText(passwordChangeText);
                        notifiView.setTextColor(Color.GREEN);
                        notifiView.setText("Profil zaktualizowany! Nowy email: " + emailChangeText);
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notifiView.setTextColor(Color.GRAY);
                        notifiView.setText("Edycja profilu anulowana");
                        switchState = 0;
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog2 = builder2.create();
        dialog2.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton= ((AlertDialog)dialog2).getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setBackgroundColor(R.color.light_blue);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Zmień email")
                .setMessage("Podaj nowy email: ")
                .setView(changeEmailText)
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog2.show();
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switchState = 1;
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton= ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setBackgroundColor(R.color.light_blue);
            }
        });
        if(switchState == 0) {
            dialog.show();
        } else {
            dialog2.show();
        }
    }
}