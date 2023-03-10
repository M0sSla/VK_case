package com.example.vk_case;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionManager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.vk_case.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean stateCamera = true, stateMicro = true, stateFragments = true;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // при нажатии на кнопку завершения вызова - выход из приложения
        binding.callEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // поднятая рука - вызов диалога
        binding.riseHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog();
            }
        });
        // переход на приложение для сообщений
        binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:"));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Define what your app should do if no activity can handle the intent.
                }
            }
        });
        // смена значка видеокамеры
        binding.vebCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateCamera) {
                    binding.vebCam.setBackgroundResource(R.drawable.button_shape_off);
                    binding.vebCam.setImageResource(R.drawable.vebcam_off);
                    stateCamera = false;
                }
                else {
                    binding.vebCam.setBackgroundResource(R.drawable.button_shape_on);
                    binding.vebCam.setImageResource(R.drawable.vebcam_on);
                    stateCamera = true;
                }
            }
        });
        // смена значка микрофона на кнопке и на фрагменте говорящего (Скалы Джонсона)
        binding.mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateMicro) {
                    binding.mic.setBackgroundResource(R.drawable.button_shape_off);
                    binding.mic.setImageResource(R.drawable.mic_off);
                    TextView tv = findViewById(R.id.dwayneName);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.mic_off, 0);
                    stateMicro = false;
                }
                else {
                    binding.mic.setBackgroundResource(R.drawable.button_shape_on);
                    binding.mic.setImageResource(R.drawable.mic_on);
                    TextView tv = findViewById(R.id.dwayneName);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.mic_on, 0);
                    stateMicro = true;
                }
            }
        });
        // функционал значка группы: при нажатии на него всплывает новое окно, реализованное с помощью intent.
        // Можно и с помощью фрагмент, однако тогда потребуется создавать nav_graph, слишком муторно :)
        binding.group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                startActivity(intent);
            }
        });
        // функционал кнопки вопросительного знака. Я точно не понял, что конкретно необходимо сделать
        // поэтому я просто переставлю местами фрагменты двух пользователей
        binding.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failure();
                // переставить не получилось. При запуске данного куска кода приложение просто крашится((
                // это итоговая версия моего проекта, большего я из себя не выдавлю за эти три дня активной верстки)
//                ConstraintSet cache = new ConstraintSet();
//                cache.clone(constraintLayout);
//                if (stateFragments) {
//                    cache.connect(R.id.firstFragment, ConstraintSet.TOP, R.id.secondFragment, ConstraintSet.BOTTOM);
//                    cache.connect(R.id.firstFragment, ConstraintSet.BOTTOM, R.id.callEnd, ConstraintSet.TOP);
//                    cache.connect(R.id.secondFragment, ConstraintSet.TOP, R.id.group, ConstraintSet.BOTTOM);
//                    cache.connect(R.id.secondFragment, ConstraintSet.BOTTOM, R.id.firstFragment, ConstraintSet.TOP);
//                    stateFragments = false;
//                }
//                else {
//                    cache.connect(R.id.firstFragment, ConstraintSet.TOP, R.id.group, ConstraintSet.BOTTOM);
//                    cache.connect(R.id.firstFragment, ConstraintSet.BOTTOM, R.id.secondFragment, ConstraintSet.TOP);
//                    cache.connect(R.id.secondFragment, ConstraintSet.TOP, R.id.firstFragment, ConstraintSet.BOTTOM);
//                    cache.connect(R.id.secondFragment, ConstraintSet.BOTTOM, R.id.callEnd, ConstraintSet.TOP);
//                    stateFragments = true;
//                }
//                TransitionManager.beginDelayedTransition(constraintLayout);
//                cache.applyTo(constraintLayout);
            }
        });
    }

    // отдельная функция, отвечающая за диалог
    private void myDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.hi);
        builder.setMessage(R.string.message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void failure() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.fail1);
        builder.setMessage(R.string.fail2);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}