package com.msaggik.sixthlessontreasuresearch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // поля
    private TextView output, field;
    private float x, y; // координаты касания TextView field

    private Box[] boxes; // массив для объектов сундуков сокровищ
    private Random random = new Random(); // создание объекта класса Random
    private boolean isPainted = false;
    private Thread thread;
    private float dimensions = 50; // габариты сундука сокровищ
    private int count = 0; // счётчик найденных сундуков

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = findViewById(R.id.output);
        field = findViewById(R.id.field);

        getInfo();

        Context context = this;
        ViewTreeObserver obs = field.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                // Обработчик вызывается после полной отрисовки View
                field.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                generateBoxes(10, field);
                isPainted = true;
                thread.interrupt();
                field.setOnTouchListener(listener);
                Toast.makeText(context, "Поле поиска отрисованно!", Toast.LENGTH_SHORT);
            }
        });
    }

    // создание слушателя
    private View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            // определение координат касания
            x = motionEvent.getX();
            y = motionEvent.getY();

            // определение типа касания
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: // касание TextView field
                    field.setText("Касание " + x + ", " + y);
                    break;
                case MotionEvent.ACTION_MOVE: // движение по TextView field
                    field.setText("Движение " + x + ", " + y);
                    // поиск сундуков сокровищ
                    for(Box box: boxes) {
                        // если удалось провести пальцем по сундуку сокровищ и он не найден
                        if(!box.isFound() && x >= (box.getCoordinateX() - dimensions) && x <= (box.getCoordinateX() + dimensions) &&
                                y >= (box.getCoordinateY() - dimensions) && y <= (box.getCoordinateY() + dimensions)) {
                            box.setFound(true); // установка сундука как найденного
                            count++; // повышение счётчика поиска сундуков
                            output.setText("Найдено сундуков " + count);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP: // отпускание TextView field
                    field.setText("Отпускание " + x + ", " + y);
                    break;
            }
            return true;
        }
    };
    private void getInfo() {
        Runnable runnable = () -> {
            while (true) {
                if (isPainted) break;
                else continue;
            }
        };
        thread = new Thread(runnable);
        thread.start();
    }


    private void generateBoxes(int Count, TextView field) {
        boxes = new Box[Count];

        int maxCoordinateX = Math.round((field.getWidth() - dimensions)); // x
        int maxCoordinateY = Math.round((field.getHeight() - dimensions)); // y

        for (int i = 0; i < Count; i++)
            boxes[i] = new Box(random.nextInt(maxCoordinateX), random.nextInt(maxCoordinateY), false);
        for (int i = 0; i < boxes.length; i++) {
            String localCords = boxes[i].getCoordinateX() + " " + boxes[i].getCoordinateY();
            Log.i("COORDS", localCords); // Возможность получить координаты коробок
        }
        Log.i("COORDS", "\n");
    }
}
