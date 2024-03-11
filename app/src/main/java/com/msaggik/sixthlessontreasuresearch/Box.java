package com.msaggik.sixthlessontreasuresearch;

public class Box {
    // поля
    private float coordinateX;
    private float coordinateY;
    private boolean found; // поле найден/не найден сундук сокровищ
    // конструктор
    public Box(float coordinateX, float coordinateY, boolean found) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.found = found;
    }
    // геттеры
    public float getCoordinateX() {
        return coordinateX;
    }
    public float getCoordinateY() {
        return coordinateY;
    }
    public boolean isFound() {
        return found;
    }
    // сеттер
    public void setFound(boolean found) {
        this.found = found;
    }
}
