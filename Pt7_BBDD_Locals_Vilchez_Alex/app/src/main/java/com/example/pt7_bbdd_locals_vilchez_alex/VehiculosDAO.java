package com.example.pt7_bbdd_locals_vilchez_alex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class VehiculosDAO extends SQLiteOpenHelper {

    // VARIABLES DE CONFIGURACIÓN OBLIGATORIAS
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vehiculos";

    // NOMBRE DE LA TABLA
    private static final String TABLE_VEHICULOS = "registro";
    // CAMPOS DE LA BASE DE DATOS
    private static final String KEY_ID = "id";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_APELLIDOS = "apellidos";
    private static final String KEY_TELEFONO = "telefono";
    private static final String KEY_MARCA_VEHICULO = "marca_vehiculo";
    private static final String KEY_MODELO_VEHICULO = "modelo_vehiculo";
    private static final String KEY_MATRICULA = "matricula";

    public VehiculosDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // QUERY DE CREACIÓN DE LA TABLA
    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Table = "CREATE TABLE " + TABLE_VEHICULOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NOMBRE + " TEXT, "
                + KEY_APELLIDOS + " TEXT, "
                + KEY_TELEFONO + " TEXT, "
                + KEY_MARCA_VEHICULO + " TEXT, "
                + KEY_MODELO_VEHICULO + " TEXT, "
                + KEY_MATRICULA + " TEXT UNIQUE"
                + ")";

        db.execSQL(Create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICULOS);
        onCreate(db);
    }

    // INSERTA UN NUEVO VEHÍCULO EN LA TABLA
    public void addVehiculo(Vehiculo vehiculo) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Se crea un objeto ContentValues para almacenar todos los datos y luego
        // insertarlo en la DB
        ContentValues valores = new ContentValues();
        valores.put(KEY_NOMBRE, vehiculo.getNombre()); // Primero se pone el nombre de la columna y luego el valor
        valores.put(KEY_APELLIDOS, vehiculo.getApellidos());
        valores.put(KEY_TELEFONO, vehiculo.getTelefono());
        valores.put(KEY_MARCA_VEHICULO, vehiculo.getMarcaVehiculo());
        valores.put(KEY_MODELO_VEHICULO, vehiculo.getModeloVehiculo());
        valores.put(KEY_MATRICULA, vehiculo.getMatricula());

        db.insert(TABLE_VEHICULOS, null, valores); // Se indica la tabla, luego null y luego el objeto ContentValues
        db.close(); // SIEMPRE se cierra la DB
    }

    // RETORNA TODOS LOS VEHÍCULOS DE LA TABLA
    public List<Vehiculo> getAllVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_VEHICULOS;
        Cursor cursor = db.rawQuery(selectQuery, null); // Esto nos permitirá movernos por cada fila del resultado

        if (cursor.moveToFirst()) {
            do {
                // Creamos un objeto Vehiculo por cada fila y vamos añadiéndolo a la lista
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setNombre(cursor.getString(1)); // El índice 0 es el ID, 1 es nombre, 2 apellidos, etc.
                vehiculo.setApellidos(cursor.getString(2));
                vehiculo.setTelefono(cursor.getString(3));
                vehiculo.setMarcaVehiculo(cursor.getString(4));
                vehiculo.setModeloVehiculo(cursor.getString(5));
                vehiculo.setMatricula(cursor.getString(6));

                vehiculos.add(vehiculo);
            } while (cursor.moveToNext());
        }

        return vehiculos;
    }

    public Vehiculo getVehiculoByMatricula(String matricula) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta 'SELECT' con un WHERE para buscar en matrícula
        Cursor cursor = db.query(TABLE_VEHICULOS, new String[] {
                KEY_ID, KEY_NOMBRE, KEY_APELLIDOS, KEY_TELEFONO,
                KEY_MARCA_VEHICULO, KEY_MODELO_VEHICULO, KEY_MATRICULA
        }, KEY_MATRICULA + "=?", new String[] { matricula }, null, null, null, null);

        // Comprueba que la consulta devuelva un resultado y si es así, construye un
        // Vehículo y lo devuelve
        if (cursor != null && cursor.moveToFirst()) {
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setNombre(cursor.getString(1));
            vehiculo.setApellidos(cursor.getString(2));
            vehiculo.setTelefono(cursor.getString(3));
            vehiculo.setMarcaVehiculo(cursor.getString(4));
            vehiculo.setModeloVehiculo(cursor.getString(5));
            vehiculo.setMatricula(cursor.getString(6));
            return vehiculo;
        }

        return null; // Retorna null si no se encuentra el vehículo
    }

    // ACTUALIZA LOS DATOS DE UN VEHÍCULO
    public int updateVehiculo(Vehiculo vehiculo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(KEY_NOMBRE, vehiculo.getNombre());
        valores.put(KEY_APELLIDOS, vehiculo.getApellidos());
        valores.put(KEY_TELEFONO, vehiculo.getTelefono());
        valores.put(KEY_MARCA_VEHICULO, vehiculo.getMarcaVehiculo());
        valores.put(KEY_MODELO_VEHICULO, vehiculo.getModeloVehiculo());
        valores.put(KEY_MATRICULA, vehiculo.getMatricula());

        // El DB.update devuelve el 'int' de filas afectadas. Si devuelve 1, es que se
        // ha actualizado correctamente
        return db.update(TABLE_VEHICULOS, valores, KEY_MATRICULA + "=?", new String[] { vehiculo.getMatricula() });
    }

    // ELIMINA UN VEHÍCULO DE LA TABLA
    public void deleteVehiculo(Vehiculo vehiculo) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_VEHICULOS, KEY_MATRICULA + "=?", new String[] { vehiculo.getMatricula() });
        db.close();
    }

    // COMPRUEBA SI UNA MATRÍCULA YA EXISTE EN LA TABLA
    public boolean existeMatricula(String matricula) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VEHICULOS, new String[] { KEY_ID }, KEY_MATRICULA + "=?",
                new String[] { matricula }, null, null, null);
        boolean existe = (cursor.getCount() > 0);
        cursor.close();
        return existe;
    }
}
