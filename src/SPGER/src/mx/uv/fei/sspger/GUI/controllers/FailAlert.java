package mx.uv.fei.sspger.GUI.controllers;

import mx.uv.fei.sspger.logic.Status;

public class FailAlert {

    public static void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

    public static void showFXMLFileFailedAlert() {
        DialogGenerator.getDialog(new AlertMessage("Archivo FXML corrupto.", Status.FATAL));
    }

    public static void downloadFailedAlert() {
        DialogGenerator.getDialog(new AlertMessage("No se pudo realizar la descarga", Status.ERROR));
    }
}
