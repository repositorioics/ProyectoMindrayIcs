package ni.ics.mindrayics.principal;

import ni.ics.mindrayics.datos.MensajeDA;
import ni.ics.mindrayics.pdf.ReporteBhc;
import ni.ics.mindrayics.servicios.MensajeService;
import ni.ics.mindrayics.utils.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadClient implements Runnable {
    private final Socket cliente;
    public ThreadClient(Socket socket) {
        this.cliente = socket;
    }
    private final MensajeService mensajeService = new MensajeDA();
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void run() {
        BufferedReader br = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            cliente.setKeepAlive(true);
            cliente.setTcpNoDelay(true);
            cliente.setSoLinger(true, 0);
            br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            logger.info("Iniciando....");
            logger.info("Obteniendo respuesta del servidor.....");
            while (true) {
                if (br.ready()) {
                    logger.info("Respuesta del servidor.....");
                    String line = br.readLine().replaceAll("\\P{Print}", "");
                    while(!StringUtils.isNullOrEmpty(line)) {
                        stringBuilder.append(line);
                        stringBuilder.append("\n");
                        line = br.readLine();
                    }
                    if (!StringUtils.isNullOrEmpty(stringBuilder.toString())) {
                        mensajeService.transformarMensajeRecibido(stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                    }
                }
            }
        } catch (IOException e) {
            logger.error("IOException: %s%n", e);
        }
    }
}
