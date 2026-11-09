package pe.edu.upeu.sysventas.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.Map;

public class QrUtil {

    // Genera el QR directamente como imagen de JavaFX
    public static Image generarFx(String contenido, int tam) {
        try {
            Map<EncodeHintType, Object> hints = Map.of(
                    EncodeHintType.CHARACTER_SET, "UTF-8",
                    EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M,
                    EncodeHintType.MARGIN, 1
            );
            BitMatrix matriz = new QRCodeWriter()
                    .encode(contenido, BarcodeFormat.QR_CODE, tam, tam, hints);

            WritableImage img = new WritableImage(tam, tam);
            for (int x = 0; x < tam; x++) {
                for (int y = 0; y < tam; y++) {
                    img.getPixelWriter().setArgb(x, y,
                            matriz.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return img;
        } catch (Exception e) {
            throw new RuntimeException("No se pudo generar el QR", e);
        }
    }

    // Formato estandar de comprobantes en Peru (SUNAT)
    public static String contenidoSunat(String rucEmisor, String tipo, String serie,
                                        String numero, double igv, double total, String fecha,
                                        String tipoDocCliente, String nroDocCliente) {
        return String.join("|", rucEmisor, tipo, serie, numero,
                String.format("%.2f", igv), String.format("%.2f", total),
                fecha, tipoDocCliente, nroDocCliente) + "|";
    }
}