package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Camera extends SubsystemBase {

  public Camera() {

    new Thread(() -> {

      UsbCamera vs_camera = CameraServer.startAutomaticCapture();
      vs_camera.setResolution(640, 480);

    
      // Criacao da resolucao da camera
      /*CvSink cvsink = CameraServer.getVideo();
      CvSource outputStream = CameraServer.putVideo("blur", 640, 480); // nome e resolucao da camera
      // criacao de itens para tratamento da imagem
      Mat source = new Mat();
      Point p = new Point(320, 240); // posicao da marcacao
      Scalar s = new Scalar(0, 255, 0); // cor da marcacao
      // verifica se a thread foi parada
      while (!Thread.interrupted()) {
        // verifica se deu erro ao pegar o frame do video
        if (cvsink.grabFrame(source) == 0) {
          continue;
        }
        // desenha no frame capturado e depois o apresenta
        Imgproc.drawMarker(source, p, s, 0, 100, 4);
        outputStream.putFrame(source);
      } //*/

    }).start();
    
  }

  @Override
  public void periodic() {
  }

}
